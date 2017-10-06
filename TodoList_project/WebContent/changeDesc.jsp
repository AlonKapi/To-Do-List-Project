<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="javax.servlet.RequestDispatcher"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>Add Item</title>
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
	padding-top: 100px;
	padding-bottom: 480px;
}
.center_div{
    margin: 0 auto;
    width:25%; /* value of your choice which suits your alignment */
}
</style>

<body>
<%
	String exception = (String)request.getSession().getAttribute("Exception");
%>
<div class="container-fluid">
	<h1 class='text-light'>Please Enter A New Description</h1>
	<div class = "container center_div">
		<% if (exception != null){%>
		<div class="alert alert-danger" role="alert"><strong>Error!</strong> <%=exception%></div>
		<%}%>
		<form action="http://localhost:8080/TodoList_project/Controller/items/changeDesc" method="post">
		  <div class="form-group col-sm-12"> <!-- Description input -->
	        <input class="form-control" id="description" name="description" placeholder="Description" type="text"/>
	      </div>
	      <div class="form-group"> <!-- Submit button -->
	        <button class="btn btn-primary col-sm-8" name="submit" type="submit">Submit</button>
	      </div>
		</form>
		<a href="http://localhost:8080/TodoList_project/Controller/menu" class="btn btn-primary col-sm-8" role="button">Go Back</a>
	</div>
</div>

</body>
</html>