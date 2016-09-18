package nz.ac.auckland.Anime.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Ben on 9/17/2016.
 */
@XmlRootElement(name="Comment")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommentDTO {

    private Long commenterID;

    private String comment;

    private Long date;

    public CommentDTO() {
    }

    public CommentDTO(Long commenter, String comment, Long date) {
        this.commenterID = commenter;
        this.comment = comment;
        this.date = date;
    }

    public Long getCommenterID() {
        return commenterID;
    }

    public void setCommenterID(Long commenterID) {
        this.commenterID = commenterID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
