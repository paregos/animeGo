package nz.ac.auckland.Anime.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Ben on 9/17/2016.
 */
@Entity
public class Club {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long _id;

    @ManyToMany
    private Set<User> members;

    @OneToMany
    private Set<Forum> forums;

    public Club() {
    }

    public Club(Set<User> members, Set<Forum> forums) {
        this.members = members;
        this.forums = forums;
    }

    public Club(Long _id, Set<User> members, Set<Forum> forums) {
        this._id = _id;
        this.members = members;
        this.forums = forums;
    }

    public Long getId() {
        return _id;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Set<Forum> getForums() {
        return forums;
    }

    public void setForums(Set<Forum> forums) {
        this.forums = forums;
    }
}
