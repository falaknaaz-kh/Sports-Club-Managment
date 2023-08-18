package com.app.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.demo.model.Sports;
import com.app.demo.model.Coach;

@Repository
public interface  CoachRepo extends JpaRepository<Coach, Integer> {

	
	@Modifying
	@Transactional
	@Query("Update coach set coachname=?1,coach_desc=?2,coach_location=?3,coach_price=?4 where id=?5 ")
	void updatecoach(String coachname, String coachdesc, String coachloc, int coachprice ,int id);
	
	@Modifying
	@Transactional
	@Query("Update coach set coachname=?1,coach_desc=?2,coach_location=?3, coach_price=?4, coach_img=?5 where id=?6 ")
	public void updatecoachwithImage(String coachName, String coachDesc, String coachlocation, int coachprice, String image, int id);

	@Query("select v from coach v where coachname LIKE %?1% or coach_location LIKE %?1% or coach_price LIKE %?1%")
	List<Coach> findbykey(String searchkey);

}
