package nz.ac.auckland.Anime.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Ben on 9/17/2016.
 */
@XmlRootElement(name="Forum")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForumDTO {

    private Long _id;

    @XmlElementWrapper(name="Morderators")
    @XmlElement(name="Morderator")
    private List<UserDTO> moderators;

    @XmlElementWrapper(name="Comments")
    @XmlElement(name="Comment")
    private List<CommentDTO> comments;

    private AnimeDTO animeTopic;

    public ForumDTO() {
    }

    public ForumDTO(List<UserDTO> moderators, List<CommentDTO> comments, AnimeDTO topic) {
        this.moderators = moderators;
        this.comments = comments;
        this.animeTopic = topic;
    }

    public ForumDTO(Long _id, List<UserDTO> moderators, List<CommentDTO> comments, AnimeDTO topic) {
        this._id = _id;
        this.moderators = moderators;
        this.comments = comments;
        this.animeTopic = topic;
    }

    public Long getId() {
        return _id;
    }

    public List<UserDTO> getModerators() {
        return moderators;
    }

    public void setModerators(List<UserDTO> moderators) {
        this.moderators = moderators;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public AnimeDTO getAnimeTopic() {
        return animeTopic;
    }

    public void setAnimeTopic(AnimeDTO animeTopic) {
        this.animeTopic = animeTopic;
    }
}
