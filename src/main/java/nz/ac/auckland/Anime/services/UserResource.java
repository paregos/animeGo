package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/User")
public class UserResource {
	// Setup a Logger.
	private static Logger _logger = LoggerFactory
			.getLogger(UserResource.class);


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

		user.setFirstname(newUser.getFirstname());
		user.setFollowers(newUser.getFollowers());
		user.setLastname(newUser.getLastname());
		user.setUsername(newUser.getUsername());

		em.persist(user);
		em.getTransaction().commit();
		em.close();

		return Response.noContent().build();
	}

	@POST
	@Consumes({"application/xml","application/json"})
	public Response createUser(UserDTO is) {
		//_logger.debug("Created parolee with id: " + parolee.getId());

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
		//_logger.debug("Created parolee with id: " + parolee.getId());
	}

	@GET
	@Path ("{id}")
	@Produces({"application/xml","application/json"})
	public UserDTO getUser(@PathParam("id") int id) {

		PersistenceManager p = PersistenceManager.instance();
		EntityManager em = p.createEntityManager();
		em.getTransaction().begin();
		User temp = em.find(User.class, new Long(id));
		UserDTO user = UserMapper.toDto(temp);

		em.close();

		_logger.info(temp.getId() + "");
		_logger.info(temp.getFirstname() + "");
		_logger.info(temp.getLastname() + "");

		return user;
	}

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
