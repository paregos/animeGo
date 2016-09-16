package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.Rating;
import nz.ac.auckland.Anime.dto.RatingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by Ben on 9/16/2016.
 */
@Path("/Rating")
public class RatingResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(RatingResource.class);

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

    @GET
    @Path("{id}")
    @Produces({"application/xml","application/json"})
    public RatingDTO getRating(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Rating temp = em.find(Rating.class, new Long(id));
        RatingDTO rating = RatingMapper.toDto(temp);
        em.close();

        return rating;
    }

}
