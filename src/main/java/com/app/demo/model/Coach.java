package com.app.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CollectionId;

@Entity(name="coach")
public class Coach {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;
	
	@Column(name="coachname")
	private String coachname;
	
	@Column(name="coach_desc")
	private String coach_desc;
	
	@Column(name="coach_location")
	private String coach_location;
	
	@Column(name="coach_price")
	private int coach_price;
	
	@Column(name="coach_img",columnDefinition = "longblob")
	private String coach_img;

	public String getCoach_img() {
		return coach_img;
	}

	public void setCoach_img(String coach_img) {
		this.coach_img = coach_img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getCoach_desc() {
		return coach_desc;
	}

	public void setCoach_desc(String coach_desc) {
		this.coach_desc = coach_desc;
	}

	public String getCoach_location() {
		return coach_location;
	}

	public void setCoach_location(String coach_location) {
		this.coach_location = coach_location;
	}

	public int getCoach_price() {
		return coach_price;
	}

	public void setCoach_price(int coachprice) {
		this.coach_price = coachprice;
	}

	@Override
	public String toString() {
		return "Coach [id=" + id + ", coachname=" + coachname + ", coach_desc=" + coach_desc + ", coach_location="
				+ coach_location + ", coach_price=" + coach_price + ", coach_img=" + coach_img + "]";
	}
	

}
