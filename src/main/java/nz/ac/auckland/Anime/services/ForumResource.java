package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Forum;
import nz.ac.auckland.Anime.domain.PersistenceManager;
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
@Path("/Forum")
public class ForumResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(nz.ac.auckland.Anime.services.UserResource.class);

    @PUT
    @Path ("{id}")
    @Consumes({"application/xml","application/json"})
    public Response editForum(ForumDTO is, @PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        //getting the post request
        Forum newForum = ForumMapper.toDomainModel(is);

        //finding the associated forum
        Forum forum = em.find(Forum.class, new Long(id));

        if(newForum.getModerators() != null ){
            forum.setModerators(newForum.getModerators());
        }
        if(newForum.getComments() != null ){
            forum.setComments(newForum.getComments());
        }
        if(newForum.getAnimeTopic() != null ){
            forum.setAnimeTopic(newForum.getAnimeTopic());
        }

        em.persist(forum);
        em.getTransaction().commit();
        em.close();

        return Response.noContent().build();
    }

    @POST
    @Consumes({"application/xml","application/json"})
    public Response createForum(ForumDTO is) {
        //_logger.debug("Created parolee with id: " + parolee.getId());

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
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

    @GET
    @Produces({"application/xml", "application/json"})
    public List<ForumDTO> getAllForum() {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a from Forum a");

        List<Forum> allForum = query.getResultList();
        List<ForumDTO> allForumDTO = new ArrayList<ForumDTO>();

        for(Forum b : allForum){
            allForumDTO.add(ForumMapper.toDto(b));
        }

        em.close();

        return allForumDTO;
    }
}
