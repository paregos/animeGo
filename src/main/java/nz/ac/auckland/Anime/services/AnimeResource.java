package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.AnimeDTO;
import nz.ac.auckland.Anime.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by Ben on 9/16/2016.
 */
@Path("/Anime")
public class AnimeResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(nz.ac.auckland.Anime.services.UserResource.class);

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

        return Response.created(URI.create("/Anime/" + anime.getId())).build();
        //_logger.debug("Created parolee with id: " + parolee.getId());
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public AnimeDTO getAnime(@PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();
        Anime temp = em.find(Anime.class, new Long(id));
        AnimeDTO anime = AnimeMapper.toDto(temp);

        em.close();

        return anime;
    }


}
