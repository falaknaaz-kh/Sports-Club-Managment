package com.app.demo.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.demo.model.Facility;
import com.app.demo.model.Sports;
import com.app.demo.repository.FacilityRepo;


@Service
public class FacilityServices {

	@Autowired
	private FacilityRepo  facilityrepo;
	
	

	public FacilityRepo getFacilityrepo() {
		return facilityrepo;
	}

	public void setFacilityrepo(FacilityRepo facilityrepo) {
		this.facilityrepo = facilityrepo;
	}
	
	public void save(Facility facility) {
		
		System.out.println("saving...");
		facilityrepo.save(facility);
	}
	
	

	public List<Facility> findAll() {
		
		return facilityrepo.findAll();
	}

	public void savefacilitytoDB(MultipartFile file, String facilityname, String facilityDesc, String facilityLoc, int facilityprice) {
		Facility c = new Facility();
		
	c.setFacilityname(facilityname);
	c.setFacility_desc(facilityDesc);
	c.setFacility_location(facilityLoc);
	c.setFacility_price(facilityprice);
	
		
		try {
			c.setFacility_img(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		facilityrepo.save(c);
		
	}

	
	public void deletefacility(int id)
	{
		System.out.println("deleting...");
		facilityrepo.deleteById(id);
	}



	public Facility findById(int id) {
		return facilityrepo.findById(id).orElse(null);
		
	}

	

	public void updateFacilityDetailswithImage(String facilityname, String facilitydesc, String facilityloc, int facilityprice, MultipartFile file, int id) {
		String image="";
		try {
			
			image= Base64.getEncoder().encodeToString(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facilityrepo.updateFacilitywithImage(facilityname, facilitydesc,  facilityloc, facilityprice ,image, id);
		
		
	}

	public void updateFacilityDetails(String facilityname, String facilitydesc, String facilityloc, int facilityprice, int id) {
		facilityrepo.updateFacility(facilityname, facilitydesc, facilityloc, facilityprice, id);
	}

	public long facilitycount() {
		// TODO Auto-generated method stub
		return facilityrepo.count();
	}

	public List<Facility> findBykey(String searchkey) {
		// TODO Auto-generated method stub
		return facilityrepo.findbykey(searchkey);
	}

	
	
}