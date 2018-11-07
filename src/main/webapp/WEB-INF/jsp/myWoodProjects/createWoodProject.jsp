<%--
  Created by IntelliJ IDEA.
  User: dzdun
  Date: 01/10/2018
  Time: 00:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Create WoodProject</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>

<c:out value="${message}"/>

<form:form method="POST" action="${ViewName.CREATE_WOOD_PROJECT}" modelAttribute="woodProjectDTO">
    <table>

        <tr>
            <td><form:label path="woodProject.title">Title</form:label></td>
            <td><form:input path="woodProject.title"/></td>
        </tr>
        <tr>
            <td><form:label path="woodProject.description">Description</form:label></td>
            <td><form:input path="woodProject.description"/></td>
        </tr>
        <tr>
            <td><label>Image:</label></td>
            <td><input type="file" name="imageFile"/></td>
                <%--<td><form:label path="imageFile">Files</form:label></td>--%>
                <%--<td><form:input path="imageFile" type="file"/></td>--%>
        </tr>


        <tr>
            <td><input type="submit" value="Submit" class="btn btn-primary"/></td>
        </tr>

    </table>

</form:form>

<form:form method="POST" action="/fileUploadProcess?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data"
           methodParam="imageFile">
    <table>

        <tr>
            <td><label>Image:</label></td>
            <td><input type="file" name="imageFile"/></td>
                <%--<td><form:label path="imageFile">Files</form:label></td>--%>
                <%--<td><form:input path="imageFile" type="file"/></td>--%>
        </tr>

        <tr>
            <td><input type="submit" value="Add image" class="btn btn-primary"/></td>
        </tr>

    </table>


</form:form>
<c:if test="${uploadedImagePaths != null && !uploadedImagePaths.isEmpty()}">
    <c:forEach var="image" items="${uploadedImagePaths}">
        <c:out value="${image}"/>
        <img src="${image}"/>

    </c:forEach>
</c:if>


    <%@include file="../templates/footer.jsp" %>
</body>
</html>
