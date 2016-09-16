package nz.ac.auckland.Anime.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long _id;
	
	@Column(unique=true)
	private String username;
	
	private String lastname;
	
	private String firstname;

	@ManyToMany
	@JoinTable(name="Following", joinColumns=@JoinColumn(name="userFollowing"),
			inverseJoinColumns = @JoinColumn(name="userFollowed"))
    private Set<User> followers;
	
	public User(String username, String lastname, String firstname) {
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
       // this.followers = followers;
	}

	public User(String username, String lastname, String firstname, Set<User> followers) {
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
		this.followers = followers;
	}

	public User(Long _id, String username, String lastname, String firstname, Set<User> followers) {
		this._id = _id;
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
		this.followers = followers;
	}

	public User(String username) {
		this(username, null, null);
	}
	
	public User() {}
	
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

    public Set<User> getFollowers() {
        return followers;
    }
}
