package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Club;
import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.ClubDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import nz.ac.auckland.Anime.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
            .getLogger(nz.ac.auckland.Anime.services.ClubResource.class);


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
                _logger.info(a.getId().toString());
            }
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
    public Response getClubForums(@PathParam("id") Long id,
                                        @DefaultValue("0") @QueryParam("start") int start,
                                        @DefaultValue("1") @QueryParam("size") int size,
                                        @Context UriInfo uriInfo) {

        URI uri = uriInfo.getAbsolutePath();

        Link previous = null;
        Link next = null;

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a.forums from Club a where a.id = :id").setParameter("id", id);

        List<Forum> allClubForums = query.setFirstResult(start) // Index of first row to be retrieved.
                            .setMaxResults(size) // Amount of rows to be retrieved.
                            .getResultList();

        if(start > 0) {
            // There are previous Forums - create a previous link.
            previous = Link.fromUri(uri + "?start={start}&size={size}")
                    .rel("prev")
                    .build(start - 1, size);
        }
        if(start + size <= query.getResultList().size()) {
            // There are successive forums - create a next link.
            _logger.info("Making NEXT link");
            next = Link.fromUri(uri + "?start={start}&size={size}")
                    .rel("next")
                    .build(start + 1, size);
        }

        List<ForumDTO> clubForums = new ArrayList<ForumDTO>();

        if(allClubForums == null) {
            throw new EntityNotFoundException();
        } else {
            for (Forum b : allClubForums) {
                clubForums.add(ForumMapper.toDto(b));
            }
        }

        GenericEntity<List<ForumDTO>> entity = new GenericEntity<List<ForumDTO>>(clubForums){};
        // Build a Response that contains the list of Forums plus the link
        // headers.
        Response.ResponseBuilder builder = Response.ok(entity);
        if(previous != null) {
            builder.links(previous);
        }
        if(next != null) {
            builder.links(next);
        }
        Response response = builder.build();


        em.close();

        return response;
    }

    @DELETE
    @Path ("{id}")
    @Produces({"application/xml","application/json"})
    public void deleteClub(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Club temp = em.find(Club.class, new Long(id));
        em.remove(temp);

        em.getTransaction().commit();

        em.close();

    }


    @GET
    @Path("{id}/members")
    @Produces({"application/xml", "application/json"})
    public Response getClubMembers(@PathParam("id") Long id,
                                        @DefaultValue("0") @QueryParam("start") int start,
                                        @DefaultValue("1") @QueryParam("size") int size,
                                        @Context UriInfo uriInfo) {

        URI uri = uriInfo.getAbsolutePath();

        Link previous = null;
        Link next = null;

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a.members from Club a where a.id = :id").setParameter("id", id);

        List<User> allClubMembers = query.setFirstResult(start) // Index of first row to be retrieved.
                .setMaxResults(size) // Amount of rows to be retrieved.
                .getResultList();

        if(start > 0) {
            // There are previous members - create a previous link.
            previous = Link.fromUri(uri + "?start={start}&size={size}")
                    .rel("prev")
                    .build(start - 1, size);
        }
        if(start + size <= query.getResultList().size()) {
            // There are successive memebrs - create a next link.
            _logger.info("Making NEXT link");
            next = Link.fromUri(uri + "?start={start}&size={size}")
                    .rel("next")
                    .build(start + 1, size);
        }

        List<UserDTO> clubMembers = new ArrayList<UserDTO>();

        if(allClubMembers == null){
            throw new EntityNotFoundException();
        } else {
            for (User member : allClubMembers) {
                clubMembers.add(UserMapper.toDto(member));
            }
        }

        GenericEntity<List<UserDTO>> entity = new GenericEntity<List<UserDTO>>(clubMembers){};
        // Build a Response that contains the list of Members plus the link
        // headers.
        Response.ResponseBuilder builder = Response.ok(entity);
        if(previous != null) {
            builder.links(previous);
        }
        if(next != null) {
            builder.links(next);
        }
        Response response = builder.build();

        em.close();

        return response;
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ClubDTO getClub(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Club child = em.find(Club.class, new Long(id));
        ClubDTO club;
        if(child == null){
            throw new EntityNotFoundException();
        } else {
            club = ClubMapper.toDto(child);
        }

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
