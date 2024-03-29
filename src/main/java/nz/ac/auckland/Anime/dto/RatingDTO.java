package nz.ac.auckland.Anime.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Ben on 9/16/2016.
 */
@XmlRootElement(name="Rating")
@XmlAccessorType(XmlAccessType.FIELD)
public class RatingDTO extends ReviewDTO{

    private Long score;

    public RatingDTO() {
    }

    public RatingDTO(Long _id, Long score) {
        super(_id);
        this.score = score;
    }

    public RatingDTO(Long show, Long user, String review, Long score) {
        super(show, user, review);
        this.score = score;
    }

    public RatingDTO(Long _id, Long show, Long reviewer, String review, Long score) {
        super(_id, show, reviewer, review);
        this.score = score;
    }

    public Long getScore() {
        return score;
    }
}
