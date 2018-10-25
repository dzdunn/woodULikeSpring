<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>

<div sec:authorize="hasAuthority('CHANGE_PASSWORD_PRIVILEGE')">
<form:form action="${ViewName.UPDATE_PASSWORD_PROCESS}" modelAttribute="newPassword">
    <form:label path="message">Set a new password:</form:label>
    <form:password path="message"></form:password>
</form:form>

</div>

<%@include file="../templates/footer.jsp"%>
</body>
</html>
