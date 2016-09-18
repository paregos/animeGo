package nz.ac.auckland.Anime.domain;

import javax.persistence.*;

/**
 * Created by Ben on 9/16/2016.
 */
@Entity

public class Rating extends Review{

    private Long score;

    public Rating() {
    }

    public Rating(Anime show, User user, String review, Long score) {
        super(show, user, review);
        this.score = score;
    }

    public Rating(Long _id, Anime show, User user, String review, Long score) {
        super(_id, show, user, review);
        this.score = score;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
