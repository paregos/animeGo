package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.Rating;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.RatingDTO;

import javax.persistence.EntityManager;

/**
 * Created by Ben on 9/16/2016.
 */
public class RatingMapper {

    static Rating toDomainModel(RatingDTO in) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();

        em.getTransaction().begin();
        User reviewer = in.getReviewerID() == null ? null : em.find(User.class, in.getReviewerID());
        em.close();

        em = p.createEntityManager();
        em.getTransaction().begin();
        Anime show = in.getShowID() == null ? null : em.find(Anime.class, in.getShowID());
        em.close();

        Rating rating = new Rating(in.getId(), show, reviewer, in.getReview(), in.getScore());

        return rating;
    }

    static RatingDTO toDto(Rating rating) {

        Long animeId = rating.getShow() == null ? null : rating.getShow().getId();
        Long reviewerId = rating.getUser() == null ? null : rating.getUser().getId();

        RatingDTO in = new RatingDTO(rating.getId(), animeId, reviewerId, rating.getReview(), rating.getScore());

        return in;

    }

}
