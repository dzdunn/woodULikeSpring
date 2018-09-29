<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="wul" uri="http://woodulike.com/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<wul:header text="HEADER TAG"/>

<%@include file="template.jsp" %>




</head>
<body>

<% out.println(request.getRemoteHost()); out.println(request.getHttpServletMapping()); out.println(session.getId()); %>

	<h1>HOME PAGE</h1>
	<form action="inputData" method="POST">
		<label>Type your name: </label> <input type="text" name="name" /> <label>Type
			your age: </label> <input type="text" name="age" /> <input type="submit"
			value="submit" />
	</form>
	
	<h3>The date is: <%= (new java.util.Date()).toLocaleString() %></h3>
	
<button type="button" class="btn btn-primary">Primary</button>

<img alt="grad" src="static/img/picard.jpg">

<script type="text/javascript">
//alert("hello");

$(document).ready(function(){
	
	alert("works");
});

</script>

</body>
</html>