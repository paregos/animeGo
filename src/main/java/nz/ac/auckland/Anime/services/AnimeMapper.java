package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.dto.AnimeDTO;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 9/16/2016.
 */
public class AnimeMapper {

    static Anime toDomainModel(AnimeDTO in) {

        Set<Anime> sequels = new HashSet<Anime>();

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();

        if(in.getSequelIds() != null) {
            for (Long i : in.getSequelIds()) {
                em = p.createEntityManager();
                em.getTransaction().begin();
                Anime sequel = i == null ? null : em.find(Anime.class, i);
                sequels.add(sequel);
                em.close();
            }
        }

        Anime anime = new Anime(in.getId(), in.getTitle(), in.getEpisodes(), in.getYear(), in.getSynopsis(), sequels);

        return anime;
    }

    static AnimeDTO toDto(Anime anime) {

        Set<Long> sequelIds = new HashSet<Long>();

        if(anime.getSequels() != null) {
            for (Anime i : anime.getSequels()) {
                Long temp = i == null ? null : i.getId();
                sequelIds.add(temp);
            }
        }

        AnimeDTO in = new AnimeDTO(anime.getId(), anime.getTitle(), anime.getEpisodes(), anime.getYear(), anime.getSynopsis(), sequelIds);

        return in;

    }


}
