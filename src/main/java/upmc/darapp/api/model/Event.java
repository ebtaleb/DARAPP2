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

	@Column(name = "name")
	private String name;

	@Column(name = "descr")
	private String descr;

	@Column(name = "address")
	private String address;

    @Column(name = "ev_date")
    private java.sql.Date ev_date;

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

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String a) {
		this.address = a;
	}

    @Type(type="date")
    public java.sql.Date getEv_date() {
        return ev_date;
    }

    @Type(type="date")
    public void setEv_date(java.sql.Date ev_d) {
        this.ev_date = ev_d;
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