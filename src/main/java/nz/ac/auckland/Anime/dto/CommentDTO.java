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

    private int date;

    public CommentDTO() {
    }

    public CommentDTO(Long commenter, String comment, int date) {
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
