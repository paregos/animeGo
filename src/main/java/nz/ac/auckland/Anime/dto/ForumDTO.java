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
    @XmlElement(name="MorderatorID")
    private List<Long> moderators;

    @XmlElementWrapper(name="Comments")
    @XmlElement(name="Comment")
    private List<CommentDTO> comments;

    private Long animeTopicID;

    public ForumDTO() {
    }

    public ForumDTO(List<Long> moderators, List<CommentDTO> comments, Long topic) {
        this.moderators = moderators;
        this.comments = comments;
        this.animeTopicID = topic;
    }

    public ForumDTO(Long _id, List<Long> moderators, List<CommentDTO> comments, Long topic) {
        this._id = _id;
        this.moderators = moderators;
        this.comments = comments;
        this.animeTopicID = topic;
    }

    public Long getId() {
        return _id;
    }

    public List<Long> getModerators() {
        return moderators;
    }

    public void setModerators(List<Long> moderators) {
        this.moderators = moderators;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public Long getAnimeTopicID() {
        return animeTopicID;
    }

    public void setAnimeTopicID(Long animeTopicID) {
        this.animeTopicID = animeTopicID;
    }
}
