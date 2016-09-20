package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.*;
import nz.ac.auckland.Anime.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/User")
public class UserResource {

	// Setup a Logger.
	private static Logger _logger = LoggerFactory
			.getLogger(UserResource.class);


	//Update a specifc User object in the database where the specific user is identified,
	//by the path param id.
	@PUT
	@Path ("{id}")
	@Consumes({"application/xml","application/json"})
	public Response editUser(UserDTO is, @PathParam("id") int id) {

		PersistenceManager p = PersistenceManager.instance();
		EntityManager em = p.createEntityManager();
		em.getTransaction().begin();

		//getting the post request
		User newUser = UserMapper.toDomainModel(is);

		//finding the associated user
		User user = em.find(User.class, new Long(id));

		if(newUser.getFirstname() != null){
			user.setFirstname(newUser.getFirstname());
		}

		if(newUser.getFollowers() != null){
			user.setFollowers(newUser.getFollowers());
		}

		if(newUser.getLastname() != null){
			user.setLastname(newUser.getLastname());
		}

		if(newUser.getUsername() != null){
			user.setUsername(newUser.getUsername());
		}

		em.persist(user);
		em.getTransaction().commit();
		em.close();

		return Response.noContent().build();
	}

	//Creates a new User object based off of a UserDTO object, the newly created User
	//object is then persisted into the database.
	@POST
	@Consumes({"application/xml","application/json"})
	public Response createUser(UserDTO is) {
		//_logger.debug("Created anime with id: " + anime.getId());

			PersistenceManager p = PersistenceManager.instance();
			EntityManager em = p.createEntityManager();
			em.getTransaction().begin();
			_logger.info("jj "+is.getId() + "");
			_logger.info("jj "+is.getFirstname() + "");
			_logger.info("jj "+is.getLastname() + "");

			User user = UserMapper.toDomainModel(is);
			em.persist(user);
			em.getTransaction().commit();
			em.close();


			_logger.info("jj "+user.getId() + "");

		return Response.created(URI.create("/User/" + user.getId())).build();
		//_logger.debug("Created anime with id: " + anime.getId());
	}

	//Retrieves a specific User object that is stored in the database where the specific
	//User is indetified by the path param id.
	@GET
	@Path ("{id}")
	@Produces({"application/xml","application/json"})
	public UserDTO getUser(@PathParam("id") int id) {

		PersistenceManager p = PersistenceManager.instance();
		EntityManager em = p.createEntityManager();
		em.getTransaction().begin();
		User temp = em.find(User.class, new Long(id));
		UserDTO user;
		if(temp == null){
			throw new EntityNotFoundException();
		} else {
			 user = UserMapper.toDto(temp);
			_logger.info(temp.getId() + "");
			_logger.info(temp.getFirstname() + "");
			_logger.info(temp.getLastname() + "");
		}

		em.close();

		return user;
	}

	//Attempts to Log a user in where the user loggin in is represented by the path param
	//id. When a user logs in, if the id is valid a cookie is returned to the client, this
	//cookie is then used later on when a user tries to update a forum.
	@GET
	@Path ("{id}/login")
	@Produces({"application/xml","application/json"})
	public Response loginUser(@PathParam("id") int id) {

		PersistenceManager p = PersistenceManager.instance();
		EntityManager em = p.createEntityManager();
		em.getTransaction().begin();
		User temp = em.find(User.class, new Long(id));
		UserDTO user;
		if(temp == null){
			throw new EntityNotFoundException();
		} else {
			user = UserMapper.toDto(temp);
		}

		em.close();


		_logger.info("LOGGING IN AS NEW USER " +temp.getId().toString());
		return Response.noContent().cookie(new NewCookie("name", temp.getId().toString())).build();
	}

	//This method deletes a specific user from the database where the user being deleted is
	//identified by the path param id.
	@DELETE
	@Path ("{id}")
	@Produces({"application/xml","application/json"})
	public void deleteUser(@PathParam("id") int id) {

		PersistenceManager p = PersistenceManager.instance();
		EntityManager em = p.createEntityManager();
		em.getTransaction().begin();

		User temp = em.find(User.class, new Long(id));

		if(temp != null){
			Query query = em.createQuery("select a from User a");
			List<User> allUser = query.getResultList();
			for(User i : allUser){
				i.getFollowers().remove(temp);
			}

			query = em.createQuery("select a from Club a");
			List<Club> allClubs = query.getResultList();
			for(Club j : allClubs){
				j.getMembers().remove(temp);

			}

			query = em.createQuery("select a from Forum a");
			List<Forum> allForums = query.getResultList();
			for(Forum j : allForums){
				for(Comment c : j.getComments()){
					if(c.getCommenter().getId().equals(temp.getId())){
						j.getComments().remove(c);
						break;
					}
				}
				j.getModerators().remove(temp);
			}

			query = em.createQuery("select a from Review a");
			List<Review> allReviews = query.getResultList();
			for(Review j : allReviews){
				if(j.getUser().getId().equals(temp.getId())){
					em.remove(j);
				}
			}

			query = em.createQuery("select a from Rating a");
			List<Rating> allRating = query.getResultList();
			for(Rating j : allRating){
				if(j.getUser().getId().equals(temp.getId())){
					em.remove(j);
				}
			}

			em.remove(temp);
		}

		em.getTransaction().commit();

		em.close();

	}

	//Retrieves all User objects from the database and sends them as a list to the user who requested them.
	@GET
	@Produces({"application/xml", "application/json"})
	public List<UserDTO> getAllUser() {

		PersistenceManager p = PersistenceManager.instance();
		EntityManager em = p.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createQuery("select a from User a");

		List<User> allUser = query.getResultList();
		List<UserDTO> allUserDTO = new ArrayList<UserDTO>();

		for(User b : allUser){
			allUserDTO.add(UserMapper.toDto(b));
		}

		em.close();

		return allUserDTO;
	}



}
