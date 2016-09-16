package nz.ac.auckland.Anime.services;

import nz.ac.auckland.Anime.domain.Anime;
import nz.ac.auckland.Anime.domain.Rating;
import nz.ac.auckland.Anime.domain.User;
import nz.ac.auckland.Anime.dto.AnimeDTO;
import nz.ac.auckland.Anime.dto.RatingDTO;
import nz.ac.auckland.Anime.dto.UserDTO;

/**
 * Created by Ben on 9/16/2016.
 */
public class RatingMapper {

    static Rating toDomainModel(RatingDTO in) {

        AnimeDTO show = in.getShow();
        System.out.println(show.getTitle());
        System.out.println(show.getId());
        UserDTO reviewer = in.getReviewer();
        System.out.println(reviewer.getFirstname());
        System.out.println(reviewer.getId());

        Rating rating = new Rating(in.getId(), AnimeMapper.toDomainModel(show), UserMapper.toDomainModel(reviewer), in.getReview(), in.getScore());

        return rating;
    }

    static RatingDTO toDto(Rating rating) {

        Anime show = rating.getShow();
        User reviewer = rating.getUser();

        RatingDTO in = new RatingDTO(rating.getId(), AnimeMapper.toDto(show), UserMapper.toDto(reviewer), rating.getReview(), rating.getScore());

        return in;

    }

}
