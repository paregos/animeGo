package nz.ac.auckland.Anime.dto;
import javax.xml.bind.annotation.*;
import java.util.Set;

/**
 * Created by Ben on 9/8/2016.
 */
@XmlRootElement(name="User")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO {

    private Long _id;

    private String username;

    private String lastname;

    private String firstname;

    @XmlElementWrapper(name="followingIds")
    @XmlElement(name="userId")
    private Set<Long> followIds;
    //private Set<User> friends;

    public UserDTO(String username, String lastname, String firstname) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public UserDTO(String username, String lastname, String firstname, Set<Long> follow) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
         this.followIds = follow;
    }

    public UserDTO(Long _id, String username, String lastname, String firstname, Set<Long> followIds) {
        this._id = _id;
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.followIds = followIds;
    }

    public UserDTO(String username) {
        this(username, null, null);
    }

    public UserDTO() {}

    public Long getId() {
        return _id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public Set<Long> getFollowIds() {
        return followIds;
    }
}
