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

    private UserDTO commenter;

    private String comment;

    private int date;

    public CommentDTO() {
    }

    public CommentDTO(UserDTO commenter, String comment, int date) {
        this.commenter = commenter;
        this.comment = comment;
        this.date = date;
    }

    public UserDTO getCommenter() {
        return commenter;
    }

    public void setCommenter(UserDTO commenter) {
        this.commenter = commenter;
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
