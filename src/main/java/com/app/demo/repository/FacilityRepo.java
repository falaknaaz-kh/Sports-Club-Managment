package com.app.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.demo.model.Facility;
import com.app.demo.model.Sports;

@Repository
public interface  FacilityRepo extends JpaRepository<Facility, Integer> {

	@Modifying
	@Transactional
	@Query("Update facility set facilityname=?1,facility_desc=?2,facility_location=?3,facility_price=?4 where id=?5 ")
	void updateFacility(String facilityname, String facilitydesc, String facilityloc, int facilityprice ,int id);
	
	@Modifying
	@Transactional
	@Query("Update facility set facilityname=?1,facility_desc=?2,facility_location=?3, facility_price=?4, facility_img=?5 where id=?6 ")
	public void updateFacilitywithImage(String facilityName, String facilityDesc, String facilitylocation, int facilityprice, String image, int id);

	@Query("select c from facility c where facilityname LIKE %?1% or facility_price LIKE %?1% or facility_location LIKE %?1%")
	List<Facility> findbykey(String searchkey);



	
}
