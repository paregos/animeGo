package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.AnimeDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 9/16/2016.
 */
public class AnimeMapper {

    static Anime toDomainModel(AnimeDTO in) {

        Set<Anime> sequels = new HashSet<Anime>();

        for(Long i : in.getSequelIds()){
            PersistenceManager p = PersistenceManager.instance();
            EntityManager em = p.createEntityManager();
            em.getTransaction().begin();
            Anime temp = em.find(Anime.class, i);
            System.out.println(temp.getId());
            sequels.add(temp);
            em.close();
        }

        Anime anime = new Anime(in.getId(), in.getTitle(), in.getEpisodes(), in.getYear(), in.getSynopsis(), sequels);

        return anime;
    }

    static AnimeDTO toDto(Anime anime) {

        Set<Long> sequelIds = new HashSet<Long>();

        for(Anime i : anime.getSequels()){
            sequelIds.add(i.getId());
        }

        AnimeDTO in = new AnimeDTO(anime.getId(), anime.getTitle(), anime.getEpisodes(), anime.getYear(), anime.getSynopsis(), sequelIds);

        return in;

    }


}
