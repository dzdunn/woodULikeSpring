<%--
  Created by IntelliJ IDEA.
  User: dzdun
  Date: 01/10/2018
  Time: 00:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Create WoodProject</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>

<form:form method="POST" action="${viewName.CREATE_WOOD_PROJECT}" modelAttribute="woodProject">
    <table>
        <tr>
            <td><form:label path="title">Title</form:label></td>
            <td><form:input path="title"/></td>
        </tr>
        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:input path="description"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" class="btn btn-primary"/></td>
        </tr>

    </table>

</form:form>


<%@include file="../templates/footer.jsp"%>
</body>
</html>
