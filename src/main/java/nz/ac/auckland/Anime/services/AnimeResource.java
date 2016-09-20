package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.*;
import nz.ac.auckland.Anime.dto.AnimeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 9/16/2016.
 */
@Path("/Anime")
public class AnimeResource {

    protected List<AsyncResponse> responses = new
            ArrayList<AsyncResponse>( );

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(nz.ac.auckland.Anime.services.AnimeResource.class);


    @GET
    @Path("subscribe")
    @Produces({"application/xml","application/json"})
    public void subscribe(
            @Suspended AsyncResponse response ) {
        responses.add( response );
    }

    @PUT
    @Path ("{id}")
    @Consumes({"application/xml","application/json"})
    public Response editAnime(AnimeDTO is, @PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        //getting the post request
        Anime newAnime = AnimeMapper.toDomainModel(is);

        //finding the associated anime
        Anime anime = em.find(Anime.class, new Long(id));

        if(newAnime.getEpisodes() != null){
            anime.setEpisodes(newAnime.getEpisodes());
        }
        if(newAnime.getSequels() != null){
            anime.setSequels(newAnime.getSequels());
        }
        if(newAnime.getSynopsis() != null){
            anime.setSynopsis(newAnime.getSynopsis());
        }
        if(newAnime.getTitle() != null){
            anime.setTitle(newAnime.getTitle());
        }
        if(newAnime.getYear() != null){
            anime.setYear(newAnime.getYear());
        }

        em.persist(anime);
        em.getTransaction().commit();
        em.close();

        return Response.noContent().build();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createAnime(AnimeDTO is) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Anime anime = AnimeMapper.toDomainModel(is);
        em.persist(anime);
        em.getTransaction().commit();
        em.close();

        _logger.info("jj " + anime.getId() + "");

        // Notify subscribers.
        for (AsyncResponse response : responses) {
            response.resume(anime);
        }
        responses.clear();

        return Response.created(URI.create("/Anime/" + anime.getId())).build();
        //_logger.debug("Created parolee with id: " + parolee.getId());
    }

    @DELETE
    @Path ("{id}")
    @Produces({"application/xml","application/json"})
    public void deleteAnime(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Anime temp = em.find(Anime.class, new Long(id));

        if(temp != null){
            Query query = em.createQuery("select a from Forum a");
            List<Forum> allForums = query.getResultList();
            query = em.createQuery("select a from Club a");
            List<Club> allClubs = query.getResultList();
            for(Forum j : allForums){
                if(j.getAnimeTopic().getId().equals(temp.getId())){
                    for(Club c : allClubs){
                        c.getForums().remove(j);
                        em.persist(c);
                    }
//                    for(Comment s: j.getComments()){
//                        s.setCommenter(null);
//                    }
                    em.remove(j);
                }
            }

            query = em.createQuery("select a from Review a");
            List<Review> allReviews = query.getResultList();
            for(Review j : allReviews){
                if(j.getShow().getId().equals(temp.getId())){
                    em.remove(j);
                }
            }

            query = em.createQuery("select a from Rating a");
            List<Rating> allRating = query.getResultList();
            for(Rating j : allRating){
                if(j.getShow().getId().equals(temp.getId())){
                    em.remove(j);
                }
            }

            em.remove(temp);
        }

        em.getTransaction().commit();

        em.close();

    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public AnimeDTO getAnime(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Anime temp = em.find(Anime.class, new Long(id));
        AnimeDTO anime;
        if(temp == null){
            throw new EntityNotFoundException();
        } else {
            anime = AnimeMapper.toDto(temp);
        }


        em.close();

        return anime;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<AnimeDTO> getAllAnime() {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a from Anime a");

        List<Anime> allAnime = query.getResultList();
        List<AnimeDTO> allAnimeDTO = new ArrayList<AnimeDTO>();

        for(Anime b : allAnime){
            allAnimeDTO.add(AnimeMapper.toDto(b));
        }

        em.close();

        return allAnimeDTO;
    }


}
