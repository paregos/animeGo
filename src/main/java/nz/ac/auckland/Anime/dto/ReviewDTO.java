package nz.ac.auckland.Anime.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Ben on 9/16/2016.
 */
@XmlRootElement(name="Review")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewDTO {

    private Long _id;

    private AnimeDTO show;

    private UserDTO reviewer;

    private String review;

    public ReviewDTO(){};

    public ReviewDTO(Long _id) {
        this._id = _id;
    }

    public ReviewDTO(AnimeDTO show, UserDTO user, String review) {
        this.show = show;
        this.reviewer = user;
        this.review = review;
    }

    public ReviewDTO(Long _id, AnimeDTO show, UserDTO reviewer, String review) {
        this._id = _id;
        this.show = show;
        this.reviewer = reviewer;
        this.review = review;
    }

    public Long getId() {
        return _id;
    }

    public AnimeDTO getShow() {
        return show;
    }

    public UserDTO getReviewer() {
        return reviewer;
    }

    public String getReview() {
        return review;
    }
}
