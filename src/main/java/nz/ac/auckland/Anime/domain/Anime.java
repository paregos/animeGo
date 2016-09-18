package nz.ac.auckland.Anime.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Ben on 9/16/2016.
 */
@Entity
public class Anime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long _id;

    private String title;

    private Long episodes;

    private Long year;

    private String synopsis;

    @ManyToMany
    @JoinTable(name="animeSequels", joinColumns=@JoinColumn(name="prequelId"),
            inverseJoinColumns = @JoinColumn(name="sequelId"))
    private Set<Anime> sequels;

    public Anime() {}

    public Anime(String title, Long episodes, Long year, String synopsis) {
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
    }

    public Anime(String title, Long episodes, Long year, String synopsis, Set<Anime> sequels) {
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
        this.sequels = sequels;
    }

    public Anime(Long _id, String title, Long episodes, Long year, String synopsis, Set<Anime> sequels) {
        this._id = _id;
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
        this.sequels = sequels;
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

    public Set<Anime> getSequels() {
        return sequels;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEpisodes(Long episodes) {
        this.episodes = episodes;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setSequels(Set<Anime> sequels) {
        this.sequels = sequels;
    }
}
