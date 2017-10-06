<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="javax.servlet.*, il.ac.hit.todoListProject.model.Item, java.time.format.DateTimeFormatter, java.time.temporal.ChronoUnit, java.time.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>Items</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" 
				integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<style scoped>
.container-fluid {
	background: url(https://i.imgur.com/mY7ZYma.jpg);
	background-size: cover;
	text-align: center;
	padding-top: 75px;
	padding-bottom: 600px;
}
.center_div{
    margin: 0 auto;
    width:25%; /* value of your choice which suits your alignment */
}
</style>

<body>
<%! DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); %>

<div class="container-fluid">
	<div class="row">
	    <div class="col-lg-12">
	    	<%
	    		java.util.List<Item> items = (java.util.List<Item>)request.getSession().getAttribute("items");
	    		if (!items.isEmpty()){
	    			LocalDateTime now = LocalDateTime.now();
	    			%> <h1 class='text-light'>To Do List: </h1>
	    			    <form action="http://localhost:8080/TodoList_project/Controller/menu" class="form" method="post">
	    				<table class="table table-responsive">
							<thead>
								<tr>
									<th style="width: 1%;"><p class='text-light'>&nbsp;&nbsp;&nbsp;Check</p></th>
									<th style="width: 10%;"><p class='text-light'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;Description</p></th>
									<th style="width: 10%;"><p class='text-light'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Start Date</p></th>
									<th style="width: 10%;"><p class='text-light'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;End Date</p></th>
									<th style="width: 10%;"><p class='text-light'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Time Remaining</p></th>
								</tr>
							</thead>
							<tbody>
								 	<%		// TABLE
						    			for (int i=0; i<items.size(); i++){
						    				String itemName = items.get(i).getTodo();
						    				String startDate = items.get(i).getStartdate().format(formatter);
						    				String endDate = items.get(i).getEnddate().format(formatter);
						    				long days = now.until(items.get(i).getEnddate(), ChronoUnit.DAYS);
						    				long hours = now.until(items.get(i).getEnddate(), ChronoUnit.HOURS);
						    				long mins = now.until(items.get(i).getEnddate(), ChronoUnit.MINUTES);
						    				
						    				if (days > 3){ // above 3 days, plenty of time to finish the todo
						    					%>
						    					<tr>
						    						<td class="table-primary"><label class="custom-control custom-radio">
						    						<%
						    							if (i==0){
						    								%> <input name="options" type="radio" class="custom-control-input" value="<%=i%>" checked> <%
						    							}
						    							else{
						    								%><input name="options" type="radio" class="custom-control-input" value="<%=i%>"> <%
						    							}
						    						%>
						    						<span class="custom-control-indicator"></span></label></td>
													<td class="table-primary"><p class="h5"><%=itemName%></p></td>
													<td class="table-primary"><p class="h5"><%=startDate%></p></td>
													<td class="table-primary"><p class="h5"><%=endDate%></p></td>
													<td class="table-primary"><p class="h5"><%=days%> days</p></td>
												</tr>
						    					<%
						    				}
						    				else if (days >= 1){ // between 1 day and 3 days, need to give a warning
						    					%>
						    					<tr>
						    						<td class="table-warning"><label class="custom-control custom-radio">
						    						<%
						    							if (i==0){
						    								%> <input name="options" type="radio" class="custom-control-input" value="<%=i%>" checked> <%
						    							}
						    							else{
						    								%><input name="options" type="radio" class="custom-control-input" value="<%=i%>"> <%
						    							}
						    						%>
						    						<span class="custom-control-indicator"></span></label></td>
													<td class="table-warning"><p class="h5"><%=itemName%></p></td>
													<td class="table-warning"><p class="h5"><%=startDate%></p></td>
													<td class="table-warning"><p class="h5"><%=endDate%></p></td>
													<td class="table-warning"><p class="h5"><%=days%> days</p></td>
												</tr>
						    					<%
						    				}
						    				else{	// less than a day left, danger!
						    					%>
						    					<tr>
						    						<td class="table-danger"><label class="custom-control custom-radio">
						    						<%
						    							if (i==0){
						    								%> <input name="options" type="radio" class="custom-control-input" value="<%=i%>" checked> <%
						    							}
						    							else{
						    								%><input name="options" type="radio" class="custom-control-input" value="<%=i%>"> <%
						    							}
						    						%>
						    						<span class="custom-control-indicator"></span></label></td>
													<td class="table-danger"><p class="h5"><%=itemName%></p></td>
													<td class="table-danger"><p class="h5"><%=startDate%></p></td>
													<td class="table-danger"><p class="h5"><%=endDate%></p></td>
													<% if (hours > 0){ %>
													<td class="table-danger"><p class="h5"><%=hours%> hours!</p></td>
													<%} else { %>
													<td class="table-danger"><p class="h5">Time's Up!</p></td>
													<%} %>
												</tr>
						    					<%
						    				}
						    			} %>
						  	
							</tbody>
						</table>
						<button type="submit" class="btn btn-primary col-sm-3" role="button">Actions Menu</button>
						</form>
	    				<%
	    				// BUTTONS
	    				%>
	    				<div class="container center_div">
		    				<div class="row">
		    					<a href="http://localhost:8080/TodoList_project/Controller/items/add" class="btn btn-primary col-sm-12" role="button">Add New Item</a>
		    				</div>
		    				<div class="row">
		    					<a href="http://localhost:8080/TodoList_project/Controller/logout" class="btn btn-dark col-sm-12" role="button">Logout</a>
		    				</div>
	    				</div>
	    			<%
	    			}
	    		else{
	    			%> <h1 class='text-light'>Your List Is Empty.. Please Add A To-Do! </h1>
	    			   <a href="http://localhost:8080/TodoList_project/Controller/items/add" class="btn btn-primary col-sm-2" role="button">Add Item</a>
	    			   <a href="http://localhost:8080/TodoList_project/Controller/logout" class="btn btn-dark col-sm-2" role="button">Logout</a>
	    			<%
	    		}
	    	%>
    	</div>
    </div>
</div>

</body>
</html>