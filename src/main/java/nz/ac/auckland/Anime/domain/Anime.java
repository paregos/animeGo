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

    private int episodes;

    private int year;

    private String synopsis;

    @ManyToMany
    @JoinTable(name="AnimeSequels", joinColumns=@JoinColumn(name="PrequelId"),
            inverseJoinColumns = @JoinColumn(name="SequelId"))
    private Set<Anime> sequels;

    public Anime() {}

    public Anime(String title, int episodes, int year, String synopsis) {
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
    }

    public Anime(String title, int episodes, int year, String synopsis, Set<Anime> sequels) {
        this.title = title;
        this.episodes = episodes;
        this.year = year;
        this.synopsis = synopsis;
        this.sequels = sequels;
    }

    public Anime(Long _id, String title, int episodes, int year, String synopsis, Set<Anime> sequels) {
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

    public int getEpisodes() {
        return episodes;
    }

    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Set<Anime> getSequels() {
        return sequels;
    }
}
