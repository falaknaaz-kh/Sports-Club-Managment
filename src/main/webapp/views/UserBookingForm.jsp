<%if (session.getAttribute("User_email") == null) {
	response.sendRedirect("/signin");
} else {%> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/header.jsp" />  

<jsp:include page="includes/userNav.jsp" />  


<!-- Page Content  -->
<div id="content">

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">

			<button type="button" id="sidebarCollapse" class="btn btn-info">
				<i class="fas fa-align-left"></i>
				<span>Toggle Sidebar</span>
			</button>
		   <div>
				<h3 class="text-info">BOOK YOUR EVENTS</h3>
			</div>
			<div>
				<p>Welcome 
				<% if(session.getAttribute("User_gender").equals("male")){ %> 
					Mr.
				<%}else{%> 
					Miss.
				<%}%> 
				<span class="font-weight-bold text-info">${User_firstname} ${User_lastname}</span></p>
			</div>
		</div>
	</nav>

<div>
	   

   
<section class="container ">
  <div class="container">
		<div class="row align-items-center my-5">
				

				<!-- Booking Form -->
				<div class="col-md-12 ">
				   <form action="/makeBookingForm" modelAttribute="makeBookingForm" method="POST">
						   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="user_id" value="${User_id}"/>
						<input type="hidden" name="current_date" id="current_date" value=""/>
						<div class="row">
							<!-- First Name -->
							
								<div class="col-lg-5 col-md-5 col-sm-12 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputEmail1">Email address</label>
										<input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" value="${User_email}" readonly/>
										<small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
									  </div>
								</div>

								<div class="col-lg-5 col-md-5 col-sm-12 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputPassword1">Phone Number</label>
										<input type="tel" class="form-control" id="contactno" name="contactno" placeholder="phoneNumber" value="${User_phone}" readonly>
									  </div>
								</div>
								
								<div class="col-lg-5 col-md-5 col-sm-5 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputPassword1">Select Event Date</label>
										<input type="date" id="event_date" name="event_date" class="form-control" onchange="validatedate();" required>
									</div>
								</div>

								<div class="col-lg-5 col-md-5 col-sm-5 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputPassword1">Select Event	Starting Time</label>
										<input type="time" id="start_at" name="start_at" class="form-control" required>
									</div>
								</div>

								
								<div class="col-lg-10 col-md-10 col-sm-12 mb-2 ml-2">
									<div class="form-group">
										<label for="exampleInputPassword1">Select Event Type</label>
										<select id="event_book" name="event_id" class="form-control" required>
											<option value="">Choose the Event</option>
											<c:forEach var="eventData" items="${event_for_booking}">
												<option id="${eventData.id}" value="${eventData.id}">${eventData.eventname}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="col-lg-5 col-md-5 col-sm-12 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputPassword1">Select Sports</label>
										<select id="sports_book" name="sports_id" class="form-control" required>
											<option value="">Choose the Sports</option>
											<c:forEach var="sportsData" items="${sports_for_booking}" >
												<option id="${sportsData.id}" value="${sportsData.id}">${sportsData.sportsName} - Per Hour Rs.${sportsData.price}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
	
								<div class="col-lg-5 col-md-5 col-sm-12 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputPassword1">Select Facility Type</label>
										<select id="facility_book" name="facility_id" class="form-control" required>
											<option value="">Choose the Facility</option>
											<c:forEach var="facilityData" items="${facility_for_booking}">
												<option id="${facilityData.id}" value="${facilityData.id}">${facilityData.facilityname} - Per Person Rs.${facilityData.facility_price}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								
								
								<div class="col-lg-5 col-md-5 col-sm-5 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputPassword1">Select Event Timing</label>
										<select id="event_maxhour" name="max_total_hour" class="form-control" required>
											<option value="">Choose the Event Timing</option>
											<option value="2">2hours</option>
											<option value="4">4hours</option>
											<option value="6">6hours</option>
											<option value="8">8hours</option>
											<option value="10">10hours</option>
											<option value="12">12hours</option>
											<option value="14">14hours</option>
											<option value="16">16hours</option>
											<option value="18">18hours</option>
											<option value="20">20hours</option>
											<option value="22">22hours</option>
											<option value="24">24hours</option>
										</select>
									</div>
								</div>
								
								<div class="col-lg-5 col-md-5 col-sm-5 mb-2 ml-3">
									<div class="form-group">
										<label for="exampleInputPassword1">Total Number of Teammates/Opponents</label>
										<input type="number" id="no_of_guest" name="no_of_guest" class="form-control" required>
									</div>
								</div>
								
								
								<div class="col-lg-5 col-md-5 col-sm-5">
									<div class="form-group form-check">
										<label>Select Mentor</label>
										<br>
										<select id="photo_book" name="photographer_name_desc" class="form-control" required>
										<option value="">Mentor</option>
										<c:forEach var="coachData" items="${coach_for_booking}">
											<c:if test = "${coachData.coach_desc == 'Photographer'}">
												<option id="${coachData.id}" value="${coachData.coachname}(${coachData.id})">${coachData.coachname} - ${coachData.coach_location} - Rs.${coachData.coach_price}</option>
											</c:if>
										</c:forEach>
										<option id="photo_none" value="none">None</option>
									</select>
									  </div>	  
								</div>

								
								<!-- <div class="col-lg-5 col-md-5 col-sm-5">
									<div class="form-group form-check">
										<label>Select Disc Jockey</label>
										<br>
										<select id="dj_book" name="dj_name_desc" class="form-control" required>
										<option value="">Choose the Disc Jokey</option>
										<c:forEach var="coachData" items="${coach_for_booking}">
											<c:if test = "${coachData.coach_desc == 'DJ'}">
												<option id="${coachData.id}" value="${coachData.coachname}(${coachData.id})">${coachData.coachname} - ${coachData.coach_location}  - Rs.${coachData.coach_price}</option>
											</c:if>
										</c:forEach>
										<option id="dj_none" value="none">None</option>
									</select>
									  </div>	  
								</div>
			
								<div class="col-lg-5 col-md-5 col-sm-5">
									<div class="form-group form-check">
										<label>Select MakeUpArtist</label>
										<br>
										<select id="makeup_book" name="makeupartist_name_desc" class="form-control" required>
										<option value="">Choose the MakeUpArtist</option>
										<c:forEach var="coachData" items="${coach_for_booking}">
											<c:if test = "${coachData.coach_desc == 'Makeupartisit'}">
												<option id="${coachData.id}" value="${coachData.coachname}(${coachData.id})">${coachData.coachname} - ${coachData.coach_location} - Rs.${coachData.coach_price}</option>
											</c:if>
										</c:forEach>
										<option id="makeup_none" value="none">None</option>
									</select>
									  </div>	  
								</div>

								<div class="col-lg-5 col-md-5 col-sm-5">
									<div class="form-group form-check">
										<label>Select Decorator</label>
										<br>
										<select id="decorator_book" name="decorator_name_desc" class="form-control" required>
										<option value="">Choose the Decorator</option>
										<c:forEach var="coachData" items="${coach_for_booking}">
											<c:if test = "${coachData.coach_desc == 'Decorator'}">
												<option id="${coachData.id}" value="${coachData.coachname}(${coachData.id})">${coachData.coachname} - ${coachData.coach_location} -Rs.${coachData.coach_price} </option>
											</c:if>
										</c:forEach>
										<option id="decorator_none" value="none">None</option>
									</select>
									  </div>	  
								</div> -->

				<!-- 				<div class="col-lg-12 col-md-12 col-sm-12 mb-2 ml-5 align-items-center>
									<h1 class="text-muted">INVOICE</h1>
									<p class="text-muted">Sports Price - > <span id="sports_price"></span> <span id="hours"></span>  <span id="total_amt_sports"></span></p>
									<p class="text-muted">Facility Price - > <span id="facility_price"></span>  <span id="guest"></span>  <span id="total_amt_facility"></span></p>
									<p class="text-muted">Coach Price - > <span id="photo_price"></span>  <span id="dj_price"></span> <span id="makeup_price"></span>  <span id="deco_price"></span>  <span id="total_amt_coach"></span></p>
								</div>
								<div class="form-group col-lg-5 mx-auto mb-0 align-items-center">
									
										<span class="btn btn-info btn-block py-2" class="font-weight-bold" name="Calculate" onclick="calc();">Calculate Amount</span>
									
								</div>
								<div class="col-lg-12 col-md-12 col-sm-12 mb-2">
									<div class="form-group">
										<label for="exampleInputPassword1">Total Amount</label>
										<input type="text" class="form-control" id="amt" name="amount" value="0" placeholder="Amount" value="" readonly>
									  </div>
								</div>
			-->
							<!-- Submit Button -->
							<div class="form-group col-lg-12 mx-auto mb-0">
								<button type="submit" class="btn btn-info btn-block py-2" name="adduser" onclick="return calc();">
									<span class="font-weight-bold">Book your Event</span>
								</button>
							</div>
					</div>
			 </form>
			</div>
		</div>
	</div>

</section>
	   
</div>
</div>


<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
var yyyy = today.getFullYear();

today = dd + '/' + mm + '/' + yyyy;
// document.write(today);
document.getElementById("current_date").value=today;

$('#sports_book').change(testMessage);
$('#facility_book').change(testMessage2);
$('#event_maxhour').change(textMessage3);
$('#photo_book').change(textMessage5);
$('#dj_book').change(textMessage6);
$('#makeup_book').change(textMessage7);
$('#decorator_book').change(textMessage8);

$("#no_of_guest").on('input',textMessage4);



var sports_amt=0,facility_amt=0,coach_amt=0,no_of_guest=1,no_of_hours=1,photo=0,makup=0,dj=0,deco=0;

function testMessage(){
	
	var id = $(this).children(":selected").attr("id");
	console.log(id);
	var sports_name="";
	$.ajax({
			type: "GET",
			url: "${pageContext.request.contextPath}/sportsbookfind/"+id, //this is my servlet
			data: "input=" +$('#ip1').val()+"&output="+$('#op1').val(),
			success: function(sports){      
				  //var result=$('#amt').val(sports.price);
				sports_amt=sports.price;
				sports_name=sports.sportsName;
				// alert(sports_amt);
				$('#sports_price').text(sports_amt);
			
				
			}
	});

	var e_date=$("#event_date").val();
	$.ajax({
		type:"GET",
		url: "${pageContext.request.contextPath}/sportsbookingfind/"+id, //this is my servlet
		data: "input=" +$('#ip15').val()+"&output="+$('#op15').val(),
		success: function(book){
			console.log(e_date)
		      
			for(let i=0;i<book.length;i++){
					console.log(book[i].event_date);
					if(e_date==book[i].event_date){
						swal("Alert!", "The Sports "+sports_name+" is already booked at the date "+e_date+" Please choose other sports !", "error");
						$("#sports_book").val("");
					}
			}
		}
	});
}
function testMessage2(){
	
	var id = $(this).children(":selected").attr("id");
	console.log(id);

	var facility_name="";
	$.ajax({
			type: "GET",
			url: "${pageContext.request.contextPath}/facilitybookfind/"+id, //this is my servlet
			data: "input=" +$('#ip2').val()+"&output="+$('#op2').val(),
			success: function(facility){      
				//  $('#amt').val(facility.price);
				  facility_amt=facility.facility_price;
					// alert(facility_amt);
					facility_name=facility.facilityname;
				  $('#facility_price').text(facility_amt)
				  
				//   $('#amt').val(result);
			}
	});


	var e_date=$("#event_date").val();
	$.ajax({
		type:"GET",
		url: "${pageContext.request.contextPath}/facilitybookingfind/"+id, //this is my servlet
		data: "input=" +$('#ip16').val()+"&output="+$('#op16').val(),
		success: function(book){
			console.log(e_date)
		      
			for(let i=0;i<book.length;i++){
					console.log(book[i].event_date);
					if(e_date==book[i].event_date){
						swal("Alert!", "The Facility "+facility_name+" is already booked at the date "+e_date+" Please choose other Facility", "error");
						$("#facility_book").val("");
					}
			}
		}
	});

}

function textMessage3(){
	no_of_hours = $(this).children(":selected").val();
	// alert(no_of_hours);
	$("#hours").text(' X '+no_of_hours);
	
}

function textMessage4(){
	no_of_guest = $("#no_of_guest").val();
	$("#guest").text(' X '+no_of_guest);
}


function textMessage5(){
	
	var id = $(this).children(":selected").attr("id");
	console.log(id);
	var photo_name="";
	if(id=="photo_none"){
		photo=0;
		$('#photo_price').text(photo);
	}	else{	
		$.ajax({
				type: "GET",
				url: "${pageContext.request.contextPath}/coachbookfind/"+id, //this is my servlet
				data: "input=" +$('#ip3').val()+"&output="+$('#op3').val(),
				success: function(Photographer){      
					photo=Photographer.coach_price;
					// alert(photo);
					photo_name=Photographer.coach_name;
					$('#photo_price').text(photo);
					//  $('#amt').val(facility.price);
					//   facility_amt=facility.facility_price;
						// alert(facility_amt);
					//   $('#facility_price').text(facility_amt)
					
					//   $('#amt').val(result);
				}
		});

		var e_date=$("#event_date").val();
		var photo_name_id=$("#"+id+"").val();
		$.ajax({
			type:"GET",
			url: "${pageContext.request.contextPath}/photobookingfind/"+photo_name_id, //this is my servlet
			data: "input=" +$('#ip17').val()+"&output="+$('#op17').val(),
			success: function(photobook){
				console.log(e_date)
				// console.log(photobook)
				for(let i=0;i<photobook.length;i++){
					console.log(photobook[i].event_date);
					if(e_date==photobook[i].event_date){
						swal("Alert!", "The Coach  "+photo_name_id+" is already have an appointment at the date "+e_date+" Please choose other PhotoGrapher", "error");
						$("#photo_book").val("");
					}
			}
			}
		});


	}
}
function textMessage6(){
	
	var id = $(this).children(":selected").attr("id");
	console.log(id);

	if(id=="dj_none"){
		dj=0;
		$('#dj_price').text(' + '+dj);
	}else{
		$.ajax({
				type: "GET",
				url: "${pageContext.request.contextPath}/coachbookfind/"+id, //this is my servlet
				data: "input=" +$('#ip4').val()+"&output="+$('#op4').val(),
				success: function(d_j){      
					dj=d_j.coach_price;
					// alert(dj);
					$('#dj_price').text(' + '+dj);
				}
		});

		var e_date=$("#event_date").val();
		var dj_name_id=$("#"+id+"").val();
		$.ajax({
			type:"GET",
			url: "${pageContext.request.contextPath}/djbookingfind/"+dj_name_id, //this is my servlet
			data: "input=" +$('#ip18').val()+"&output="+$('#op18').val(),
			success: function(djbook){
				console.log(e_date)
				// console.log(photobook)
				for(let i=0;i<djbook.length;i++){
					console.log(djbook[i].event_date);
					if(e_date==djbook[i].event_date){
						swal("Alert!", "The Coach  "+dj_name_id+" is already have an appointment at the date "+e_date+" Please choose other Disc Jockey", "error");
						$("#dj_book").val("");
					}
			}
			}
		});
	}
	
}

function textMessage7(){
	
	var id = $(this).children(":selected").attr("id");
	console.log(id);

	if(id=="makeup_none"){
		makeup=0;
		$('#makeup_price').text(' + '+makeup);
	}
	else{
		$.ajax({
			type: "GET",
			url: "${pageContext.request.contextPath}/coachbookfind/"+id, //this is my servlet
			data: "input=" +$('#ip2').val()+"&output="+$('#op2').val(),
			success: function(makeup_art){      
				makeup=makeup_art.coach_price;
				// alert(makeup);
				$('#makeup_price').text(' + '+makeup);
			}
	});


	var e_date=$("#event_date").val();
		var makeup_name_id=$("#"+id+"").val();
		$.ajax({
			type:"GET",
			url: "${pageContext.request.contextPath}/makeupbookingfind/"+makeup_name_id, //this is my servlet
			data: "input=" +$('#ip18').val()+"&output="+$('#op18').val(),
			success: function(makeupbook){
				console.log(e_date)
				// console.log(photobook)
				for(let i=0;i<makeupbook.length;i++){
					console.log(makeupbook[i].event_date);
					if(e_date==makeupbook[i].event_date){
						swal("Alert!", "The Coach  "+makeup_name_id+" is already have an appointment at the date "+e_date+" Please choose other MakeupArtist", "error");
						$("#makeup_book").val("");
					}
			}
			}
		});
	}
	
}
function textMessage8(){
	
	var id = $(this).children(":selected").attr("id");
	console.log(id);
	if(id=="decorator_none"){
		deco=0;
		$('#deco_price').text(' + '+deco);
	}
	else{
		$.ajax({
			type: "GET",
			url: "${pageContext.request.contextPath}/coachbookfind/"+id, //this is my servlet
			data: "input=" +$('#ip2').val()+"&output="+$('#op2').val(),
			success: function(decorator){      
				deco=decorator.coach_price;
				// alert(deco);
				$('#deco_price').text(' + '+deco);
			}
	});

	var e_date=$("#event_date").val();
		var deco_name_id=$("#"+id+"").val();
		$.ajax({
			type:"GET",
			url: "${pageContext.request.contextPath}/decoratorbookingfind/"+deco_name_id, //this is my servlet
			data: "input=" +$('#ip19').val()+"&output="+$('#op19').val(),
			success: function(decobook){
				console.log(e_date)
				// console.log(photobook)
				for(let i=0;i<decobook.length;i++){
					console.log(decobook[i].event_date);
					if(e_date==decobook[i].event_date){
						swal("Alert!", "The Coach  "+deco_name_id+" is already have an appointment at the date "+e_date+" Please choose other Decorator", "error");
						$("#decorator_book").val("");
					}
			}
			}
		});
	}
}



function calc(){
	var sports=no_of_hours*sports_amt;
	var facility=no_of_guest*facility_amt;
	var coach=photo+dj+makeup+deco;

	var result=sports+facility+coach;

	$('#total_amt_sports').text(' = '+sports);
	$('#total_amt_facility').text(' = '+facility);
	$('#total_amt_coach').text(' = '+coach);
	$('#amt').val(result);
}

function validatedate(){    
	

    today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //As January is 0.
    var yy = today.getFullYear();

    var  e = document.getElementById('event_date');
    
	var  dateformat = e.value.split('-');
    var  cin=dateformat[2];
    var  cinmonth=dateformat[1];
    var  cinyear=dateformat[0];
    if (yy==cinyear && mm==cinmonth && dd<=cin) { 
   		return true;
		   
    }
    else if(yy<cinyear){
        return true;
		
    }
    else if(mm<cinmonth && yy<=cinyear){
        return true;
		
    }
    
    else {    
		alert("Please select valid appointment date from today");
		e.value ='';
    }    
	
}


</script>
<jsp:include page="includes/footer.jsp" /> 

<%}%>