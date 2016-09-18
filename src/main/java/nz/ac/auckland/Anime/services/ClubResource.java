package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Club;
import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.dto.ClubDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 9/17/2016.
 */
@Path("/Club")
public class ClubResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(nz.ac.auckland.Anime.services.UserResource.class);


    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response editClub(ClubDTO is, @PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        //getting the post request
        Club newClub = ClubMapper.toDomainModel(is);

        //finding the associated club
        Club club = em.find(Club.class, new Long(id));

        if (newClub.getMembers() != null) {
            club.setMembers(newClub.getMembers());
        }
        if (newClub.getForums() != null) {
            club.setForums(newClub.getForums());
            for(Forum a : newClub.getForums()){
                System.out.println(a.getId());
            }
        } else {
            System.out.println("YOOooOOo");
        }
        if (newClub.getName() != null) {
            club.setName(newClub.getName());
        }

        em.persist(club);
        em.getTransaction().commit();
        em.close();

        return Response.noContent().build();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createClub(ClubDTO is) {
        //_logger.debug("Created parolee with id: " + parolee.getId());

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Club club = ClubMapper.toDomainModel(is);
        em.persist(club);
        em.getTransaction().commit();
        em.close();

        return Response.created(URI.create("/Club/" + club.getId())).build();
        //_logger.debug("Created parolee with id: " + parolee.getId());
    }

    @GET
    @Path("{id}/forums")
    @Produces({"application/xml", "application/json"})
    public List<ForumDTO> getClubForums(@PathParam("id") Long id,
                                        @DefaultValue("0") @QueryParam("start") int start,
                                        @DefaultValue("10") @QueryParam("size") int size) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a.forums from Club a where a.id = :id").setParameter("id", id);

        List<Forum> allClubForums = query.setFirstResult(start) // Index of first row to be retrieved.
                            .setMaxResults(size) // Amount of rows to be retrieved.
                            .getResultList();

        List<ForumDTO> clubForums = new ArrayList<ForumDTO>();

        for (Forum b : allClubForums) {
            clubForums.add(ForumMapper.toDto(b));
        }

        em.close();

        return clubForums;
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ClubDTO getClub(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Club child = em.find(Club.class, new Long(id));
        ClubDTO club = ClubMapper.toDto(child);
        em.close();

        return club;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<ClubDTO> getAllRating() {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a from Club a");

        List<Club> allClub = query.getResultList();
        List<ClubDTO> allClubDTO = new ArrayList<ClubDTO>();

        for (Club b : allClub) {
            allClubDTO.add(ClubMapper.toDto(b));
        }

        em.close();

        return allClubDTO;
    }
}
