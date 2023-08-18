<%if (session.getAttribute("Admin_email") == null) {response.sendRedirect("/signin"); } else {%> 
<jsp:include page="includes/header.jsp" />  
	
	<jsp:include page="includes/adminNav.jsp" />  
	
	<style>
    .card:hover {
      transform: scale(1.05);
      box-shadow: 0 10px 20px rgba(0, 0, 0, .12), 0 4px 8px rgba(0, 0, 0, .06);
      transition: 0.3s ease-in-out;
      cursor:pointer;
  }
    </style>
	    <!-- Page Content  -->
        <div id="content">

             <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">

                    <button type="button" id="sidebarCollapse" class="btn btn-info">
                        <i class="fas fa-align-left"></i>
                        <span>Toggle Sidebar</span>
                    </button>
                   <div>
                    	<h3 class="text-info">ADMIN DASHBOARD</h3>
                    </div>
                   <div>
                    	<p>Welcome 
                    	<% if(session.getAttribute("Admin_gender").equals("male")){ %> 
                    		Mr.
                    	<%}else{%> 
                    		Miss.
                    	<%}%> 
                    	<span class="font-weight-bold text-info">${Admin_firstname} ${Admin_lastname}</span></p>
                    </div>
                </div>
            </nav>

<link rel="stylesheet" type="text/css" href="https://pixinvent.com/stack-responsive-bootstrap-4-admin-template/app-assets/css/bootstrap-extended.min.css">
<link rel="stylesheet" type="text/css" href="https://pixinvent.com/stack-responsive-bootstrap-4-admin-template/app-assets/fonts/simple-line-icons/style.min.css">
<link rel="stylesheet" type="text/css" href="https://pixinvent.com/stack-responsive-bootstrap-4-admin-template/app-assets/css/colors.min.css">


<div class="grey-bg container-fluid">
  <section id="minimal-statistics">
    <div class="row">
      <div class="col-12 mt-3 mb-1">
        <h4 class="text-uppercase">Your Controls, your power</h4>
          <p>Your record</p>
      </div>
    </div>
    <div class="row">
      <div class="col-xl-3 col-sm-6 col-12"> 
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="align-self-center">
                  <i class="fas fa-users primary fa-3x"></i>
                </div>
                <div class="media-body text-right">
                  <h3 class="success">${admin_user_count}</h3>
                  <span>Total Users</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xl-3 col-sm-6 col-12">
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="align-self-center">
                  <img alt="" src="https://th.bing.com/th/id/OIP.Z_CeDFSFgtSs7C_RtbndjQHaHa?pid=ImgDet&rs=1" style="height: 60px" width="60px">                </div>
                <div class="media-body text-right">
                  <h3 class="danger">${admin_hotel_count}</h3>
                  <span>Total Sports</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xl-3 col-sm-6 col-12">
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="align-self-center">
                  <img alt="" src="https://clipground.com/images/sports-arena-clipart-4.jpg" height="60px" width="60px">
                </div>
                <div class="media-body text-right">
                  <h3 class="primary">${admin_event_count}</h3>
                  <span>Total Events</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xl-3 col-sm-6 col-12">
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="align-self-center">
                  <img alt="" src="https://cdn4.iconfinder.com/data/icons/hotel-facilities-1/512/fitness-512.png" height="60px" width="60px">
                </div>
                <div class="media-body text-right">
                  <h3 class="warning">${admin_facilitying_count}</h3>
                  <span>Total Facility</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  
    <div class="row">
      <div class="col-xl-3 col-sm-6 col-12">
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="media-body text-left">
                  <h3 class="danger">${admin_vendor_count}</h3>
                  <span>Total Coach</span>
                </div>
                <div class="align-self-center">
                 <img alt="" src="https://cdn4.iconfinder.com/data/icons/basketball-crayons-vol-1/256/Female_Coach-512.png" height="60px" width="60px" >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      
      <div class="col-xl-3 col-sm-6 col-12">
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="media-body text-left">
                  <h3 class="success">${admin_booking_count}</h3>
                  <span>Total Booking</span>
                </div>
                <div class="align-self-center">
                  <i class="fas fa-calendar-check info fa-3x"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
 
    <!--     <div class="col-xl-3 col-sm-6 col-12">
      <div class="card">
        <div class="card-content">
          <div class="card-body">
            <div class="media d-flex">
              <div class="media-body text-left">
                <h3 class="warning">${admin_bookingpending_count}</h3>
                <span>Pending Bookings</span>
              </div>
              <div class="align-self-center">
                <i class="fal fa-calendar-check info fa-3x"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
      <div class="col-xl-3 col-sm-6 col-12">
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="media-body text-left">
                  <h3 class="warning">${admin_bookingpaid_count}</h3>
                  <span>Paid Bookings</span>
                </div>
                <div class="align-self-center">
                  <i class="fab fa-paypal secondary fa-3x"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
       
      
       
       
      <div class="col-xl-3 col-sm-6 col-12">
        <div class="card">
          <div class="card-content">
            <div class="card-body">
              <div class="media d-flex">
                <div class="media-body text-left">
                  <h3 class="primary">${admin_bookingunpaid_count}</h3>
                  <span>UnPaid Bookings</span>
                </div>
                <div class="align-self-center">
                  <i class="fab fa-amazon-pay primary fa-3x"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    <div class="row">
    

    
    
    <div class="col-xl-3 col-sm-6 col-12">
      <div class="card">
        <div class="card-content">
          <div class="card-body">
            <div class="media d-flex">
              <div class="media-body text-left">
                <h3 class="warning">${admin_bookingcancelbyadmin_count}</h3>
                <span>Bookings Cancelled by Admin</span>
              </div>
              <div class="align-self-center">
                <i class="fal fa-calendar-check warning fa-3x"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-xl-3 col-sm-6 col-12">
      <div class="card">
        <div class="card-content">
          <div class="card-body">
            <div class="media d-flex">
              <div class="media-body text-left">
                <h3 class="danger">${admin_bookingcancelbyuser_count}</h3>
                <span>Bookings Cancelled by User</span>
              </div>
              <div class="align-self-center">
                <i class="fad fa-calendar-check danger fa-3x"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div> -->


	

    

  </div>


  </div>

  </div>
</section>
</div>  
 

   
  </div>
</div>
</div>
</div>
	
	

<jsp:include page="includes/footer.jsp" />  
<%}%>