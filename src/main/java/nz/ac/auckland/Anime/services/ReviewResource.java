package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.Rating;
import nz.ac.auckland.Anime.domain.Review;
import nz.ac.auckland.Anime.dto.RatingDTO;
import nz.ac.auckland.Anime.dto.ReviewDTO;
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
@Path("/Review")
public class ReviewResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(ReviewResource.class);

    //This method updates a specified review object in the database.
    @PUT
    @Path ("{id}")
    @Consumes({"application/xml","application/json"})
    public Response editReview(ReviewDTO is, @PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        //getting the post request
        Review newReview = ReviewMapper.toDomainModel(is);

        //finding the associated review
        Review review = em.find(Review.class, new Long(id));

        if(newReview.getReview() != null){
            review.setReview(newReview.getReview());
        }
        if(newReview.getShow() != null){
            review.setShow(newReview.getShow());
        }
        if(newReview.getUser() != null) {
            review.setUser(newReview.getUser());
        }

        em.persist(review);
        em.getTransaction().commit();
        em.close();

        return Response.noContent().build();
    }

    //This method creates a new review object based off of a reviewDTO object and
    //presists it to the database.
    @POST
    @Consumes({"application/xml","application/json"})
    public Response createReview(ReviewDTO is) {
        //_logger.debug("Created anime with id: " + anime.getId());

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Review review = ReviewMapper.toDomainModel(is);
        em.persist(review);
        em.getTransaction().commit();
        em.close();

        return Response.created(URI.create("/Review/" + review.getId())).build();
        //_logger.debug("Created anime with id: " + anime.getId());
    }

    //This method retrieves a specified review object
    @GET
    @Path("{id}")
    @Produces({"application/xml","application/json"})
    public ReviewDTO getReview(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Review temp = em.find(Review.class, new Long(id));
        ReviewDTO review;
        if(temp == null){
            throw new EntityNotFoundException();
        } else {
            review = ReviewMapper.toDto(temp);
        }


        em.close();

        return review;
    }

    //This method deletes a specified review object from the database.
    @DELETE
    @Path ("{id}")
    @Produces({"application/xml","application/json"})
    public void deleteReview(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Review temp = em.find(Review.class, new Long(id));
        em.remove(temp);

        em.getTransaction().commit();

        em.close();

    }

    //Retrieves all of the review objects that are stored in the database and returns
    //them to the user that requested them.
    @GET
    @Produces({"application/xml", "application/json"})
    public List<ReviewDTO> getAllRating() {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a from Review a");

        List<Review> allReview = query.getResultList();
        List<ReviewDTO> allReviewDTO = new ArrayList<ReviewDTO>();

        for(Review b : allReview){
            allReviewDTO.add(ReviewMapper.toDto(b));
        }

        em.close();

        return allReviewDTO;
    }

}
