<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="com.dunn.controller.uipaths.views.ViewName" %>
<html>
<head>
    <title>TemplateTitle</title>
    <%@include file="templates/header.jsp" %>
</head>
<body>

<form:form method="POST" action="/uploadFile" enctype="multipart/form-data">
    <table>
        <tr>

            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>

<%@include file="templates/footer.jsp" %>
</body>
</html>
