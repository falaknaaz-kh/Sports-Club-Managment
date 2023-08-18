package com.app.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="facility")
public class Facility {
	

	@GeneratedValue( strategy = GenerationType.AUTO)
	@Id
	private int id;
	
	@Column(name="facilityname")
	private String facilityname;
	
	@Column(name="facility_desc")
	private String facility_desc;
	
	@Column(name="facility_location")
	private String facility_location;
	
	@Column(name="facility_price")
	private int facility_price;
	
	@Column(name="facility_img",columnDefinition = "longblob")
	private String facility_img;

	@OneToMany(mappedBy="facility",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Booking> booking;
	
	public int getId() {
		return id;
	}

	public String getFacility_img() {
		return facility_img;
	}

	public void setFacility_img(String facility_img) {
		this.facility_img = facility_img;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFacilityname() {
		return facilityname;
	}

	public void setFacilityname(String facilityname) {
		this.facilityname = facilityname;
	}

	public String getFacility_desc() {
		return facility_desc;
	}

	public void setFacility_desc(String facility_desc) {
		this.facility_desc = facility_desc;
	}

	public String getFacility_location() {
		return facility_location;
	}

	public void setFacility_location(String facility_location) {
		this.facility_location = facility_location;
	}

	public int getFacility_price() {
		return facility_price;
	}

	public void setFacility_price(int facilityprice) {
		this.facility_price = facilityprice;
	}
	
	@Override
	public String toString() {
		return "Facility [id=" + id + ", facilityname=" + facilityname + ", facility_desc=" + facility_desc + ", facility_location="
				+ facility_location + ", facility_price=" + facility_price + ", facility_img=" + facility_img + "]";
	}

}
