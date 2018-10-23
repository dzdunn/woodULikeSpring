<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TemplateTitle</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>

<h1>Register</h1>

<div class="row">
    <div class="col-6 p-5">
        <form:form id="registerForm" name="registerForm" action="${ViewName.REGISTER_PROCESS}" method="POST"
                   modelAttribute="woodulikeUser">
            <div class="form-group">
                <form:label path="emailAddress">Email</form:label>
                <form:input cssClass="form-control" path="emailAddress" type="text" id="emailAddress"
                            name="emailAddress"/>
            </div>
            <div class="form-group">
                <form:label path="username">Username</form:label>
                <form:input cssClass="form-control" path="username" type="text" id="username" name="username"/>
            </div>
            <div class="form-group">
                <form:label path="password">Password</form:label>
                <form:password cssClass="form-control" path="password" id="password" name="password"/>
            </div>
            <div class="form-group">
                <form:label path="firstName">First Name</form:label>
                <form:input cssClass="form-control" path="firstName" type="text" id="firstName" name="firstName"/>
            </div>
            <div class="form-group">
                <form:label path="middleName">Middle Names</form:label>
                <form:input cssClass="form-control" path="middleName" type="text" id="middleName"
                            name="middleName"/>
            </div>
            <div class="form-group">
                <form:label path="lastName">Last Name</form:label>
                <form:input cssClass="form-control" path="lastName" type="text" id="lastName" name="lastName"/>
            </div>
            <div class="form-group">
                <form:label path="dateOfBirth">Date of Birth</form:label>
                <div class="input-group date" id="dobCalendar" data-target-input="nearest">
                    <form:input path="dateOfBirth" type="text" cssClass="form-control datetimepicker-input" data-target="#dobCalendar"
                                id="dateOfBirth"
                                name="dateOfBirth"/>
                    <div class="input-group-append" data-target="#dobCalendar" data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <form:label path="country">Country location</form:label>
                <form:select cssClass="form-control" path="country" id="country" name="country">
                    <form:option cssClass="form-control" value="" label="----"/>
                    <form:options cssClass="form-control" items="${countries}"/>
                </form:select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn">Register</button>
            </div>


        </form:form>


    </div>
</div>



<%@include file="../templates/footer.jsp" %>
<script>
    $(document).ready(function () {
        $('#dobCalendar').datetimepicker({
            format: "DD-MMM-YYYY",
            extraFormats: ['DD-MM-YYYY'],
            maxDate: moment(),
            viewMode: "years"
        });


        $('#registerForm').submit(function(){
            alert("works");
            var input = $(this).find('input[name=dateOfBirth]');
            var date = input.val();
            input.val(moment(date).format("YYYY-MM-DD"));
        });
    })
</script>
</body>
</html>
