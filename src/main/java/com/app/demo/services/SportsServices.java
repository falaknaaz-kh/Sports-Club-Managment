package com.app.demo.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.demo.model.Sports;
import com.app.demo.model.User;
import com.app.demo.repository.SportsRepo;

@Service
public class SportsServices {

		@Autowired
		private SportsRepo sportsrepo;

		public SportsRepo getSportsrepo() {
			return sportsrepo;
		}

		public void setSportsrepo(SportsRepo sportsrepo) {
			this.sportsrepo = sportsrepo;
		}
		public void savesportstoDB(MultipartFile sportsimg1,String sportsName,String sportsdesc, String sportsloc, int sportsPrice ) {
			
			Sports h = new Sports();
			
			h.setSportsName(sportsName);
			h.setSportsDesc(sportsdesc);
			h.setLocation(sportsloc);
			h.setPrice(sportsPrice);
			
			try {
				h.setSportsImg1(Base64.getEncoder().encodeToString(sportsimg1.getBytes()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			sportsrepo.save(h);
		}
		
		public List<Sports> findAll(){
			return sportsrepo.findAll();
		}

		
		public Sports findById(int id) {
			return sportsrepo.findById(id).orElse(null);
		}
		public void deleteSports(int id)
		{
			System.out.println("deleting...");
			sportsrepo.deleteById(id);
		}
		public void updateSportsDetails(String sportsname,String sportsdesc, String location,int price,int id) {
			sportsrepo.updateSports(sportsname, sportsdesc, location, price,id);
		}
		public void updateSportsDetailswithImage(String sportsName, String sportsDesc, String location, int price,MultipartFile file,int id) {
			String image="";
			try {
				
				image= Base64.getEncoder().encodeToString(file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sportsrepo.updateSportswithImage(sportsName, sportsDesc,  location, price ,image, id);
			
		}

		public long sportsCount() {
			// TODO Auto-generated method stub
			return sportsrepo.count();
		}

		public List<Sports> findBykey(String searchkey) {
			// TODO Auto-generated method stub
			return sportsrepo.findbykey(searchkey);
		}
		
}