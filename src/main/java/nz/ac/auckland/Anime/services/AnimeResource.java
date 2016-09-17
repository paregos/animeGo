package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.AnimeDTO;
import nz.ac.auckland.Anime.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ben on 9/16/2016.
 */
@Path("/Anime")
public class AnimeResource {

    // Setup a Logger.
    private static Logger _logger = LoggerFactory
            .getLogger(nz.ac.auckland.Anime.services.UserResource.class);

    @PUT
    @Path ("{id}")
    @Consumes({"application/xml","application/json"})
    public Response editAnime(AnimeDTO is, @PathParam("id") int id) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        //getting the post request
        Anime newAnime = AnimeMapper.toDomainModel(is);

        //finding the associated anime
        Anime anime = em.find(Anime.class, new Long(id));

        anime.setEpisodes(newAnime.getEpisodes());
        anime.setSequels(newAnime.getSequels());
        anime.setSynopsis(newAnime.getSynopsis());
        anime.setTitle(newAnime.getTitle());
        anime.setYear(newAnime.getYear());

        em.persist(anime);
        em.getTransaction().commit();
        em.close();

        return Response.created(URI.create("/Anime/" + anime.getId())).build();
    }

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

    @GET
    @Produces({"application/xml", "application/json"})
    public List<AnimeDTO> getAllAnime() {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select a from Anime a");

        List<Anime> allAnime = query.getResultList();
        List<AnimeDTO> allAnimeDTO = new ArrayList<AnimeDTO>();

        for(Anime b : allAnime){
            allAnimeDTO.add(AnimeMapper.toDto(b));
        }

        em.close();

        return allAnimeDTO;
    }


}
