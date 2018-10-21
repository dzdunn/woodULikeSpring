<%@taglib prefix="wul" uri="http://woodulike.com/tags" %>
<html>
<head>
<title>Name and Age</title>
	<%@include file="templates/header.jsp"%>
</head>
<body>

<h1>Your name is: </h1>
<h2>${name}</h2>
<h1>Your age is:</h1>
<h2>${age}</h2>
<a href="${ViewName.HOMEPAGE}">Home</a>
<%@include file="templates/footer.jsp"%>
</body>
</html>