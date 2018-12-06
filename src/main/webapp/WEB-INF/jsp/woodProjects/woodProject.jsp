<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>
<h1>WoodProjectTitle</h1>
<h1>${woodProjectDTO.woodProject.title}</h1>
<h3>WoodProjectDescription</h3>
<h3>${woodProjectDTO.woodProject.description}</h3>
<h3>WoodProjectImages</h3>
<c:if test="${woodProjectDTO.imagePaths != null && !woodProjectDTO.imagePaths.isEmpty()}">
    <c:forEach var="image" items="${woodProjectDTO.getRelativeImagePaths()}">
        <c:out value="${image}"/>
        <img src="${image}"/>

    </c:forEach>
</c:if>

<h3>WoodProjectAuthor</h3>
<h3>${PwoodProject.woodulikeUser.username}</h3>


<%@include file="../templates/footer.jsp" %>
</body>
</html>
