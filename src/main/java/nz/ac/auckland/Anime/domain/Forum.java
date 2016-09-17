package nz.ac.auckland.Anime.domain;

import nz.ac.auckland.Anime.dto.AnimeDTO;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Ben on 9/17/2016.
 */
@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long _id;

    @ManyToMany
    @JoinTable(name="Moderators", joinColumns=@JoinColumn(name="ForumID"),
            inverseJoinColumns = @JoinColumn(name="userID"))
    private List<User> moderators;

    @ElementCollection
    private List<Comment> comments;

    @ManyToOne
    private Anime animeTopic;

    public Forum() {
    }

    public Forum(List<User> moderators, List<Comment> comments, Anime topic) {
        this.moderators = moderators;
        this.comments = comments;
        this.animeTopic = topic;
    }

    public Forum(Long _id, List<User> moderators, List<Comment> comments, Anime topic) {
        this._id = _id;
        this.moderators = moderators;
        this.comments = comments;
        this.animeTopic = topic;
    }

    public Long getId() {
        return _id;
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Anime getAnimeTopic() {
        return animeTopic;
    }

    public void setAnimeTopic(Anime animeTopic) {
        this.animeTopic = animeTopic;
    }

}
