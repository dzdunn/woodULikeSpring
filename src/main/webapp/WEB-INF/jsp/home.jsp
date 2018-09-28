<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>

<link href="static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
 <script src="static/jquery/dist/jquery.min.js"></script>




</head>
<body>

	<h1>HOME PAGE</h1>
	<form action="inputData" method="POST">
		<label>Type your name: </label> <input type="text" name="name" /> <label>Type
			your age: </label> <input type="text" name="age" /> <input type="submit"
			value="submit" />
	</form>
	
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