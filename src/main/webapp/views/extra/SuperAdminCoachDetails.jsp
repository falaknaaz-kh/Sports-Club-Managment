<%if (session.getAttribute("Superadmin_email") == null) {response.sendRedirect("/signin"); } else {%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="includes/header.jsp" />  
	
	<jsp:include page="includes/superadminNav.jsp" />  
	
	
	    <!-- Page Content  -->
    <div id="content">
 <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">

                    <button type="button" id="sidebarCollapse" class="btn btn-info">
                        <i class="fas fa-align-left"></i>
                        <span>Toggle Sidebar</span>
                    </button>
                    <div>
                    	<h3 class="text-info">VENDOR DETAILS</h3>
                    </div>
                   <div>
                    	<p>Welcome 
                    	<% if(session.getAttribute("Superadmin_gender").equals("male")){ %> 
                    		Mr.
                    	<%}else{%> 
                    		Miss.
                    	<%}%> 
                    	<span class="font-weight-bold text-info">${Superadmin_firstname} ${Superadmin_lastname}</span></p>
                    </div>
                </div>
            </nav>


    <div>
    
<nav class="d-flex justify-content-between">

   
     <form class="d-flex"  action="/superadmincoachSearch"  method="post" autocomplete="off">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="user" value="superadmin" />
			<input class="form-control" type="search" name="valueToSearch" placeholder="Value To Search" aria-label="Search" value="${coach_keyword}">
            <button class="btn ml-2 btn-info" type="submit" name="search">Search</button>
        </form>

 <form class="d-flex">
        <button type="button" class="btn btn-info ml-2" name="add_coach" data-toggle="modal" data-target="#AddcoachModal" data-whatever="@mdo">Add coach</button>
    </form>


     <!-- Add User modal -->
     <div class="modal fade" id="AddcoachModal" tabindex="-1" role="dialog" aria-labelledby="AdduserModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Add Coach</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/addcoachForm" modelAttribute="coachForm"  method="POST" enctype= "multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="superadmin" value="superadmin"/>
				<input type="hidden" name="subadmin" value="not" />
                <div class="modal-body">

                    <div class="form-group">
                        <label for="message-text" class="col-form-label">coach Name:</label>
                        <input type="text" class="form-control" placeholder="Coach Name" name="coachname" id="coachname" required>
                    </div>
                    <div class="form-group">
                        
						 <label for="message-text" class="col-form-label">coach Description</label>
                        <select id="coach_desc" name="coach_desc"  class="form-control" required>
							<option value="">Choose the Description</option>
							<option value="Photographer">Photographer</option>
							<option value="DJ">Disc Jockey</option>
							<option value="Makeupartisit">Makeup Artisit</option>
							<option value="Decorator">Decorator</option>
						</select>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">coach Image:</label>
                        <input type="file" class="form-control" name="coach_img" id="coach_img" required>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">coach Price:</label>
						<input  class="form-control"  type="text" name="coach_price"  placeholder="Coach Price" id="coach_price" required>
                    </div>
                   
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">coach Location:</label>
						<textarea class="form-control" name="coach_location" placeholder="Coach Location" id="coach_location"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-info" name="addcoach" >Add coach</button>
                </div>
            </form>
            </div>
        </div>
        </div>

</nav>

<br/>
<br/>

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
                    <th>VENDOR_NAME</th>
                    <th>VENDOR_DESCRIPTION</th>
                    <th>VENDOR_IMG</th>
                    <th>VENDOR_PRICE</th>
                    <th>LOCATION</th>
                    <th>ACTION</th>
                </tr>
            </thead>
                <tbody>
                	 <c:forEach var="allcoach" items="${coachlist}" >
			            <tr>
			            <td>${allcoach.coachname}</td>
			            <td>${allcoach.coach_desc}</td>
			           <td ><img src="data:image/jpeg;base64,${allcoach.coach_img}" class="rounded-circle"width="100" height="100"/></td>
			            <td>${allcoach.coach_price}</td>
			            <td>${allcoach.coach_location}</td>
                        <td class="d-flex">
                            <a class="btn btn-info edit" data-toggle="modal" name="edit_coach" data-target="#EditcoachModal" data-whatever="@mdo">EDIT</a>
					         <input type="hidden" value="${allcoach.id}" id="edit_id">
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
			 <div class="modal fade" id="EditcoachModal" tabindex="-1" role="dialog" aria-labelledby="AdduserModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Edit User</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<form action="/EditcoachForm" modelAttribute="coachEditForm" method="POST" enctype= "multipart/form-data">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" name="superadmin" value="superadmin"/>
						<input type="hidden" name="subadmin" value="not"/>
						<input type="hidden" name="id" id="coach_id">
						<div class="modal-body">
		
							<div class="modal-body">

								<div class="form-group">
									<label for="message-text" class="col-form-label">coach Name:</label>
									<input type="text" class="form-control" placeholder="coachName" name="coachname" id="coachName1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">coach Description:</label>
									<select id="coachDesc1" name="coach_desc"  class="form-control" required>
							<option value="">Choose the Description</option>
							<option value="Photographer">Photographer</option>
							<option value="DJ">Disc Jockey</option>
							<option value="Makeupartisit">Makeup Artisit</option>
							<option value="Decorator">Decorator</option>
						</select>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">coach price:</label>
									<input type="text" class="form-control"  placeholder="coach price Id" name="coach_price" id="price1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">Address:</label>
									<textarea class="form-control" name="coach_location" placeholder="coach location" id="location1"></textarea>
								</div>
								<div class="form-group">
                       			 <label for="message-text" class="col-form-label">coach Image:</label>
                     		   <input type="file" class="form-control" name="coach_img" id="coachImg11" >
                  			  </div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-info" name="Editcoach" >Edit coach</button>
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
                    url: "${pageContext.request.contextPath}/coachfind/"+id, //this is my servlet
                    data: "input=" +$('#ip').val()+"&output="+$('#op').val(),
                    success: function(allcoach){ 
                    		$('#EditcoachModal #coach_id').val(allcoach.id);
                            $('#EditcoachModal #coachName1').val(allcoach.coachname);
							$('#EditcoachModal #coachDesc1').val(allcoach.coach_desc);
							$('#EditcoachModal #location1').val(allcoach.coach_location);
							$('#EditcoachModal #price1').val(allcoach.coach_price);
							$('#EditcoachModal #coachImg11').val(allcoach.coach_img);
							
                    }
                });
            });

        });
    </script>
	
<jsp:include page="includes/footer.jsp" />  
<%}%>