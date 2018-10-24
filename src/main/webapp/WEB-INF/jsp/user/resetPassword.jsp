<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
<p>Enter your email address:</p>
<form:form name="resetPasswordForm" action="${ViewName.RESET_PASSWORD_PROCESS}" method="POST" modelAttribute="emailAddress">
    <div class="form-group">
        <form:label path="emailAddress"></form:label>
        <form:input path="emailAddress"></form:input>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn">Reset Password</button>
    </div>
</form:form>


<%@include file="../templates/footer.jsp"%>
</body>
</html>
