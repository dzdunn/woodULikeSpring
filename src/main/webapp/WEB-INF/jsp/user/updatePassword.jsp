<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>

<div sec:authorize="hasAuthority('CHANGE_PASSWORD_PRIVILEGE')">
    <h1>Reset your password</h1>
    <spring:form


</div>

<%@include file="../templates/footer.jsp"%>
</body>
</html>
