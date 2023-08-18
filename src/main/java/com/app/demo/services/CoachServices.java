package com.app.demo.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.app.demo.model.Coach;

import com.app.demo.repository.CoachRepo;

@Service
public class CoachServices {

	@Autowired
	private CoachRepo  coachrepo;
	
	

	public CoachRepo getCoachrepo() {
		return coachrepo;
	}

	public void setCoachrepo(CoachRepo sportsrepo) {
		this.coachrepo = sportsrepo;
	}
	
	public void save(Coach coach) {
		
		System.out.println("saving...");
		coachrepo.save(coach);
	}
	
	

	public List<Coach> findAll() {
		
		return coachrepo.findAll();
	}

	public void savecoachtodb(MultipartFile file, String coachname, String coachDesc, String coachLoc,
			int coachprice) {
		Coach v= new Coach();
		v.setCoachname(coachname);
		v.setCoach_desc(coachDesc);
		v.setCoach_location(coachLoc);
		v.setCoach_price(coachprice);
		
			
			try {
				v.setCoach_img(Base64.getEncoder().encodeToString(file.getBytes()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			coachrepo.save(v);
		
	}
	public void deletecoach(int id)
	{
		System.out.println("deleting...");
		coachrepo.deleteById(id);
	}

	public Coach findById(int id) {
		return coachrepo.findById(id).orElse(null);
	}
	
	public void updateCoachDetailswithImage(String coachname, String coachdesc, String coachloc, int coachprice, MultipartFile file, int id) {
		String image="";
		try {
			
			image= Base64.getEncoder().encodeToString(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		coachrepo.updatecoachwithImage(coachname, coachdesc,  coachloc, coachprice ,image, id);
		
		
		}
	public void updatecoachDetails(String coachname, String coachdesc, String coachloc, int coachprice, int id) {
		coachrepo.updatecoach(coachname, coachdesc, coachloc, coachprice, id);
	}

	public long coachcount() {
		// TODO Auto-generated method stub
		return coachrepo.count();
	}

	public List<Coach> findBykey(String searchkey) {
		// TODO Auto-generated method stub
		return coachrepo.findbykey(searchkey);
	}


	
}
