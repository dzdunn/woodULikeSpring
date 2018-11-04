<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="for" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TemplateTitle</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>

<h1>Register</h1>

<div class="row">
    <div class="col-md-8 p-5">
        <form:form id="registerForm" name="registerForm" action="${ViewName.REGISTER_PROCESS}" method="POST"
                   modelAttribute="woodulikeUser">
            <div class="form-group">
                <form:label path="emailAddress">Email</form:label>
                <form:input cssClass="form-control" path="emailAddress" type="text" id="emailAddress"
                            name="emailAddress"/>
                <form:errors path="emailAddress" cssClass="alert alert-danger" element="div"/>

            </div>
            <div class="form-group">
                <form:label path="username">Username</form:label>
                <form:input cssClass="form-control" path="username" type="text" id="username" name="username"/>
                <form:errors path="username" cssClass="alert alert-danger" element="div"/>
            </div>
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
            <div class="form-group">
                <form:label path="firstName">First Name</form:label>
                <form:input cssClass="form-control" path="firstName" type="text" id="firstName" name="firstName"/>
                <form:errors path="firstName" cssClass="alert alert-danger" element="div"/>
            </div>
            <div class="form-group">
                <form:label path="middleName">Middle Names</form:label>
                <form:input cssClass="form-control" path="middleName" type="text" id="middleName"
                            name="middleName"/>
            </div>
            <div class="form-group">
                <form:label path="lastName">Last Name</form:label>
                <form:input cssClass="form-control" path="lastName" type="text" id="lastName" name="lastName"/>
                <form:errors path="lastName" cssClass="alert alert-danger" element="div"/>


            </div>
            <div class="form-group">
                <form:label path="dateOfBirth">Date of Birth</form:label>
                <div class="input-group date" id="dobCalendar" data-target-input="nearest">
                    <input type="text" class="form-control datetimepicker-input"
                                data-target="#dobCalendar"
                                id="visibleDateOfBirth"/>
                    <form:input path="dateOfBirth" type="hidden" id="hiddenDateOfBirth"/>
                    <div class="input-group-append" data-target="#dobCalendar" data-toggle="datetimepicker">
                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                    </div>
                </div>
                <form:errors path="dateOfBirth" cssClass="alert alert-danger" element="div"/>
            </div>
            <div class="form-group">
                <form:label path="country">Country location</form:label>
                <form:select cssClass="form-control" path="country" id="country" name="country">
                    <form:option cssClass="form-control" value="${null}" label="----"/>
                    <form:options cssClass="form-control" items="${countries}"/>
                </form:select>
                <form:errors path="country" cssClass="alert alert-danger" element="div"/>
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
            viewMode: "decades"
        });


        $('#registerForm').submit(function () {
            var input = $(this).find('input[name=dateOfBirth]');
            var hiddenInput = $(this).find('input[name=hiddenDateOfBirth]');
            var date = input.val();
            hiddenInput.val(moment(date).format("YYYY-MM-DD"));

            input.val(moment(date).format("YYYY-MM-DD"));
        });
    })
</script>
</body>
</html>
