<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>

<div class="row">
    <div class="col-md-8 p-5">



        <form:form name="resetPasswordForm" action="${ViewName.RESET_PASSWORD_PROCESS}" method="POST" modelAttribute="emailAddressWrapper">
            <div class="form-group">
                <form:label path="emailAddress">Enter your email address:</form:label>
                <form:input path="emailAddress" cssClass="form-control" id="emailAddress" name="emailAddress" ></form:input>

            </div>
            <form:errors path="emailAddress" cssClass="alert alert-danger" element="div"></form:errors>
            <div class="form-actions">
                <button type="submit" class="btn">Reset Password</button>
            </div>
        </form:form>


    </div>
</div>





<%@include file="../templates/footer.jsp"%>
</body>
</html>
