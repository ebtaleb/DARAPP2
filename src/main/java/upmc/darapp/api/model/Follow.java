package upmc.darapp.api.model;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "follows", catalog = "TEST")
public class Follow implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

	@Column(name = "user")
	private String user;

    @Column(name = "followed_event_id")
    private int followed_event_id;
    
    public Follow() {}

    public Follow(String u, int e_id) {
        user = u;
        followed_event_id = e_id;
    }

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public int getFollowed_event_id() {
		return followed_event_id;
	}

	public void setFollowed_event_id(int i) {
		this.followed_event_id = i;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String u) {
		this.user = u;
	}

}