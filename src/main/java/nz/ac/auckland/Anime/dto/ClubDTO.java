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

    @XmlElementWrapper(name="members")
    @XmlElement(name="member")
    private Set<Long> members;

    @XmlElementWrapper(name="forums")
    @XmlElement(name="forum")
    private Set<ForumDTO> forums;

    private String name;

    public ClubDTO() {
    }

    public ClubDTO(Set<Long> members, Set<ForumDTO> forums, String name) {
        this.members = members;
        this.forums = forums;
        this.name = name;
    }

    public ClubDTO(Long _id, Set<Long> members, Set<ForumDTO> forums, String name) {
        this._id = _id;
        this.members = members;
        this.forums = forums;
        this.name = name;
    }

    public Long getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getMembers() {
        return members;
    }

    public void setMembers(Set<Long> members) {
        this.members = members;
    }

    public Set<ForumDTO> getForums() {
        return forums;
    }

    public void setForums(Set<ForumDTO> forums) {
        this.forums = forums;
    }
}
