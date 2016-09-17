package nz.ac.auckland.Anime.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Ben on 9/17/2016.
 */
@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long _id;

    @ManyToMany
    private Set<User> commenters;

    @ManyToMany
    private Set<User> moderators;

    @OneToMany
    private Set<Comment> comments;

    private Topic topic;

    public Forum() {
    }

    public Forum(Set<User> commenters, Set<User> moderators, Set<Comment> comments, Topic topic) {
        this.commenters = commenters;
        this.moderators = moderators;
        this.comments = comments;
        this.topic = topic;
    }

    public Forum(Long _id, Set<User> commenters, Set<User> moderators, Set<Comment> comments, Topic topic) {
        this._id = _id;
        this.commenters = commenters;
        this.moderators = moderators;
        this.comments = comments;
        this.topic = topic;
    }

    public Set<User> getCommenters() {
        return commenters;
    }

    public void setCommenters(Set<User> commenters) {
        this.commenters = commenters;
    }

    public Set<User> getModerators() {
        return moderators;
    }

    public void setModerators(Set<User> moderators) {
        this.moderators = moderators;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    
}
