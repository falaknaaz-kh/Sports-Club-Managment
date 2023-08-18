package com.app.demo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.demo.UserNotFoundException;
import com.app.demo.UserPDFExporter;
import com.app.demo.Utility;
import com.app.demo.model.Booking;
import com.app.demo.model.Facility;
import com.app.demo.model.Event;
import com.app.demo.model.Sports;
import com.app.demo.model.User;
import com.app.demo.model.Coach;
import com.app.demo.services.BookingServices;
import com.app.demo.services.FacilityServices;
import com.app.demo.services.EventServices;
import com.app.demo.services.SportsServices;
import com.app.demo.services.TwillioService;
import com.app.demo.services.UserServices;
import com.app.demo.services.CoachServices;
import com.lowagie.text.DocumentException;

import net.bytebuddy.utility.RandomString;


@Controller

public class UserController {
	
	@Autowired
	private UserServices userservice;
	
	@Autowired
	private SportsServices sportsservice;
	
	@Autowired
	private FacilityServices facilityservice;
	
	@Autowired
	private CoachServices coachservice;
	
	@Autowired
	private EventServices eventservice;
	
	@Autowired
	private BookingServices bookingservice;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	TwillioService twillioService;
	
	
	@Value("${app.twillio.fromPhoneNo}")
	private String from;
	
	@Value("${app.twillio.toPhoneNo}")
	private String to;

	     
//	@GetMapping("/sendSms")
//	public String sendSms() {
//		
//		String body = "Hello. Saairam This is ganapathy blah blah!!";
//		twillioService.sendSms(to, from, body);
//		return "message sent successfully";
//		
//		
//	}
	
	
//	@GetMapping("/makeCall")
//	public String makeVoiceCall() {
//		
//		twillioService.makeCall(from, to);
//		return "call initiated..";
//		
//		
//	}
//	
	
	@RequestMapping(value="/registerForm",method= RequestMethod.POST)
	public String UserRegister(@ModelAttribute("registerForm") User user,Model model)
	{
//		System.out.println(user);
//		if(user.getPassword()!=user.getConfirmPassword()) {
//
//			model.addAttribute("reg_error","Password and confirmpassword mismatch");
//			
//			return "UserRegisteration";
//		}else {
			model.addAttribute("user",user);
//			System.out.println(user.getEmail());
			String Fullname=user.getFirstName()+" "+user.getLastName();
			this.to="+91"+user.getContactno();
			String email=user.getEmail();
			String password=user.getPassword();
			User result=userservice.findByEmail(user.getEmail());
			if(result != null) {
				
				model.addAttribute("reg_error","User Email Already Taken");
				
				return "UserRegisteration";
			}
			else {
				userservice.save(user);
				String body = "Hello "+Fullname+"\n Welcome to EXQUISITE \n Thank you for Registration!!! \n Remember your EmailId and Password \n  Emailid : "+email+" \n Password : "+password;
				twillioService.sendSms(to, from, body);
				
//				System.out.println("Success");
				return "redirect:/signin";
			}	
				
//		}
		
	}
	
	
	@RequestMapping(value="/login-validation",method=RequestMethod.POST)
	public String UserLogin(@ModelAttribute("loginForm") User user,HttpSession session)
	{
		
//		System.out.println(user);
		User userDetail=userservice.findByEmail(user.getEmail());
		
		
		if(userDetail!=null) {
			if(userDetail.getPassword().equals(user.getPassword())) {
//				System.out.println(userDetail.toString());
				
				if(userDetail.getRole().equals("Admin")) {
					session.setAttribute("Admin_firstname",userDetail.getFirstName());
					session.setAttribute("Admin_lastname", userDetail.getLastName());
					session.setAttribute("Admin_email", userDetail.getEmail());
					session.setAttribute("Admin_phone", userDetail.getContactno());
					session.setAttribute("Admin_address", userDetail.getAddress());
					session.setAttribute("Admin_gender", userDetail.getGender());
					session.setAttribute("Admin_id", userDetail.getId());
					session.setAttribute("Admin_cpassword", userDetail.getConfirmPassword());
					session.setAttribute("Admin_password", userDetail.getPassword());
					session.setAttribute("Admin_role", userDetail.getRole());
					return "redirect:/adminhome";
				}
					
				
				else if(userDetail.getRole().equals("User")) {

					session.setAttribute("User_firstname",userDetail.getFirstName());
					session.setAttribute("User_lastname", userDetail.getLastName());
					session.setAttribute("User_email", userDetail.getEmail());
					session.setAttribute("User_phone", userDetail.getContactno());
					session.setAttribute("User_address", userDetail.getAddress());
					session.setAttribute("User_gender", userDetail.getGender());
					session.setAttribute("User_id", userDetail.getId());
					session.setAttribute("User_cpassword", userDetail.getConfirmPassword());
					session.setAttribute("User_password", userDetail.getPassword());
					session.setAttribute("User_role", userDetail.getRole());
					return "redirect:/userhome";
				}
				else if(userDetail.getRole().equals("SubAdmin")) {
					session.setAttribute("Subadmin_firstname",userDetail.getFirstName());
					session.setAttribute("Subadmin_lastname", userDetail.getLastName());
					session.setAttribute("Subadmin_email", userDetail.getEmail());
					session.setAttribute("Subadmin_phone", userDetail.getContactno());
					session.setAttribute("Subadmin_address", userDetail.getAddress());
					session.setAttribute("Subadmin_gender", userDetail.getGender());
					session.setAttribute("Subadmin_id", userDetail.getId());
					session.setAttribute("Subadmin_cpassword", userDetail.getConfirmPassword());
					session.setAttribute("Subadmin_password", userDetail.getPassword());
					session.setAttribute("Subadmin_role", userDetail.getRole());
					return "redirect:/subadminhome";
				}
				else if(userDetail.getRole().equals("SuperAdmin")) {
					session.setAttribute("Superadmin_firstname",userDetail.getFirstName());
					session.setAttribute("Superadmin_lastname", userDetail.getLastName());
					session.setAttribute("Superadmin_email", userDetail.getEmail());
					session.setAttribute("Superadmin_phone", userDetail.getContactno());
					session.setAttribute("Superadmin_address", userDetail.getAddress());
					session.setAttribute("Superadmin_gender", userDetail.getGender());
					session.setAttribute("Superadmin_id", userDetail.getId());
					session.setAttribute("Superadmin_cpassword", userDetail.getConfirmPassword());
					session.setAttribute("Superadmin_password", userDetail.getPassword());
					session.setAttribute("Superadmin_role", userDetail.getRole());
					return "redirect:/superadminhome";
				}
			}
		}
		
		return "redirect:/loginfailed";
		
	}
	
	
	
	@RequestMapping(value="/usernewbooking",method=RequestMethod.GET)
	public String userBookingForm(ModelMap model) {
		
		List<Sports> sports=sportsservice.findAll();
		model.addAttribute("sports_for_booking",sports);
		
		List<Facility> facility=facilityservice.findAll();
		model.addAttribute("facility_for_booking",facility);
		
		List<Coach> coach=coachservice.findAll();
		model.addAttribute("coach_for_booking",coach);
		
		List<Event> event=eventservice.findAll();
		model.addAttribute("event_for_booking",event);
		
	    return "UserBookingForm"; 
	}
	@RequestMapping(value="/userbookingdetails",method=RequestMethod.GET)
	public String userBookingDetails(HttpSession session,ModelMap model) {
		if(session.getAttribute("User_id")==null) {
			return "redirect:/signin";
		}else {
			int id=(int) session.getAttribute("User_id");
			User user=userservice.findById(id);
			
			
			List<Booking> bookings=bookingservice.findAllByUser(user);
			
		
			
//			System.out.println(id);
			
//			System.out.println(bookings);
		    session.setAttribute("User_bookings",bookings);
			return "UserBookingDetails"; 
		}
	}
	@RequestMapping(value="/useraccount",method=RequestMethod.GET)
	public String userAccount() {
	    return "UserAccount";  
	}

	@RequestMapping(value="/EdituserProfile",method=RequestMethod.POST)
	public String updateUserProfile(@ModelAttribute("userEditProfile") User user ,HttpSession session) {
//		System.out.println(user);
		
		userservice.updateUserProfile(user.getEmail(),user.getFirstName(),user.getLastName(),user.getGender(),user.getContactno(),user.getAddress(),user.getRole(),user.getPassword(),user.getConfirmPassword(),user.getId());
		session.setAttribute("User_firstname",user.getFirstName());
		session.setAttribute("User_lastname", user.getLastName());
		session.setAttribute("User_email", user.getEmail());
		session.setAttribute("User_phone", user.getContactno());
		session.setAttribute("User_address", user.getAddress());
		session.setAttribute("User_gender", user.getGender());
		session.setAttribute("User_id", user.getId());
		session.setAttribute("User_role", user.getRole());
		session.setAttribute("User_cpassword", user.getConfirmPassword());
		session.setAttribute("User_password", user.getPassword());
	
		return "redirect:/useraccount";
		
	}

	
	
	@RequestMapping(value="/userfacilitydetails",method=RequestMethod.GET)
	public String userFacilityDetails(ModelMap model) {
		List<Facility> facility= facilityservice.findAll();
		model.addAttribute("facilitylist", facility);
	    return "UserFacilityDetails"; 
	}
	
	@RequestMapping(value="/usersportsdetails",method=RequestMethod.GET)
	public String userSportsDetails(ModelMap  model) {
		List<Sports> sports =sportsservice.findAll();
		model.addAttribute("sportslist", sports);
	    return "UserSportsDetails"; 
	}
	
	@RequestMapping(value="/usercoachdetails",method=RequestMethod.GET)
	public String userCoachDetails(ModelMap model) 
	{
		List<Coach> coach=coachservice.findAll();
		model.addAttribute("coachlist", coach);
	    return "UserCoachDetails";  
	}
	
	
	
	@RequestMapping(value="/usereventdetails",method=RequestMethod.GET)
	public String userEventDetails(ModelMap model) {
		List<Event> event =eventservice.findAll();
		model.addAttribute("eventlist", event);
	    return "UserEventDetails";  
	}

	
	@RequestMapping(value="sportsbookfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sports> sportsPriceDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Sports>(sportsservice.findById(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<Sports>(HttpStatus.BAD_REQUEST);
	    }
		
	}
	
	@RequestMapping(value="facilitybookfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Facility> facilityPriceDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Facility>(facilityservice.findById(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<Facility>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	
	@RequestMapping(value="eventbookfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> eventPriceDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Event>(eventservice.findById(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value="coachbookfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Coach> coachPriceDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Coach>(coachservice.findById(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<Coach>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value="sportsbookingfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> sportsbookingDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<List<Booking>>(bookingservice.findBySportsId(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value="facilitybookingfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> facilitybookingDetails(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<List<Booking>>(bookingservice.findByFacilityId(id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	
	@RequestMapping(value="photobookingfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> photobookingDetails(@PathVariable("id") String name_id) {
		try {
			return new ResponseEntity<List<Booking>>(bookingservice.findByPhotoNameId(name_id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value="djbookingfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> djbookingDetails(@PathVariable("id") String name_id) {
		try {
			return new ResponseEntity<List<Booking>>(bookingservice.findByDjNameId(name_id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value="makeupbookingfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> makeupbookingDetails(@PathVariable("id") String name_id) {
		try {
			return new ResponseEntity<List<Booking>>(bookingservice.findByMakeupNameId(name_id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value="decoratorbookingfind/{id}",method=RequestMethod.GET,produces =MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> decoratorbookingDetails(@PathVariable("id") String name_id) {
		try {
			return new ResponseEntity<List<Booking>>(bookingservice.findByDecoratorNameId(name_id),HttpStatus.OK);
		}
	    catch(Exception e) {
	    	return new ResponseEntity<List<Booking>>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@RequestMapping(value="/makeBookingForm",method= RequestMethod.POST)
	public String UserBooking(@ModelAttribute("makeBookingForm") Booking booking,@RequestParam("user_id") int user_id,@RequestParam("sports_id") int sports_id,@RequestParam("event_id") int event_id,@RequestParam("facility_id") int facility_id)
	{
		
			User user=userservice.findById(user_id);
			Sports sports=sportsservice.findById(sports_id);
			Facility facility=facilityservice.findById(facility_id);
			Event event=eventservice.findById(event_id);
			
			booking.setSports(sports);
			booking.setFacility(facility);
			booking.setEvent(event);
			booking.setUser(user);
//			System.out.println(booking);
			bookingservice.save(booking);
			System.out.println("Success");
			
			return "redirect:/userbookingdetails";
	
	}
	
	
	@RequestMapping(value="/bookcancelbyuser",method= RequestMethod.POST)
	public String UserBookingCancel(@RequestParam("booking_id") int booking_id)
	{
			bookingservice.bookingcancelByUser(booking_id);
			return "redirect:/userbookingdetails";
	
	}
	
	

	
	@GetMapping(value="/userbookingdetails/export")
	public String Bill(@RequestParam("booking_id") int booking_id,HttpServletResponse response) throws DocumentException, IOException {
//	System.out.println("hello");
		Booking booking=	bookingservice.findById(booking_id);
		response.setContentType("application/pdf");
	    DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
	    String currentDateTime = dateFormatter.format(new Date());
	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment; filename=Inovice generated on:" + currentDateTime + ".pdf";
	    response.setHeader(headerKey, headerValue);
	          
	    UserPDFExporter exporter = new UserPDFExporter(booking);
	    exporter.export(response);
		
//		System.out.println(booking);
		
		return null;
		
	}
	
		@GetMapping("/forgot_password")
		public String showForgotPasswordForm() {
		    return "forgot_password_form";
		}
	 
		@PostMapping("/forgot_password")
		public String processForgotPassword(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException {
		    String email = request.getParameter("email");
		    String token = RandomString.make(30);
		     
		    try {
		        userservice.updateResetPasswordToken(token, email);
		        String resetPasswordLink = Utility.getSiteURL(request)   + "/reset_password?token=" + token;
		 
		        sendEmail(email, resetPasswordLink);
		        model.addAttribute("msg", "We have sent a reset password link to your email. Please check.");
		         
		    } catch (UserNotFoundException ex) {
		        model.addAttribute("error", ex.getMessage());
		    }
		         
		    return "forgot_password_form";
		}
	     
		public void sendEmail(String recipientEmail, String link)
		        throws MessagingException, UnsupportedEncodingException {
		    MimeMessage msg = mailSender.createMimeMessage();              
		    MimeMessageHelper helper = new MimeMessageHelper(msg);
		     
		    helper.setFrom("ganapathydaprojects@gmail.com", "EXQUISITE");
		    helper.setTo(recipientEmail);
		     
		    String subject = "Here's the link to reset your password";
		     
		    String content = "<p>Hello,</p>"
		            + "<p>You have requested to reset your password.</p>"
		            + "<p>Click the link below to change your password:</p>"
		            + "<p><a href=\"" + link + "\">Change my password</a></p>"
		            + "<br>"
		            + "<p>Ignore this email if you do remember your password, "
		            + "or you have not made the request.</p>";
		     
		    helper.setSubject(subject);
		     
		    helper.setText(content, true);
		     
		    mailSender.send(msg);
		}
	     
	     
		@GetMapping("/reset_password")
		public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
		    User customer = userservice.getByResetPasswordToken(token);
		    model.addAttribute("token", token);
		     
		    if (customer == null) {
		        model.addAttribute("msg", "Invalid Token");
		        return "forgot_password_form";
		    }
		     
		    return "reset_password_form";
		}
	     
		@PostMapping("/reset_password")
		public String processResetPassword(HttpServletRequest request, Model model) {
		    String token = request.getParameter("token");
		    String password = request.getParameter("password");
		     
		    User customer = userservice.getByResetPasswordToken(token);
		    model.addAttribute("title", "Reset your password");
		     
		    if (customer == null) {
		        model.addAttribute("msg", "Invalid Token");
		        return "forgot_password_form";
		    } else {           
		        userservice.updatePassword(customer, password);
		         
		        model.addAttribute("msg", "You have successfully changed your password.");
		    }
		     
		    return "forgot_password_form";
		}
		
		
		

		 @RequestMapping(value="/userlogout",method=RequestMethod.GET)
			public String userlogout(HttpSession session) {
			    if (session != null) {
			        // session.removeAttribute(null)
			        session.removeAttribute("User_firstname");
					session.removeAttribute("User_lastname");
					session.removeAttribute("User_email");
					session.removeAttribute("User_phone");
					session.removeAttribute("User_address");
					session.removeAttribute("User_gender");
					session.removeAttribute("User_id");
					session.removeAttribute("User_cpassword");
					session.removeAttribute("User_password");
					session.removeAttribute("User_role");
			    }
			    return "redirect:/signin"; 
			}
	
}
