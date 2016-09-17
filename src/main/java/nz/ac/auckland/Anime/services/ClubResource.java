package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.dto.ForumDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by Ben on 9/17/2016.
 */
@Path("/Club")
public class ClubResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(nz.ac.auckland.Anime.services.UserResource.class);

    @POST
    @Consumes({"application/xml","application/json"})
    public Response createForum(ForumDTO is) {
        //_logger.debug("Created parolee with id: " + parolee.getId());

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        System.out.println(is.getId());
        System.out.println(is.getComments().get(0).getComment());
        Forum forum = ForumMapper.toDomainModel(is);
        em.persist(forum);
        em.getTransaction().commit();
        em.close();

        return Response.created(URI.create("/Forum/" + forum.getId())).build();
        //_logger.debug("Created parolee with id: " + parolee.getId());
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml","application/json"})
    public ForumDTO getForum(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Forum child = em.find(Forum.class, new Long(id));
        System.out.println(child.getAnimeTopic().getId());
        ForumDTO forum = ForumMapper.toDto(child);
        em.close();

        return forum;
    }
}
