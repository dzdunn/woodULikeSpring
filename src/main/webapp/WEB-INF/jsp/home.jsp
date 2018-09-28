<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
</head>
<body>

	<h1>HOME PAGE</h1>
	<form action="inputData" method="POST">
		<label>Type your name: </label> <input type="text" name="name" /> <label>Type
			your age: </label> <input type="text" name="age" />
			<input type="submit" value="submit"/>
	</form>

	<img alt="graduation" src="resources/static/graduation.jpg"/>

</body>
</html>