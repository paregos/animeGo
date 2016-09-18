package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.PersistenceManager;
import nz.ac.auckland.Anime.domain.Review;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.AnimeDTO;
import nz.ac.auckland.Anime.dto.ReviewDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

import javax.persistence.EntityManager;

/**
 * Created by Ben on 9/16/2016.
 */
public class ReviewMapper {

    static Review toDomainModel(ReviewDTO in) {

        PersistenceManager p = PersistenceManager.instance();
        EntityManager em = p.createEntityManager();

        em.getTransaction().begin();
        User reviewer = in.getReviewerID() == null ? null : em.find(User.class, in.getReviewerID());
        em.close();

        em = p.createEntityManager();
        em.getTransaction().begin();
        Anime show = in.getShowID() == null ? null : em.find(Anime.class, in.getShowID());
        em.close();

        String reviewString = in.getReview() == null ? null : in.getReview().equals("") ? null : in.getReview();

        Review review = new Review(in.getId(), show, reviewer, reviewString);

        return review;
    }

    static ReviewDTO toDto(Review review) {

        Long animeId = review.getShow() == null ? null : review.getShow().getId();
        Long reviewerId = review.getUser() == null ? null : review.getUser().getId();

        ReviewDTO in = new ReviewDTO(review.getId(), animeId, reviewerId, review.getReview());

        return in;

    }

}
