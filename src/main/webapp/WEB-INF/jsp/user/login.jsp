<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TemplateTitle</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>

<form:form name="loginForm" action="${ViewName.LOGIN_PROCESS}" method="POST" modelAttribute="woodulikeUser">
    <form:label path="username">Username</form:label>
    <form:input path="username" type="text" id="username" name="username"/>
    <form:label path="password">Password</form:label>
    <form:password path="password" id="password" name="password"/>
    <%--<%--%>
        <%--int cnt = 0;--%>
        <%--while (cnt < (int)request.getAttribute("errorCount")) {--%>
            <%--cnt++;%>--%>
            <%--<br/>--%>
    <%--<%--%>
        <%--}--%>

    <%--%>--%>


    <form:errors path="username"/>
    <br/>
    <form:errors path="password"/>
    <br/>
    <form:errors path=""/>
    <div class="form-actions">
        <button type="submit" class="btn">Log in</button>
    </div>

</form:form>

<a class="button" href="${ViewName.REGISTER}">Register</a>

<a href="${ViewName.RESET_PASSWORD}">Forgot Password</a>

<%@include file="../templates/footer.jsp"%>
</body>
</html>
