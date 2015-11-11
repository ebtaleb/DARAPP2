package upmc.darapp.api.model;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "events", catalog = "TEST")
public class Event implements Serializable {

    @Id 
    @Column(name = "id")
    private int id;

	@Column(name = "owner")
	private String owner;

	@Column(name = "title")
	private String title;

	@Column(name = "descr")
	private String descr;

	@Column(name = "event_type")
	private String event_type;

	@Column(name = "address")
	private String address;

	@Column(name = "path")
    @Type(type="text")
	private String path;

    @Column(name = "start_date")
    @Type(type="date")
    private java.sql.Date start_date;

    @Column(name = "start_time")
    @Type(type="time")
    private java.sql.Time start_time;

	@Column(name = "lat")
	private double lat;

	@Column(name = "lng")
	private double lng;

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String o) {
		this.owner = o;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String n) {
		this.title = n;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String et) {
		this.event_type = et;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String a) {
		this.address = a;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String p) {
		this.path = p;
	}

    public java.sql.Date getStart_date() {
        return start_date;
    }

    public void setStart_date(java.sql.Date sd) {
        this.start_date = sd;
    }

    public java.sql.Time getStart_time() {
        return start_time;
    }

    public void setStart_time(java.sql.Time st) {
        this.start_time = st;
    }

	public double getLat() {
		return lat;
	}

	public void setLat(double i) {
		this.lat = i;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double i) {
		this.lng = i;
	}

}