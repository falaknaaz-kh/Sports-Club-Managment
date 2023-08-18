<%if (session.getAttribute("Admin_email") == null) {response.sendRedirect("/signin"); } else {%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

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
                    	<h3 class="text-info">Sports DETAILS</h3>
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
      
<div class="d-flex justify-content-between">
    
   <form class="d-flex"  action="/adminsportsSearch"  method="post" autocomplete="off">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		
			<input class="form-control" type="search" name="valueToSearch" placeholder="Value To Search" aria-label="Search" value="${sports_keyword}">
            <button class="btn ml-2 btn-info" type="submit" name="search">Search</button>
        </form>


   



     <!-- Add User modal -->
    <!--  <div class="modal fade" id="AddsportsModal" tabindex="-1" role="dialog" aria-labelledby="AdduserModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Add Sports</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div> 
            <form action="/addsportForm" modelAttribute="sportsForm"  method="POST" enctype= "multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="subadmin" value="not"/>
				<input type="hidden" name="superadmin" value="not"/>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Name:</label>
                        <input type="text" class="form-control" placeholder="Sports Name" name="sportsName" id="sportsName" required>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Description</label>
						<textarea class="form-control" name="sportsDesc" placeholder="Sports Description" id="sportsDesc"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Image:</label>
                        <input type="file" class="form-control" name="sportsImg1" id="sportsImg1" required>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Price:</label>
						<input  class="form-control"  type="text" name="price"  placeholder="Sports Price" id="price" required>
                    </div>
                   
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Location:</label>
						<textarea class="form-control" name="location" placeholder="Sports Location" id="location"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-info" name="addsports" >Add Sports</button>
                </div>
            </form>
            </div>
        </div>
        </div> -->

</div>

<br />
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
                    <th>SPORTS_NAME</th>
                    <th>SPORTS_DESCRIPTION</th>
                    
                    <th>SPORTS_PRICE</th>
                    
                </tr>
            </thead>
                <tbody>
                	 <c:forEach var="allsports" items="${Sportslist}" >
			            <tr>
			            <td>${allsports.sportsName}</td>
			            <td>${fn:substring(allsports.sportsDesc, 0, 100)}...  </td>
			        
			            <td>${allsports.price}</td>
			            
                        <td class="d-flex">
                            <a class="btn btn-info edit" data-toggle="modal" name="edit_user" data-target="#EditsportsModal" data-whatever="@mdo">EDIT</a>
					         <input type="hidden" value="${allsports.id}" id="edit_id">
                            <a href="/admindeletesports/${allsports.id}" class="btn btn-danger ml-2" onclick='return deleteSports()'>DELETE</a>
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
			 <div class="modal fade" id="EditsportsModal" tabindex="-1" role="dialog" aria-labelledby="AdduserModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Edit User</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<form action="/EditsportsForm" modelAttribute="sportsEditForm" method="POST" enctype= "multipart/form-data">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="subadmin" value="not"/>
							<input type="hidden" name="superadmin" value="not"/>
						<input type="hidden" name="id" id="sports_id">
						<div class="modal-body">
		
							<div class="modal-body">

								<div class="form-group">
									<label for="message-text" class="col-form-label">Sports Name:</label>
									<input type="text" class="form-control" placeholder="Sports name" name="sportsName" id="sportsName1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">Sports Description:</label>
									<input type="text" class="form-control" placeholder="Sports Desc" name="sportsDesc" id="sportsDesc1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">Sports price:</label>
									<input type="text" class="form-control"  placeholder="sports price Id" name="price" id="price1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">Address:</label>
									<textarea class="form-control" name="location" placeholder="Sports location" id="location1"></textarea>
								</div>
								<div class="form-group">
                       			 <label for="message-text" class="col-form-label">Sports Image:</label>
                     		   <input type="file" class="form-control" name="sportsImg1" id="sportsImg11" >
                  			  </div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-info" name="EditSports" >Edit Sports</button>
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
                    url: "${pageContext.request.contextPath}/sportsfind/"+id, //this is my servlet
                    data: "input=" +$('#ip').val()+"&output="+$('#op').val(),
                    success: function(allsports){ 
                    		$('#EditsportsModal #sports_id').val(allsports.id);
                            $('#EditsportsModal #sportsName1').val(allsports.sportsName);
							$('#EditsportsModal #sportsDesc1').val(allsports.sportsDesc);
							$('#EditsportsModal #location1').val(allsports.location);
							$('#EditsportsModal #price1').val(allsports.price);
							$('#EditsportsModal #sportsImg11').val(allsports.sportsImg1);
							
                    }
                });
            });

        });
    </script>
<jsp:include page="includes/footer.jsp" />  
<%}%>
