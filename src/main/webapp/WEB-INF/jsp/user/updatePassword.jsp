<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>

<div sec:authorize="hasAuthority('CHANGE_PASSWORD_PRIVILEGE')">

    <form:form action="${ViewName.UPDATE_PASSWORD_PROCESS}" modelAttribute="tmpWoodulikeUser">
        <div class="form-group">
            <form:label path="password">Password</form:label>
            <form:password cssClass="form-control" path="password" id="password" name="password"/>
            <form:errors path="password" cssClass="alert alert-danger" element="div"/>
        </div>
        <div class="form-group">
            <form:label path="confirmPassword">Confirm Password</form:label>
            <form:password cssClass="form-control" path="confirmPassword" id="confirmPassword"
                           name="confirmPassword"/>
            <form:errors path="confirmPassword" cssClass="alert alert-danger" element="div"/>
            <form:errors path="" cssClass="alert alert-danger" element="div"/>

        </div>
        <div class="form-actions">
            <button type="submit" class="btn">Update Password</button>
        </div>
    </form:form>
</div>

<%@include file="../templates/footer.jsp" %>
</body>
</html>
