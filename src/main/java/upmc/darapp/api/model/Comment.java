package upmc.darapp.api.model;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "comments", catalog = "TEST")
public class Comment implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

	@Column(name = "owner")
	private String owner;

	@Column(name = "content")
	private String content;

    @Column(name = "event_id")
    private int event_id;

    @Column(name = "creation_time")
    @Type(type="timestamp")
    private Date creation_time;

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int i) {
		this.event_id = i;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String o) {
		this.owner = o;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String c) {
		this.content = c;
	}

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date cd) {
        this.creation_time = cd;
    }

}
