package nz.ac.auckland.Anime.domain;

import javax.persistence.*;

/**
 * Created by Ben on 9/16/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long _id;

    @ManyToOne
    private Anime show;

    @ManyToOne
    private User user;

    private String review;

    public Review(){};

    public Review(Anime show, User user, String review) {
        this.show = show;
        this.user = user;
        this.review = review;
    }

    public Review(Long _id, Anime show, User user, String review) {
        this._id = _id;
        this.show = show;
        this.user = user;
        this.review = review;
    }

    public Long getId() {
        return _id;
    }

    public Anime getShow() {
        return show;
    }

    public User getUser() {
        return user;
    }

    public String getReview() {
        return review;
    }
}
