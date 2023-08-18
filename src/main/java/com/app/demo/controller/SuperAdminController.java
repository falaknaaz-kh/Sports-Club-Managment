
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

import com.app.demo.model.Event;
import com.app.demo.model.Booking;
import com.app.demo.model.Facility;

import com.app.demo.model.Sports;
import com.app.demo.model.User;
import com.app.demo.model.Coach;
import com.app.demo.services.BookingServices;
import com.app.demo.services.FacilityServices;
import com.app.demo.services.EventServices;
import com.app.demo.services.SportsServices;
import com.app.demo.services.UserServices;
import com.app.demo.services.CoachServices;


@Controller
public class SuperAdminController {
	
	@Autowired
	private SportsServices sportsservice;
	
	@Autowired
	private CoachServices coachservice;
	
	@Autowired
	private FacilityServices facilityservice;
	
	@Autowired
	private EventServices eventservices;
	
	@Autowired
	private UserServices userservice;
	
	
	@Autowired
	private BookingServices	bookingservice;
	
	@RequestMapping(value="/superadminfacilitydetails",method=RequestMethod.GET)
	public String superAdminFacilityDetails(ModelMap model) {
		List<Facility> facility =facilityservice.findAll();
		model.addAttribute("facilitylist", facility);
	    return "SuperAdminFacilityDetails"; 
	}
	
	@RequestMapping(value="/superadminfacilitySearch",method=RequestMethod.POST)
	public String superadminFacilitySearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Facility> facility =facilityservice.findAll();
			model.addAttribute("facilitylist", facility);
		    return "SuperAdminFacilityDetails"; 
		}
		else {
			model.addAttribute("facility_keyword",searchkey);
			List<Facility> facility =facilityservice.findBykey(searchkey);
			model.addAttribute("facilitylist", facility);
		    return "SuperAdminFacilityDetails";  
		}
	}
	@RequestMapping(value="/superadminsportsdetails",method=RequestMethod.GET)
	public String superAdminSportsDetails(ModelMap model) {
		List<Sports> sports = sportsservice.findAll();
		model.addAttribute("sportslist", sports);
	    return "SuperAdminSportsDetails"; 
	}
	
	@RequestMapping(value="/superadminsportsSearch",method=RequestMethod.POST)
	public String superadminSportsSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Sports> sports = sportsservice.findAll();
			model.addAttribute("sportslist", sports);
		    return "SuperAdminSportsDetails";
		}
		else {
			model.addAttribute("sports_keyword",searchkey);
			List<Sports> sports = sportsservice.findBykey(searchkey);
			model.addAttribute("sportslist", sports);
		    return "SuperAdminSportsDetails";   
			
		}
	}
	@RequestMapping(value="/superadmincoachdetails",method=RequestMethod.GET)
	public String superAdminCoachDetails(ModelMap model) {
		List<Coach> coach=coachservice.findAll();
		model.addAttribute("coachlist",coach);		
	    return "SuperAdminCoachDetails";  
	}
	
	@RequestMapping(value="/superadmincoachSearch",method=RequestMethod.POST)
	public String superadminCoachSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Coach> coach=coachservice.findAll();
			model.addAttribute("coachlist",coach);		
		    return "SuperAdminCoachDetails"; 
		}
		else {
			model.addAttribute("coach_keyword",searchkey);
			List<Coach> coach=coachservice.findBykey(searchkey);
			model.addAttribute("coachlist",coach);		
		    return "SuperAdminCoachDetails";  
			
		}
	}
	@RequestMapping(value="/superadminbookingdetails",method=RequestMethod.GET)
	public String superAdminBookingDetails(ModelMap model) {
		List<Booking> booking=bookingservice.findAll();
		model.addAttribute("superadmin_booking",booking);
	    return "SuperAdminBookingDetails";  
	}

	
	@RequestMapping(value="/superadmineventdetails",method=RequestMethod.GET)
	public String superAdminEventDetails(ModelMap model) {
		List<Event> event = eventservices.findAll();
		model.addAttribute("eventlist", event);
	    return "SuperAdminEventDetails";  
	}
	
	
	@RequestMapping(value="/superadmineventSearch",method=RequestMethod.POST)
	public String superadminEventSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Event> event = eventservices.findAll();
			model.addAttribute("eventlist", event);
		    return "SuperAdminEventDetails"; 
		}
		else {
			model.addAttribute("event_keyword",searchkey);
			List<Event> event = eventservices.findBykey(searchkey);
			model.addAttribute("eventlist", event);
		    return "SuperAdminEventDetails"; 
			
		}
	}
	@RequestMapping(value="/superadminaccount",method=RequestMethod.GET)
	public String adminAccount() {
	    return "SuperAdminAccount";  
	}
	
	@RequestMapping(value="/editsuperadminprofile",method=RequestMethod.POST)
	public String updateUserProfile(@ModelAttribute("superadminEditProfile") User superadmin ,HttpSession session) {
		System.out.println(superadmin);
		
		userservice.updateUserProfile(superadmin.getEmail(),superadmin.getFirstName(),superadmin.getLastName(),superadmin.getGender(),superadmin.getContactno(),superadmin.getAddress(),superadmin.getRole(),superadmin.getPassword(),superadmin.getConfirmPassword(),superadmin.getId());
		session.setAttribute("Superadmin_firstname",superadmin.getFirstName());
		session.setAttribute("Superadmin_lastname", superadmin.getLastName());
		session.setAttribute("Superadmin_email", superadmin.getEmail());
		session.setAttribute("Superadmin_phone", superadmin.getContactno());
		session.setAttribute("Superadmin_address", superadmin.getAddress());
		session.setAttribute("Superadmin_gender", superadmin.getGender());
		session.setAttribute("Superadmin_id", superadmin.getId());
		session.setAttribute("Superadmin_role", superadmin.getRole());
		session.setAttribute("Superadmin_cpassword", superadmin.getConfirmPassword());
		session.setAttribute("Superadmin_password", superadmin.getPassword());
	
		return "redirect:/superadminaccount";
		
	}

	
	@RequestMapping(value="/bookcancelbysuperadmin",method= RequestMethod.POST)
	public String UserBookingCancelAdmin(@RequestParam("booking_id") int booking_id)
	{
			bookingservice.bookingcancelByAdmin(booking_id);
			return "redirect:/superadminbookingdetails";
	}
	
	
	@RequestMapping(value="/bookacceptbysuperadmin",method= RequestMethod.POST)
	public String UserBookingAcceptAdmin(@RequestParam("booking_id") int booking_id)
	{
			bookingservice.bookingacceptByAdmin(booking_id);
			return "redirect:/superadminbookingdetails";
	
	}
	
	 @RequestMapping(value="/superadminlogout",method=RequestMethod.GET)
		public String superadminlogout(HttpSession session) {
		    if (session != null) {
		        // session.removeAttribute(null)
		        session.removeAttribute("Superadmin_firstname");
				session.removeAttribute("Superadmin_lastname");
				session.removeAttribute("Superadmin_email");
				session.removeAttribute("Superadmin_phone");
				session.removeAttribute("Superadmin_address");
				session.removeAttribute("Superadmin_gender");
				session.removeAttribute("Superadmin_id");
				session.removeAttribute("Superadmin_cpassword");
				session.removeAttribute("Superadmin_password");
				session.removeAttribute("Superadmin_role");
		    }
		    return "redirect:/signin"; 
		}

}
