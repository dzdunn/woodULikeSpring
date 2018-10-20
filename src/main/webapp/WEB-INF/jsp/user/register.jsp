<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TemplateTitle</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>

<h1>Register</h1>
<form:form name="registerForm" action="${ViewName.REGISTER_PROCESS}" method="POST" modelAttribute="woodulikeUser">
    <form:label path="username">Username</form:label>
    <form:input path="username" type="text" id="username" name="username"/>
    <form:label path="password">Password</form:label>
    <form:password path="password" id="password" name="password"/>
    <div class="form-actions">
        <button type="submit" class="btn">Register</button>
    </div>
</form:form>




<%@include file="../templates/footer.jsp"%>
</body>
</html>
