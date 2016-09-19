package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.*;
import nz.ac.auckland.Anime.dto.CommentDTO;
import nz.ac.auckland.Anime.dto.ForumDTO;
import nz.ac.auckland.Anime.dto.UserDTO;
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
        ForumDTO forum;
        if(child == null){
            throw new EntityNotFoundException();
        } else {
            forum = ForumMapper.toDto(child);
        }
        em.close();

        return forum;
    }

    @GET
    @Path("{id}/comments")
    @Produces({"application/xml", "application/json"})
    public List<CommentDTO> getForumComments(@PathParam("id") Long id,
                                        @DefaultValue("10") @QueryParam("size") int size) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a from Forum a where a.id = :id").setParameter("id", id);

        Forum forum = (Forum) query.getSingleResult();
        em.close();

        List<CommentDTO> forumComments = new ArrayList<CommentDTO>();

        int counter = 0;
        if (forum == null ){
            throw new EntityNotFoundException();
        }else {
            for (Comment comment : forum.getComments()) {
                forumComments.add(CommentMapper.toDto(comment));
                counter++;
                if (counter >= size) {
                    break;
                }
            }
        }

        return forumComments;
    }

    @GET
    @Path("{id}/moderators")
    @Produces({"application/xml", "application/json"})
    public List<UserDTO> getForumModerators(@PathParam("id") Long id,
                                        @DefaultValue("0") @QueryParam("start") int start,
                                        @DefaultValue("10") @QueryParam("size") int size) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a.moderators from Forum a where a.id = :id").setParameter("id", id);

        List<User> allForumModerators = query.setFirstResult(start) // Index of first row to be retrieved.
                .setMaxResults(size) // Amount of rows to be retrieved.
                .getResultList();
        em.close();

        List<UserDTO> forumModerators = new ArrayList<UserDTO>();

        for (User member : allForumModerators) {
            forumModerators.add(UserMapper.toDto(member));

        }

        return forumModerators;
    }

    @DELETE
    @Path ("{id}")
    @Produces({"application/xml","application/json"})
    public void deleteForum(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Forum temp = em.find(Forum.class, new Long(id));

        if(temp != null){

            Query query = em.createQuery("select a from Club a");
            List<Club> allClubs = query.getResultList();
                for(Club j : allClubs){
                    j.getForums().remove(temp);
                }

            }

        em.remove(temp);

        em.getTransaction().commit();

        em.close();

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
