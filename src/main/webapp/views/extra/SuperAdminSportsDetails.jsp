<%if (session.getAttribute("Superadmin_email") == null) {response.sendRedirect("/signin"); } else {%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

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
                    	<h3 class="text-info">HOTEL DETAILS</h3>
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
   
 
 


      <form class="d-flex"  action="/superadmingansportsSearch"  method="post" autocomplete="off">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="user" value="superadmin" />
			<input class="form-control" type="search" name="valueToSearch" placeholder="Value To Search" aria-label="Search" value="${sports_keyword}">
            <button class="btn ml-2 btn-info" type="submit" name="search">Search</button>
        </form>

   <form class="d-flex">
        <button type="button" class="btn btn-info ml-2" name="add_sports" data-toggle="modal" data-target="#AddsportsModal" data-whatever="@mdo">Add Sports</button>
    </form>
     <!-- Add User modal -->
     <div class="modal fade" id="AddsportsModal" tabindex="-1" role="dialog" aria-labelledby="AdduserModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Add Sports</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/addsportsForm" modelAttribute="sportsForm" method="POST"  enctype= "multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="subadmin" value="not"/>
				<input type="hidden" name="superadmin" value="superadmin"/>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Name:</label>
                        <input type="text" class="form-control" placeholder="SportsName" name="sportsName" id="sportsName" required>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Description</label>
						<textarea class="form-control" name="sportsDesc" placeholder="Sports Description" id="SportsDescription"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Image:</label>
                        <input type="file" class="form-control" name="sportsImg1" id="SportsImg1" required>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Price:</label>
						<input  class="form-control"  type="text" name="price"  placeholder="Sports Price" id="SportsPrice" required>
                    </div>
                   
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Sports Location:</label>
						<textarea class="form-control" name="location" placeholder="Sports Location" id="Sportslocation"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-info" name="addsports" >Add Sports</button>
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
                    <th>HOTEL_NAME</th>
                    <th>HOTEL_DESCRIPTION</th>
                    <th>HOTEL_IMG1</th>
                    <th>HOTEL_PRICE</th>
                    <th>LOCATION</th>
                    <th>ACTION</th>
                </tr>
            </thead>
                <tbody>
                	 <c:forEach var="allsports" items="${sportslist}" >
			            <tr>
			            <td>${allsports.sportsName}</td>
			            <td>${fn:substring(allsports.sportsDesc, 0, 100)}...</td>
			             <td ><img src="data:image/jpeg;base64,${allsports.sportsImg1}" class="rounded-circle" width="100" height="100"/></td>
			            <td>${allsports.price}</td>
			                      <td>${allsports.location}</td>
                        <td class="d-flex">
							<a class="btn btn-info edit" data-toggle="modal" name="edit_sports" data-target="#EditsportsModal" data-whatever="@mdo">EDIT</a>
					        <input type="hidden" value="${allsports.id}" id="edit_id">                            
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
						<input type="hidden" name="superadmin" value="superadmin"/>					
						<input type="hidden" name="id" id="sports_id">
						<div class="modal-body">
		
							<div class="modal-body">

								<div class="form-group">
									<label for="message-text" class="col-form-label">sports Name:</label>
									<input type="text" class="form-control" placeholder="sportsName" name="sportsName" id="sportsName1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">sports Description:</label>
									<input type="text" class="form-control" placeholder="sportsDesc" name="sportsDesc" id="sportsDesc1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">sports price:</label>
									<input type="text" class="form-control"  placeholder="sports price Id" name="price" id="price1" required>
								</div>
								<div class="form-group">
									<label for="message-text" class="col-form-label">Address:</label>
									<textarea class="form-control" name="location" placeholder="sports location" id="location1"></textarea>
								</div>
								<div class="form-group">
                       			 <label for="message-text" class="col-form-label">sports Image:</label>
                     		   <input type="file" class="form-control" name="sportsImg1" id="sportsImg11" >
                  			  </div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-info" name="Editsports" >Edit sports</button>
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