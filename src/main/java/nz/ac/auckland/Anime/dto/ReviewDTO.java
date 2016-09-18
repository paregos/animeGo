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

    private Long showID;

    private Long reviewerID;

    private String review;

    public ReviewDTO(){};

    public ReviewDTO(Long _id) {
        this._id = _id;
    }

    public ReviewDTO(Long show, Long user, String review) {
        this.showID = show;
        this.reviewerID = user;
        this.review = review;
    }

    public ReviewDTO(Long _id, Long show, Long reviewer, String review) {
        this._id = _id;
        this.showID = show;
        this.reviewerID = reviewer;
        this.review = review;
    }

    public Long getId() {
        return _id;
    }

    public Long getShowID() {
        return showID;
    }

    public Long getReviewerID() {
        return reviewerID;
    }

    public void setShowID(Long showID) {
        this.showID = showID;
    }

    public void setReviewerID(Long reviewerID) {
        this.reviewerID = reviewerID;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }
}
