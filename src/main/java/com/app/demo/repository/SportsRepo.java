package com.app.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.demo.model.Sports;

@Repository
public interface SportsRepo extends JpaRepository<Sports,Integer>{
	
		@Modifying
		@Transactional
		@Query("Update sports set sports_name=?1,sports_desc=?2,location=?3,price=?4 where id=?5 ")
		public void updateSports(String sportsname,String sportsdesc, String location,int price,int id);

		@Modifying
		@Transactional
		@Query("Update sports set sports_name=?1,sports_desc=?2,location=?3, price=?4, sports_img1=?5 where id=?6 ")
		public void updateSportswithImage(String sportsName, String sportsDesc, String location, int price, String image, int id);

		@Query("select h from sports h where sports_name LIKE %?1% or price LIKE %?1% or location LIKE %?1%")
		public List<Sports> findbykey(String searchkey);

}
