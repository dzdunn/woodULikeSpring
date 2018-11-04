<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
<h1>TOKEN INVALID</h1>
<div class="row">
    <div class="col-md-8 p-5">
<div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
    </div>
</div>
<%@include file="../templates/footer.jsp"%>
</body>
</html>
