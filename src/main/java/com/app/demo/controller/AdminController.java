package com.app.demo.controller;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.app.demo.UserExcelExporter;
import com.app.demo.model.Booking;
import com.app.demo.model.Facility;
import com.app.demo.model.Event;
import com.app.demo.model.Sports;
import com.app.demo.model.User;
import com.app.demo.model.Coach;
import com.app.demo.repository.FacilityRepo;
import com.app.demo.repository.SportsRepo;
import com.app.demo.repository.CoachRepo;
import com.app.demo.services.BookingServices;
import com.app.demo.services.FacilityServices;
import com.app.demo.services.EventServices;
import com.app.demo.services.SportsServices;
import com.app.demo.services.UserServices;
import com.app.demo.services.CoachServices;

@Controller
public class AdminController {
		
	@Autowired
	private SportsServices sportsservice;
	
	@Autowired
	private  UserServices userservice;

	@Autowired
	private FacilityServices facilityservice;
	
	@Autowired
	private CoachServices coachservice;

	@Autowired
	private EventServices eventservice;
	
	@Autowired
	private BookingServices bookingservice;
	

	//User Registration
	@RequestMapping(value="/adduserForm",method= RequestMethod.POST)
	public String UserRegister(@ModelAttribute("registerForm") User user,Model model)
	{
			System.out.println(user);
			
			model.addAttribute("user",user);
		
			userservice.save(user);
			System.out.println("add user Success");
			
			return "redirect:/adminuserdetails";
		
	}
	
	
	//User Table
	@RequestMapping(value="/adminuserdetails",method=RequestMethod.GET)
	public String adminUserDetails(ModelMap model) {
//		String keyword="gana";
		List<User> user=userservice.findAll();
		model.addAttribute("Userlist",user);
	    return "AdminUserDetails";  
	}
	
	@RequestMapping(value="/adminuserSearch",method=RequestMethod.POST)
	public String adminUserSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<User> listuser=userservice.findAll();
			model.addAttribute("Userlist",listuser);
			return "AdminUserDetails"; 
			
		}
		else {
			model.addAttribute("user_keyword",searchkey);
			List<User> listuser=userservice.findBykey(searchkey);
			model.addAttribute("Userlist",listuser);
			return "AdminUserDetails";
		}
	}
	
	
	
	
	
	//User Table Delete
	@RequestMapping(value="/admindeleteuser/{email}",method=RequestMethod.GET)
	public String admindeleteUser(@PathVariable String email) {
		User user=userservice.findByEmail(email);
		System.out.println(user);
		if(user.getEmail()!=null) {
			userservice.deleteUser(user.getId());
			 return "redirect:/adminuserdetails";
		}
		
	    return "redirect:/adminuserdetails";  
	}
	
	

	//Model find and fill for User
		@RequestMapping(value="userfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
		public ResponseEntity<User> adminEditDetails(@PathVariable("id") int id) {
			try {
				return new ResponseEntity<User>(userservice.findById(id),HttpStatus.OK);
			}
		    catch(Exception e) {
		    	return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		    }
			
		}
	
		
		//Edit the user
		@RequestMapping(value="/EdituserForm",method=RequestMethod.POST)
		public String updateUser(@ModelAttribute("userEditForm") User user) {
			System.out.println(user);
			userservice.updateUserDetails(user.getEmail(),user.getFirstName(),user.getLastName(),user.getGender(),user.getContactno(),user.getAddress(),user.getRole(),user.getId());
			return "redirect:/adminuserdetails";
			
		}
		
	
	//Sports Table
	@RequestMapping(value="/adminsportsdetails",method=RequestMethod.GET)
	public String adminSportsDetails(ModelMap model) {
		List<Sports> sports=sportsservice.findAll();
		model.addAttribute("Sportslist",sports);
	    return "AdminSportsDetails"; 
		
	}
	
	@RequestMapping(value="/adminsportsSearch",method=RequestMethod.POST)
	public String adminSportsSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Sports> sports=sportsservice.findAll();
			model.addAttribute("Sportslist",sports);
				return "AdminSportsDetails"; 
			
		}
		else {
			model.addAttribute("sports_keyword",searchkey);
			List<Sports> sports=sportsservice.findBykey(searchkey);
			model.addAttribute("Sportslist",sports);
			return "AdminSportsDetails"; 
			
		}
	}
	//Sports Table Delete
	@RequestMapping(value="/admindeletesports/{id}")
	public String admindeleteSports(@PathVariable int id)
	{
		Sports sports=sportsservice.findById(id);
		System.out.println(sports);
		if(sports.getSportsName()!=null) {
			sportsservice.deleteSports(id);
			return "redirect:/adminsportsdetails";
		}
		return "redirect:/adminsportsdetails";
	}
	
	//Add Sports 
	@RequestMapping(value="/addsportsForm")
	public String savesports(@RequestParam("subadmin") String role1,@RequestParam("superadmin") String role2,@RequestParam("sportsName") String sportsname,@RequestParam("sportsDesc") String sportsDesc,@RequestParam("location") String sportsLoc,@RequestParam("price") int sportsPrice,@RequestParam("sportsImg1") MultipartFile file) {
		sportsservice.savesportstoDB(file, sportsname, sportsDesc, sportsLoc, sportsPrice);
		
		if(role1.equals("subadmin")&& role2.equals("not"))
		{
			return "redirect:/subadminsportsdetails";
		}
		else if(role1.equals("not")&& role2.equals("superadmin"))
		{
			return "redirect:/superadminsportsdetails";
		}
		else {
			return "redirect:/adminsportsdetails";
			}
	}
	
	//Sports find
	@RequestMapping(value="sportsfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sports> adminhoteEditDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Sports>(sportsservice.findById(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<Sports>(HttpStatus.BAD_REQUEST);
	    }
		
	}
	
	//Edit sports
	@RequestMapping(value="/EditsportsForm",method=RequestMethod.POST)
	public String updateSports(@RequestParam("subadmin") String role1,@RequestParam("superadmin") String role2,@RequestParam("sportsName") String sportsname,@RequestParam("sportsDesc") String sportsDesc,@RequestParam("location") String sportsLoc,@RequestParam("price") int sportsPrice,@RequestParam("sportsImg1") MultipartFile file ,@RequestParam("id") int id)  {
		
		if(file.isEmpty())
		{
			sportsservice.updateSportsDetails(sportsname,sportsDesc,sportsLoc,sportsPrice,id);
		}
		else 
		{
			sportsservice.updateSportsDetailswithImage(sportsname,sportsDesc,sportsLoc,sportsPrice,file,id);
		}
		if(role1.equals("subadmin")&& role2.equals("not"))
		{
			return "redirect:/subadminsportsdetails";
		}
		else if(role1.equals("not")&& role2.equals("superadmin"))
		{
			return "redirect:/superadminsportsdetails";
		}
		else 
		{
			return "redirect:/adminsportsdetails";
		}
		
	}
	
	
	
	//Facility Table
	@RequestMapping(value="/adminfacilitydetails",method=RequestMethod.GET)
	public String adminFacilityDetails(Model model) {
		List<Facility> facility= facilityservice.findAll();
		model.addAttribute("facilitylist", facility);
	    return "AdminFacilityDetails"; 
	}
	
	@RequestMapping(value="/adminfacilitySearch",method=RequestMethod.POST)
	public String adminfacilitySearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Facility> facility= facilityservice.findAll();
			model.addAttribute("facilitylist", facility); 
		    
				return "AdminFacilityDetails"; 
			
		}
		else {
			model.addAttribute("facility_keyword",searchkey);
			List<Facility> facility= facilityservice.findBykey(searchkey);
			model.addAttribute("facilitylist", facility);
			
				return "AdminFacilityDetails"; 
			
		}
	}
	
	//Delete Facility
	@RequestMapping(value="/admindeletefacility/{id}")
	public String admindeleteFacility(@PathVariable int id)
	{
		Facility c=facilityservice.findById(id);
		if(c.getFacilityname()!=null) {
			facilityservice.deletefacility(id);
			return "redirect:/adminfacilitydetails";
		}
		return "redirect:/adminfacilitydetails";
	}
	
	
	//Add Facility
	@RequestMapping(value="/addfacilityForm")
	public String saveFacility(@RequestParam("subadmin") String role1,@RequestParam("superadmin") String role2,@RequestParam("facilityname") String facilityname,@RequestParam("facility_desc") String facilityDesc,@RequestParam("facility_location") String facilityLoc,@RequestParam("facility_price") int facilityprice,@RequestParam("facility_img") MultipartFile file) {
		facilityservice.savefacilitytoDB(file, facilityname, facilityDesc, facilityLoc, facilityprice);
		if(role1.equals("subadmin")&& role2.equals("not"))
		{
			return "redirect:/subadminfacilitydetails";
		}
		else if(role1.equals("not")&& role2.equals("superadmin"))
		{
			return "redirect:/superadminfacilitydetails";
		}
		else {
			return "redirect:/adminfacilitydetails";

			
		}
	}
	
	@RequestMapping(value="facilityfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Facility> adminfacilityEditDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Facility>(facilityservice.findById(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<Facility>(HttpStatus.BAD_REQUEST);
	    }
		
	}
	
	@RequestMapping(value="/EditfacilityForm",method=RequestMethod.POST)
	public String updateFacility(@RequestParam("subadmin") String role1,@RequestParam("superadmin") String role2,@RequestParam("facilityname") String facilityname,@RequestParam("facility_desc") String facilitydesc,@RequestParam("facility_location") String facilityloc,@RequestParam("facility_price") int facilityprice,@RequestParam("facility_img") MultipartFile file ,@RequestParam("id") int id)  {
		
		if(file.isEmpty())
		{
			facilityservice.updateFacilityDetails(facilityname,facilitydesc,facilityloc,facilityprice,id);
		}
		else {
			facilityservice.updateFacilityDetailswithImage(facilityname,facilitydesc,facilityloc,facilityprice,file,id);
		}
		if(role1.equals("subadmin")&& role2.equals("not"))
		{
			return "redirect:/subadminfacilitydetails";
		}
		else if(role1.equals("not")&& role2.equals("superadmin"))
		{
			return "redirect:/superadminfacilitydetails";
		}
		else {
			return "redirect:/adminfacilitydetails";

			
		}
	}
	
	//Coach Table
	@RequestMapping(value="/admincoachdetails",method=RequestMethod.GET)
	public String adminCoachDetails(ModelMap model) {
		List<Coach> coach=coachservice.findAll();
		model.addAttribute("coachlist",coach);
	    return "AdminCoachDetails";  
	}
	
	@RequestMapping(value="/admincoachSearch",method=RequestMethod.POST)
	public String admincoachSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
		
		System.out.println(searchkey);
		if(searchkey.equals("")) {
			List<Coach> coach=coachservice.findAll();
			model.addAttribute("coachlist",coach);
			
				return "AdminCoachDetails"; 
			 
		}
		else {
			model.addAttribute("coach_keyword",searchkey);
			List<Coach> coach=coachservice.findBykey(searchkey);
			model.addAttribute("coachlist",coach);
			
				return "AdminCoachDetails"; 
			
		}
	}
	
	
	//Delete Coach
	@RequestMapping(value="/admindeletecoach/{id}")
	public String admindeleteCoach(@PathVariable int id ) {
		Coach v =coachservice.findById(id);
		System.out.println(v);
		coachservice.deletecoach(id);
		return "redirect:/admincoachdetails";
	}
	
	//Add Coach
	@RequestMapping(value="/addcoachForm")
	public String saveCoach(@RequestParam("subadmin") String role1 ,@RequestParam("superadmin") String role2,@RequestParam("coachname") String coachname,@RequestParam("coach_desc") String coachDesc,@RequestParam("coach_location") String coachLoc,@RequestParam("coach_price") int coachprice,@RequestParam("coach_img") MultipartFile file) {
		coachservice.savecoachtodb(file, coachname,coachDesc,coachLoc,coachprice);
		if(role1.equals("subadmin")&& role2.equals("not"))
		{
			return "redirect:/subadmincoachdetails";
		}
		else if(role1.equals("not")&& role2.equals("superadmin"))
		{
			return "redirect:/superadmincoachdetails";
		}
		else {
			return "redirect:/admincoachdetails";
		}
		
	}
	
	@RequestMapping(value="coachfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Coach> admincoachEditDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Coach>(coachservice.findById(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<Coach>(HttpStatus.BAD_REQUEST);
	    }
		
	}
	
	@RequestMapping(value="/EditcoachForm",method=RequestMethod.POST)
	public String updatecoach(@RequestParam("subadmin") String role1,@RequestParam("superadmin") String role2,@RequestParam("coachname") String coachname,@RequestParam("coach_desc") String coachdesc,@RequestParam("coach_location") String coachloc,@RequestParam("coach_price") int coachprice,@RequestParam("coach_img") MultipartFile file ,@RequestParam("id") int id)  {
		if(file.isEmpty())
		{
			coachservice.updatecoachDetails(coachname,coachdesc,coachloc,coachprice,id);
		}
		else {
			coachservice.updateCoachDetailswithImage(coachname,coachdesc,coachloc,coachprice,file,id);
		}
		
		if(role1.equals("subadmin")&& role2.equals("not"))
		{
			return "redirect:/subadmincoachdetails";
		}
		else if(role1.equals("not")&& role2.equals("superadmin"))
		{
			return "redirect:/superadmincoachdetails";
		}
		else {
			return "redirect:/admincoachdetails";

			
		}
		
	}

	
	
	
	//Booking details
	@RequestMapping(value="/adminbookingdetails",method=RequestMethod.GET)
	public String adminBookingDetails(ModelMap model) {
		List<Booking> booking=bookingservice.findAll();
		model.addAttribute("admin_booking",booking);
	    return "AdminBookingDetails";  
	}

	//Admin Account
	@RequestMapping(value="/adminaccount",method=RequestMethod.GET)
	public String adminAccount(HttpSession session) {
		System.out.println(session.getAttribute("Admin_email"));
	    return "AdminAccount";  
	}
	
	@RequestMapping(value="/editadminprofile",method=RequestMethod.POST)
	public String updateAdminProfile(@ModelAttribute("adminEditProfile") User admin ,HttpSession session) {
		System.out.println(admin);
		
		userservice.updateUserProfile(admin.getEmail(),admin.getFirstName(),admin.getLastName(),admin.getGender(),admin.getContactno(),admin.getAddress(),admin.getRole(),admin.getPassword(),admin.getConfirmPassword(),admin.getId());
		session.setAttribute("Admin_firstname",admin.getFirstName());
		session.setAttribute("Admin_lastname", admin.getLastName());
		session.setAttribute("Admin_email", admin.getEmail());
		session.setAttribute("Admin_phone", admin.getContactno());
		session.setAttribute("Admin_address", admin.getAddress());
		session.setAttribute("Admin_gender", admin.getGender());
		session.setAttribute("Admin_id", admin.getId());
		session.setAttribute("Admin_role", admin.getRole());
		session.setAttribute("Admin_cpassword", admin.getConfirmPassword());
		session.setAttribute("Admin_password", admin.getPassword());
	
	
		return "redirect:/adminaccount";
		
	}
	
	
	//Event Table
		@RequestMapping(value="/admineventdetails",method=RequestMethod.GET)
		public String adminEventDetails(ModelMap model) {
			List<Event> event=eventservice.findAll();
			model.addAttribute("eventlist",event);
		    return "AdminEventDetails"; 
			
		}
		
		@RequestMapping(value="/admineventSearch",method=RequestMethod.POST)
		public String adminEventSearch(@RequestParam("valueToSearch") String searchkey,ModelMap model) {
			
			System.out.println(searchkey);
			if(searchkey.equals("")) {
				List<Event> event=eventservice.findAll();
				model.addAttribute("eventlist",event);
				
					return "AdminEventDetails"; 
				
			      
			}
			else {
				model.addAttribute("event_keyword",searchkey);
				List<Event> event=eventservice.findBykey(searchkey);
				model.addAttribute("eventlist",event);
				
					return "AdminEventDetails"; 
				
			    
			}
		}
		
		@RequestMapping(value="/admindeleteevent/{id}")
		public String admindeleteEvent(@PathVariable int id ) {
			Event v =eventservice.findById(id);
			System.out.println(v);
			eventservice.deleteevent(id);
			return "redirect:/admineventdetails";
		}
		
		
	//Add event
	@RequestMapping(value="/addeventForm")
	public String saveevent(@RequestParam("subadmin") String role1,@RequestParam("superadmin") String role2,@RequestParam("eventname") String eventname,@RequestParam("event_desc") String eventDesc,@RequestParam("event_img") MultipartFile file) {
		eventservice.saveeventtoDB(file, eventname,eventDesc);
			if(role1.equals("subadmin")&& role2.equals("not"))
			{
				return "redirect:/subadmineventdetails";
			}
			else if(role1.equals("not")&& role2.equals("superadmin"))
			{
				return "redirect:/superadmineventdetails";
			}
			else 
			{
				return "redirect:/admineventdetails";
			}
			
	}
		@RequestMapping(value="eventfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
		public ResponseEntity<Event> admineventEditDetails(@PathVariable("id") int id) {
			try {
				return new ResponseEntity<Event>(eventservice.findById(id),HttpStatus.OK);
			}
		    catch(Exception e) {
		    	return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
		    }
			
		}
		
		@RequestMapping(value="/EditeventForm",method=RequestMethod.POST)
		public String updateevent(@RequestParam("subadmin") String role1,@RequestParam("superadmin") String role2,@RequestParam("eventname") String eventname,@RequestParam("event_desc") String eventdesc,@RequestParam("event_img") MultipartFile file ,@RequestParam("id") int id)  {
			
			if(file.isEmpty())
			{
				eventservice.updateeventDetails(eventname,eventdesc,id);
			}
			else {
				eventservice.updateeventDetailswithImage(eventname,eventdesc,file,id);
			}
			if(role1.equals("subadmin")&& role2.equals("not"))
			{
				return "redirect:/subadmineventdetails";
			}
			else if(role1.equals("not")&& role2.equals("superadmin"))
			{
				return "redirect:/superadmineventdetails";
			}
			else {
				return "redirect:/admineventdetails";
	
				
			}
			
		}
		
		
		
		@RequestMapping(value="/bookcancelbyadmin",method= RequestMethod.POST)
		public String UserBookingCancelAdmin(@RequestParam("booking_id") int booking_id)
		{
				bookingservice.bookingcancelByAdmin(booking_id);
				return "redirect:/adminbookingdetails";
		
		}
		
		
		@RequestMapping(value="/bookacceptbyadmin",method= RequestMethod.POST)
		public String UserBookingAcceptAdmin(@RequestParam("booking_id") int booking_id)
		{
				bookingservice.bookingacceptByAdmin(booking_id);
				return "redirect:/adminbookingdetails";
		
		}
		
		
		 @GetMapping("/downloadExcel")
		    public void exportToExcel(HttpServletResponse response) throws IOException {
		        response.setContentType("application/octet-stream");
		        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		        String currentDateTime = dateFormatter.format(new Date());
		         
		        String headerKey = "Content-Disposition";
		        String headerValue = "attachment; filename=BookingDetails_" + currentDateTime + ".xlsx";
		        response.setHeader(headerKey, headerValue);
		         
		        List<Booking> bookings =bookingservice.findAllandSortBy();
		         
//		        System.out.println(listUsers);
		        UserExcelExporter excelExporter = new UserExcelExporter(bookings);
		         
		        excelExporter.export(response);    
		    }  
		 
		 
		 @RequestMapping(value="/adminlogout",method=RequestMethod.GET)
			public String adminlogout(HttpSession session) {
			    if (session != null) {
			        // session.removeAttribute(null)
			        session.removeAttribute("Admin_firstname");
					session.removeAttribute("Admin_lastname");
					session.removeAttribute("Admin_email");
					session.removeAttribute("Admin_phone");
					session.removeAttribute("Admin_address");
					session.removeAttribute("Admin_gender");
					session.removeAttribute("Admin_id");
					session.removeAttribute("Admin_cpassword");
					session.removeAttribute("Admin_password");
					session.removeAttribute("Admin_role");
			    }
			    return "redirect:/signin"; 
			}

}
