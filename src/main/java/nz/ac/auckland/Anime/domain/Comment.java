package nz.ac.auckland.Anime.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by Ben on 9/17/2016.
 */
@Embeddable
public class Comment {

    @ManyToOne
    private User commenter;

    private String comment;

    private int date;

    public Comment() {
    }

    public Comment(User commenter, String comment, int date) {
        this.commenter = commenter;
        this.comment = comment;
        this.date = date;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
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
