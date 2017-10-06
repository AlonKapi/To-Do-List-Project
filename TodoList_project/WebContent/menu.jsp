<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="javax.servlet.RequestDispatcher, il.ac.hit.todoListProject.model.Item, java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>Menu</title>
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
	padding-bottom: 400px;
}
.center_div{
    margin: 0 auto;
    width:25%; /* value of your choice which suits your alignment */
}
</style>

<body>
<%! DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); %>
<%
	Item selected = (Item) request.getSession().getAttribute("itemSelected");
%>
<div class="container-fluid">
	<h1 class='text-light'>Please Pick An Action To Perform On The Selected Item</h1>
	<div class = "container center_div">
		<div class="alert alert-info" role="alert"> <h5>Title: <%=selected.getTodo() %> <br> Start: <%=selected.getStartdate().format(formatter) %> <br> End: <%=selected.getEnddate().format(formatter) %></h5> </div>
		<a href="http://localhost:8080/TodoList_project/Controller/items" class="btn btn-primary col-sm-8" role="button">Go Back</a>
		<a href="http://localhost:8080/TodoList_project/Controller/items/changeDesc" class="btn btn-primary col-sm-8" role="button">Change Description</a>
	    <a href="http://localhost:8080/TodoList_project/Controller/items/changeTime" class="btn btn-primary col-sm-8" role="button">Change End Date</a>
	    <a href="http://localhost:8080/TodoList_project/Controller/items/delete" class="btn btn-danger col-sm-8" role="button">Delete</a>
	</div>
</div>

</body>
</html>