package nz.ac.auckland.Anime.domain;

import javax.persistence.*;

/**
 * Created by Ben on 9/16/2016.
 */
@Entity

public class Rating extends Review{

    private int score;

    public Rating() {
    }

    public Rating(Anime show, User user, String review, int score) {
        super(show, user, review);
        this.score = score;
    }

    public Rating(Long _id, Anime show, User user, String review, int score) {
        super(_id, show, user, review);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
