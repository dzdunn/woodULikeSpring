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
<form:form method="POST"  modelAttribute="woodProjectDTO" enctype="multipart/form-data">

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
        <td><form:label path="imageHolder">Image</form:label> </td>
        <td><input type="file" name="imageHolder"></td>
    </tr>
    <tr>
        <td><input type="submit" value="Add image" class="btn btn-primary" formaction="/fileUploadProcess?${_csrf.parameterName}=${_csrf.token}"/></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" class="btn btn-primary" formaction="/fileUploadProcess?${_csrf.parameterName}=${_csrf.token}&save=true"/></td>
    </tr>
    <%--<input type="hidden" name="imageDirectories" value="${woodProjectDTO.imageDirectories}"/>--%>
    <%--<input type="hidden" name="tempDirectory" value="${woodProjectDTO.tempDirectory}"/>--%>

</table>

</form:form>



<c:if test="${woodProjectDTO.imageDirectories != null && !woodProjectDTO.imageDirectories.isEmpty()}">
    <c:forEach var="image" items="${woodProjectDTO.imageDirectories}">
        <c:out value="${image}"/>
        <img src="${image}"/>

    </c:forEach>
</c:if>

<img src="../createWoodProjectTemp/tester-34853fe9-f15d-4938-9afe-33b5052ec380/outputtedGraduation.jpg"/>
<img src="../createWoodProjectTemp/tester-5bf7b78c-74dc-42ff-a383-98de51a4d3b4/graduation.jpg"/>


    <%@include file="../templates/footer.jsp" %>
</body>
</html>
