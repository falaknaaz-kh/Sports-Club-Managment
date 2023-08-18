package com.app.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.demo.model.Facility;
import com.app.demo.model.Event;
import com.app.demo.model.Sports;
import com.app.demo.model.User;
import com.app.demo.model.Coach;
import com.app.demo.services.FacilityServices;
import com.app.demo.services.EventServices;
import com.app.demo.services.SportsServices;
import com.app.demo.services.UserServices;
import com.app.demo.services.CoachServices;



@Controller

public class SubAdminController {
	
	@Autowired
	private SportsServices sportsservice;
	
	@Autowired
	private CoachServices coachservice;
	
	@Autowired
	private FacilityServices facilityservice;
	
	@Autowired
	private EventServices eventservice;
	
	@Autowired
	private UserServices userservice;
	
	
	@RequestMapping(value="/subadminfacilitydetails",method=RequestMethod.GET)
	public String subAdminFacilityDetails(ModelMap model) {
		List<Facility> facility=facilityservice.findAll();
		model.addAttribute("facilitylist", facility);
	    return "SubAdminFacilityDetails"; 
	}
	
	@RequestMapping(value="/subadminfacilitySearch",method=RequestMethod.POST)
	public String subadminFacilitySearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Facility> facility=facilityservice.findAll();
			model.addAttribute("facilitylist", facility);
		    return "SubAdminFacilityDetails";    
			
		}
		else {
			model.addAttribute("facility_keyword",searchkey);
			List<Facility> facility=facilityservice.findBykey(searchkey);
			model.addAttribute("facilitylist", facility);
		    return "SubAdminFacilityDetails";   
			
		}
	}
	
	@RequestMapping(value="/subadminsportsdetails",method=RequestMethod.GET)
	public String subAdminSportsDetails(ModelMap model) {
	    List<Sports> sports=sportsservice.findAll();
		model.addAttribute("sportslist",sports);
	    return "SubAdminSportsDetails"; 
	}
	
	@RequestMapping(value="/subadminsportsSearch",method=RequestMethod.POST)
	public String subadminSportsSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Sports> sports=sportsservice.findAll();
			model.addAttribute("sportslist",sports);
		    return "SubAdminSportsDetails"; 
			
		}
		else {
			model.addAttribute("sports_keyword",searchkey);
			List<Sports> sports=sportsservice.findBykey(searchkey);
			model.addAttribute("sportslist",sports);
		    return "SubAdminSportsDetails"; 
			
		}
	}
	@RequestMapping(value="/subadmincoachdetails",method=RequestMethod.GET)
	public String subAdminCoachDetails(ModelMap model) {
		List<Coach> coach=coachservice.findAll();
		model.addAttribute("coachlist",coach);
	    return "SubAdminCoachDetails";  
	}
	
	@RequestMapping(value="/subadmincoachSearch",method=RequestMethod.POST)
	public String subadminCoachSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Coach> coach=coachservice.findAll();
			model.addAttribute("coachlist",coach);
		    return "SubAdminCoachDetails";   
			
		}
		else {
			model.addAttribute("coach_keyword",searchkey);
			List<Coach> coach=coachservice.findBykey(searchkey);
			model.addAttribute("coachlist",coach);
		    return "SubAdminCoachDetails";  
			
		}
	}
	
	@RequestMapping(value="/subadminbookingdetails",method=RequestMethod.GET)
	public String subAdminBookingDetails() {
	    return "SubAdminBookingDetails";  
	}
	
	
	@RequestMapping(value="/subadmineventdetails",method=RequestMethod.GET)
	public String subAdminEventDetails(ModelMap model) {
		List<Event> event=eventservice.findAll();
		model.addAttribute("eventlist",event);
	    return "SubAdminEventDetails";  
	}
	
	@RequestMapping(value="/subadmineventSearch",method=RequestMethod.POST)
	public String subadminEventSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Event> event=eventservice.findAll();
			model.addAttribute("eventlist",event);
		    return "SubAdminEventDetails";    
			
		}
		else {
			model.addAttribute("event_keyword",searchkey);
			List<Event> event=eventservice.findBykey(searchkey);
			model.addAttribute("eventlist",event);
		    return "SubAdminEventDetails";  
			
		}
	}
	
	@RequestMapping(value="/subadminaccount",method=RequestMethod.GET)
	public String subAdminAccount() {
	    return "SubAdminAccount";  
	}
	
	@RequestMapping(value="/editsubadminprofile",method=RequestMethod.POST)
	public String updateUserProfile(@ModelAttribute("subadmineditprofile") User subadmin ,HttpSession session) {
		System.out.println(subadmin);
		
		userservice.updateUserProfile(subadmin.getEmail(),subadmin.getFirstName(),subadmin.getLastName(),subadmin.getGender(),subadmin.getContactno(),subadmin.getAddress(),subadmin.getRole(),subadmin.getPassword(),subadmin.getConfirmPassword(),subadmin.getId());
		session.setAttribute("Subadmin_firstname",subadmin.getFirstName());
		session.setAttribute("Subadmin_lastname", subadmin.getLastName());
		session.setAttribute("Subadmin_email", subadmin.getEmail());
		session.setAttribute("Subadmin_phone", subadmin.getContactno());
		session.setAttribute("Subadmin_address", subadmin.getAddress());
		session.setAttribute("Subadmin_gender", subadmin.getGender());
		session.setAttribute("Subadmin_id", subadmin.getId());
		session.setAttribute("Subadmin_role", subadmin.getRole());
		session.setAttribute("Subadmin_cpassword", subadmin.getConfirmPassword());
		session.setAttribute("Subadmin_password", subadmin.getPassword());
	
		return "redirect:/subadminaccount";
		
	}
	
	 @RequestMapping(value="/subadminlogout",method=RequestMethod.GET)
		public String subadminlogout(HttpSession session) {
		    if (session != null) {
		        // session.removeAttribute(null)
		        session.removeAttribute("Subadmin_firstname");
				session.removeAttribute("Subadmin_lastname");
				session.removeAttribute("Subadmin_email");
				session.removeAttribute("Subadmin_phone");
				session.removeAttribute("Subadmin_address");
				session.removeAttribute("Subadmin_gender");
				session.removeAttribute("Subadmin_id");
				session.removeAttribute("Subadmin_cpassword");
				session.removeAttribute("Subadmin_password");
				session.removeAttribute("Subadmin_role");
		    }
		    return "redirect:/signin"; 
		}
	
}
