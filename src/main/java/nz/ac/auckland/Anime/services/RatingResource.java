package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.Rating;
import nz.ac.auckland.Anime.domain.Review;
import nz.ac.auckland.Anime.dto.RatingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 9/16/2016.
 */
@Path("/Rating")
public class RatingResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(RatingResource.class);


    @PUT
    @Path ("{id}")
    @Consumes({"application/xml","application/json"})
    public Response editRating(RatingDTO is, @PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        //getting the post request
        Rating newRating = RatingMapper.toDomainModel(is);

        //finding the associated rating
        Rating rating = em.find(Rating.class, new Long(id));

        if(newRating.getReview() != null ){
            rating.setReview(newRating.getReview());
        }
        if(newRating.getShow() != null ){
            rating.setShow(newRating.getShow());
        }
        if(newRating.getUser() != null ){
            rating.setUser(newRating.getUser());
        }
        if(newRating.getScore() != null ){
            rating.setScore(newRating.getScore());
        }

        em.persist(rating);
        em.getTransaction().commit();
        em.close();

        return Response.noContent().build();
    }

    @POST
    @Consumes({"application/xml","application/json"})
    public Response createRating(RatingDTO is) {
        //_logger.debug("Created parolee with id: " + parolee.getId());

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Rating rating = RatingMapper.toDomainModel(is);
        em.persist(rating);
        em.getTransaction().commit();
        em.close();

        return Response.created(URI.create("/Rating/" + rating.getId())).build();
        //_logger.debug("Created parolee with id: " + parolee.getId());
    }

    @DELETE
    @Path ("{id}")
    @Produces({"application/xml","application/json"})
    public void deleteRating(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Rating temp = em.find(Rating.class, new Long(id));
        em.remove(temp);

        em.getTransaction().commit();

        em.close();

    }

    @GET
    @Path("{id}")
    @Produces({"application/xml","application/json"})
    public RatingDTO getRating(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Rating child = em.find(Rating.class, new Long(id));
        RatingDTO rating;
        if(child == null){
            throw new EntityNotFoundException();
        } else {
             rating = RatingMapper.toDto(child);
        }

        em.close();

        return rating;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<RatingDTO> getAllRating() {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a from Rating a");

        List<Rating> allRating = query.getResultList();
        List<RatingDTO> allRatingDTO = new ArrayList<RatingDTO>();

        for(Rating b : allRating){
            allRatingDTO.add(RatingMapper.toDto(b));
        }

        em.close();

        return allRatingDTO;
    }

}
