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
import javax.persistence.Table;

import com.mysql.cj.jdbc.Blob;

@Entity(name="sports")
public class Sports {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
    
    @Column(name="sports_name")
	private String sportsName;
    
    @Column(name="sports_desc")
	private String sportsDesc;
    
    @Column(name="sports_img1",columnDefinition = "longblob")
	private String sportsImg1;

    @Column(name="price")
    private int price;
    
    @Column(name="location")
    private String location;

    @OneToMany(mappedBy="sports",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Booking> booking;
    
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSportsName() {
		return sportsName;
	}

	public void setSportsName(String sportsName) {
		this.sportsName = sportsName;
	}

	public String getSportsDesc() {
		return sportsDesc;
	}

	public void setSportsDesc(String sportsDesc) {
		this.sportsDesc = sportsDesc;
	}

	public String getSportsImg1() {
		return sportsImg1;
	}

	public void setSportsImg1(String sportsImg1) {
		this.sportsImg1 = sportsImg1;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Sports [Id=" + id + ", sportsName=" + sportsName + ", sportsDesc=" + sportsDesc + ", sportsImg1=" + sportsImg1
				+ ", price=" + price + ", location=" + location + "]";
	}

	
}
