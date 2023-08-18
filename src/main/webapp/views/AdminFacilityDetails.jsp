<%if (session.getAttribute("Admin_email") == null) {response.sendRedirect("/signin"); } else {%> 
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="includes/header.jsp" />  
	
<jsp:include page="includes/adminNav.jsp" />  

	    <!-- Page Content  -->
        <div id="content">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <button type="button" id="sidebarCollapse" class="btn btn-info">
                        <i class="fas fa-align-left"></i>
                        <span>Toggle Sidebar</span>
                    </button>
                    <div>
                        <h3 class="text-info">FACILITIES DETAILS</h3>
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

        <div>
    
            <div class="d-flex justify-content-between mt-5">
                
        <form class="d-flex"  action="/adminfacilityingSearch"  method="post" autocomplete="off">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			
			<input class="form-control" type="search" name="valueToSearch" placeholder="Value To Search" aria-label="Search" value="${facilitying_keyword}">
            <button class="btn ml-2 btn-info" type="submit" name="search">Search</button>
        </form>
                <form class="d-flex">
                    <button type="button" class="btn btn-info ml-2" name="add_facility" data-toggle="modal" data-target="#AddfacilityModal" data-whatever="@mdo">Add Facility</button>
                </form>

                <!-- Add User modal -->
                <div class="modal fade" id="AddfacilityModal" tabindex="-1" role="dialog" aria-labelledby="AdduserModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Add Facility</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    <form action="/addfacilityForm" modelAttribute="facilityForm"  method="POST" enctype= "multipart/form-data">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <input type="hidden" name="superadmin" value="not" />
                                <input type="hidden" name="subadmin" value="not" />
                        <div class="modal-body">

                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Facility Name:</label>
                                <input type="text" class="form-control" placeholder="Facility Name" name="facilityname" id="facilityname" required>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Facility Description</label>
                                <textarea class="form-control" name="facility_desc" placeholder="Facility Description" id="facility_desc"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Facility Image:</label>
                                <input type="file" class="form-control" name="facility_img" id="facility_img" required>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Facility Price:</label>
                                <input  class="form-control"  type="text" name="facility_price"  placeholder="Facility Price" id="facility_price" required>
                            </div>
                        
                            <div class="form-group">
                                <label for="message-text" class="col-form-label">Facility Location:</label>
                                <textarea class="form-control" name="facility_location" placeholder="Facility Location" id="facility_location"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-info" name="addfacility" >Add Facility</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

<br />
<br />
    <label class="text-info font-weight-bold"> Select No.of.rows to display :</label>
      <select class  ="form-control" name="state" id="maxRows">
            <option value="5000">Show ALL Rows</option>
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="15">15</option>
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="70">70</option>
            <option value="100">100</option>
        </select>


        <div class="table-responsive">
            <table class="content-table table" id="table-id">
                <thead>
                    <tr>
                    <th>FACILITY_NAME</th>
                    <th>FACILITY_DESCRIPTION</th>
                    <th>FACILITY_IMG</th>
                    <th>FACILITY_PRICE</th>
                    <th>LOCATION</th>
                    <th>ACTION</th>
                </tr>
            </thead>
                <tbody>
                	 <c:forEach var="allfacility" items="${facilitylist}" >
			            <tr>
			            <td>${allfacility.facilityname}</td>
			            <td>${allfacility.facility_desc}</td>
			           <td ><img src="data:image/jpeg;base64,${allfacility.facility_img}" class="rounded-circle" width="100" height="100"/></td>
			            <td>${allfacility.facility_price}</td>
			            <td>${allfacility.facility_location}</td>
                        <td class="d-flex">
                            <a class="btn btn-info edit" data-toggle="modal" name="edit_facility" data-target="#EditfacilityModal" data-whatever="@mdo">EDIT</a>
					         <input type="hidden" value="${allfacility.id}" id="edit_id">
                            <a href="/admindeletefacility/${allfacility.id}" class="btn btn-danger ml-2" onclick='return deleteFacilitying()'>DELETE</a>
                        </td>
                        </tr>
                    </c:forEach>
                </tbody>
                    
                </table> 
            </div>
            
              <div class='pagination-container mt-2'>
            <nav>
                <ul class="pagination">
                   <li class="page-item" style="cursor:pointer;" data-page="prev" ><span class="page-link"> < <span class="sr-only">(current)</span></span></li>
                   <!--	Here the JS Function Will Add the Rows -->
                    <li class="page-item" style="cursor:pointer;"  data-page="next" id="prev"><span class="page-link"> > <span class="sr-only">(current)</span></span></li>
                </ul>
            </nav>
        </div>
        
        </div>
        </div>
    </div>
      <!-- Edit User modal -->
			 <div class="modal fade" id="EditfacilityModal" tabindex="-1" role="dialog" aria-labelledby="AdduserModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Edit User</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<form action="/EditfacilityForm" modelAttribute="facilityEditForm" method="POST" enctype= "multipart/form-data">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="superadmin" value="not" />
						<input type="hidden" name="subadmin" value="not" />
						<input type="hidden" name="id" id="facility_id">
						<div class="modal-body">
		
							<div class="modal-body">

								<div class="form-group">
									<label for="message-text" class="col-form-label">facility Name:</label>
									<input type="text" class="form-control" placeholder="facilityName" name="facilityname" id="facilityName1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">facility Description:</label>
									<input type="text" class="form-control" placeholder="facilityDesc" name="facility_desc" id="facilityDesc1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">facility price:</label>
									<input type="text" class="form-control"  placeholder="facility price Id" name="facility_price" id="price1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">Address:</label>
									<textarea class="form-control" name="facility_location" placeholder="facility location" id="location1"></textarea>
								</div>
								<div class="form-group">
                       			 <label for="message-text" class="col-form-label">facility Image:</label>
                     		   <input type="file" class="form-control" name="facility_img" id="facilityImg11" >
                  			  </div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-info" name="Editfacility" >Edit facility</button>
						</div>
					</form>
					</div>
				</div>
				</div>
				
				
	<script type="text/javascript">
        $(document).ready(function() {
            $('table .edit').click(function ()
            {
				var id=$(this).parent().find('#edit_id').val();

				console.log(id)
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/facilityfind/"+id, //this is my servlet
                    data: "input=" +$('#ip').val()+"&output="+$('#op').val(),
                    success: function(allfacility){ 
                    		$('#EditfacilityModal #facility_id').val(allfacility.id);
                            $('#EditfacilityModal #facilityName1').val(allfacility.facilityname);
							$('#EditfacilityModal #facilityDesc1').val(allfacility.facility_desc);
							$('#EditfacilityModal #location1').val(allfacility.facility_location);
							$('#EditfacilityModal #price1').val(allfacility.facility_price);
							$('#EditfacilityModal #facilityImg11').val(allfacility.facility_img);
							
                    }
                });
            });

        });
    </script>


    
<jsp:include page="includes/footer.jsp" />  

<%}%>