<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TemplateTitle</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <form:form name="loginForm" action="${ViewName.LOGIN_PROCESS}" method="POST" modelAttribute="woodulikeUser">
            <div class="form-group">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Username</span>
                    </div>
                    <form:input path="username" cssClass="form-control" type="text" id="username" name="username"/>


                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Password</span>
                    </div>
                    <form:password path="password" cssClass="form-control" id="password" name="password"/>
                </div>

            </div>
            <form:errors path="username" cssClass="alert alert-danger" element="div"/>

            <form:errors path="password" cssClass="alert alert-danger" element="div"/>

            <form:errors path="" cssClass="alert alert-danger" element="div"/>


            <div class="form-actions">
                <button type="submit" class="btn">Log in</button>
            </div>

        </form:form>
    </div>

</div>
<div class="row">
    <div class="col-md-6">
        <a class="button" href="${ViewName.REGISTER}">Register</a>
        <a href="${ViewName.RESET_PASSWORD}">Forgot Password</a>
    </div>
</div>
</div>
<%@include file="../templates/footer.jsp" %>
</body>
</html>
