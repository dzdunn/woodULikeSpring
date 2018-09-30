<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="wul" uri="http://woodulike.com/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<wul:header text="HEADER TAG" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div id="home-carousel" class="carousel slide"
				data-ride="carousel">
				<div class="carousel-inner">
					<div class="carousel-item active">
						<img class="d-block w-100" src="static/img/carousel/picard.jpg" alt="First slide">
					</div>
					<div class="carousel-item">
						<img class="d-block w-100" src="static/img/carousel/picard2.jpg" alt="Second slide">
					</div>
					<div class="carousel-item">
						<img class="d-block w-100" src="static/img/carousel/picard.jpg" alt="Third slide">
					</div>
				</div>
			</div>
		</div>
		
		<%
			out.println(request.getRemoteHost());
			out.println(request.getHttpServletMapping());
			out.println(session.getId());
		%>

		<h1>HOME PAGE</h1>


		<form action="inputData" method="POST">
			<label>Type your name: </label> <input type="text" name="name" /> <label>Type
				your age: </label> <input type="text" name="age" /> <input type="submit"
				value="submit" />
		</form>

		<h3>
			The date is:
			<%=(new java.util.Date()).toLocaleString()%></h3>

	</div>

	<%@include file="templates/footer.jsp"%>

	<script type="text/javascript">
		//alert("hello");

		$(document).ready(function() {

			alert("works");
		});
	</script>

</body>
</html>