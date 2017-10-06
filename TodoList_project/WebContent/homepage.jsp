<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="javax.servlet.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>Homepage</title>
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
	padding-top: 150px;
	padding-bottom: 540px;
}
</style>

<body>
<%@ taglib uri="/WEB-INF/tlds/mytld.tld" prefix="mytag"%>
<%
	String name = "New User";
	String sesnName = (String)request.getSession().getAttribute("name");
	
	if (sesnName != null)
		name = sesnName;
%>
<div class="container-fluid">
	<h1 class='text-light'>Welcome <% out.print(name); %>!</h1>
	<div class="row">
	    <div class="col-lg-12">
	      <a href="http://localhost:8080/TodoList_project/Controller/users" class="btn btn-primary col-md-3 offset-md-3" role="button">Enter</a>
    	</div>
    </div>
    <div class="fixed-bottom">
    	<mytag:myNameTag/>
    </div>
</div>

</body>
</html>