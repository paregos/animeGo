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
import javax.ws.rs.core.*;
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

    //Updates a specific forum object identified by the path param id. The forum can
    //only be updated if no moderators exist in the specified forum, OR the client who
    //is attempting to update the forum has previously logged in as a User who is a
    //moderator of the specific forum. This is checked by checking the clients cookie,
    //one which is given to a client after logging in as a user.
    @PUT
    @Path ("{id}")
    @Consumes({"application/xml","application/json"})
    public Response editForum(ForumDTO is, @PathParam("id") int id, @CookieParam("name") Long cookie) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        User moderator = new User();
        if(cookie != null) {
            moderator = em.find(User.class, cookie);
        }
        //getting the post request
        Forum newForum = ForumMapper.toDomainModel(is);
        //finding the associated forum
        Forum forum = em.find(Forum.class, new Long(id));

        if ((forum.getModerators().contains(moderator) || forum.getModerators().isEmpty()) && (forum.getModerators() != null)){
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

        } else {
         return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        em.close();

        return Response.noContent().build();
    }

    //Creates a new Forum object and persists it to the database, the newly created
    //objet is based off of the forumDTO obj that is passed in the payload of this method.
    @POST
    @Consumes({"application/xml","application/json"})
    public Response createForum(ForumDTO is) {
        //_logger.debug("Created anime with id: " + anime.getId());

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Forum forum = ForumMapper.toDomainModel(is);
        em.persist(forum);
        em.getTransaction().commit();
        em.close();

        return Response.created(URI.create("/Forum/" + forum.getId())).build();
        //_logger.debug("Created anime with id: " + anime.getId());
    }

    //Returns a response containing a specific forum object, where the forum object is
    //specified by the path param id.
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

    //This method is used to get a range of comments that are owned by a certain forum
    //represented by the path param id. Query params are used to request which index
    // the range should start at as well as the size of the query.
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

    //This method is used to get a range of moderators that are part of a certain forum
    //represented by the path param id. Hateos link headers are used to return next or
    // prev links based on the range selected. Query params are used to request which index
    // the range should start at as well as the size of the query.
    @GET
    @Path("{id}/moderators")
    @Produces({"application/xml", "application/json"})
    public Response getForumModerators(@PathParam("id") Long id,
                                            @DefaultValue("0") @QueryParam("start") int start,
                                            @DefaultValue("10") @QueryParam("size") int size,
                                            @Context UriInfo uriInfo) {

        URI uri = uriInfo.getAbsolutePath();

        Link previous = null;
        Link next = null;

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a.moderators from Forum a where a.id = :id").setParameter("id", id);

        List<User> allForumModerators = query.setFirstResult(start) // Index of first row to be retrieved.
                .setMaxResults(size) // Amount of rows to be retrieved.
                .getResultList();

        if(start > 0) {
            // There are previous moderators - create a previous link.
            previous = Link.fromUri(uri + "?start={start}&size={size}")
                    .rel("prev")
                    .build(start - 1, size);
        }
        if(start + size <= query.getResultList().size()) {
            // There are successive moderators - create a next link.
            _logger.info("Making NEXT link");
            next = Link.fromUri(uri + "?start={start}&size={size}")
                    .rel("next")
                    .build(start + 1, size);
        }

        List<UserDTO> forumModerators = new ArrayList<UserDTO>();

        for (User member : allForumModerators) {
            forumModerators.add(UserMapper.toDto(member));
        }

        GenericEntity<List<UserDTO>> entity = new GenericEntity<List<UserDTO>>(forumModerators){};
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


    //Deletes a specfied forum where the forum to be deleted from the database is identified
    //by the pathparam id.
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

    //Get all of the forums stored within the database and return them to the user
    //sending this get request.
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
