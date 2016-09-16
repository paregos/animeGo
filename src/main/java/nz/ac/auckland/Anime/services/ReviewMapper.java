package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.Review;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.AnimeDTO;
import nz.ac.auckland.Anime.dto.ReviewDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

/**
 * Created by Ben on 9/16/2016.
 */
public class ReviewMapper {

    static Review toDomainModel(ReviewDTO in) {

        AnimeDTO show = in.getShow();
        System.out.println(show.getTitle());
        System.out.println(show.getId());
        UserDTO reviewer = in.getReviewer();
        System.out.println(reviewer.getFirstname());
        System.out.println(reviewer.getId());

        Review review = new Review(in.getId(), AnimeMapper.toDomainModel(show), UserMapper.toDomainModel(reviewer), in.getReview());

        return review;
    }

    static ReviewDTO toDto(Review review) {

        Anime show = review.getShow();
        User reviewer = review.getUser();

        ReviewDTO in = new ReviewDTO(review.getId(), AnimeMapper.toDto(show), UserMapper.toDto(reviewer), review.getReview());

        return in;

    }

}
