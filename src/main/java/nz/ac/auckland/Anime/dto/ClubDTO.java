package nz.ac.auckland.Anime.dto;

import javax.xml.bind.annotation.*;
import java.util.Set;

/**
 * Created by Ben on 9/17/2016.
 */
@XmlRootElement(name="Club")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClubDTO {

    private Long _id;

    @XmlElementWrapper(name="Members")
    @XmlElement(name="member")
    private Set<UserDTO> members;

    @XmlElementWrapper(name="Forums")
    @XmlElement(name="forum")
    private Set<ForumDTO> forums;

    public ClubDTO() {
    }

    public ClubDTO(Set<UserDTO> members, Set<ForumDTO> forums) {
        this.members = members;
        this.forums = forums;
    }

    public ClubDTO(Long _id, Set<UserDTO> members, Set<ForumDTO> forums) {
        this._id = _id;
        this.members = members;
        this.forums = forums;
    }

    public Long getId() {
        return _id;
    }

    public Set<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<UserDTO> members) {
        this.members = members;
    }

    public Set<ForumDTO> getForums() {
        return forums;
    }

    public void setForums(Set<ForumDTO> forums) {
        this.forums = forums;
    }
}
