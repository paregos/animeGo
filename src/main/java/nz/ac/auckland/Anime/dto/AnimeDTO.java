package nz.ac.auckland.Anime.dto;
import javax.xml.bind.annotation.*;
import java.util.Set;

/**
 * Created by Ben on 9/16/2016.
 */
@XmlRootElement(name="Anime")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimeDTO {

    private Long _id;

    private String title;

    private Long episodes;

    private Long year;

    private String synopsis;

    @XmlElementWrapper(name="sequelIds")
    @XmlElement(name="sequelId")
    private Set<Long> sequelIds;

    public AnimeDTO() {}

    public AnimeDTO(String title, Long episodes, Long year, String synopsis) {
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
    }

    public AnimeDTO(String title, Long episodes, Long year, String synopsis, Set<Long> sequels) {
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
        this.sequelIds = sequels;
    }

    public AnimeDTO(Long _id, String title, Long episodes, Long year, String synopsis, Set<Long> sequelIds) {
        this._id = _id;
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
        this.sequelIds = sequelIds;
    }

    public Long getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public Long getEpisodes() {
        return episodes;
    }

    public Long getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Set<Long> getSequelIds() {
        return sequelIds;
    }

}
