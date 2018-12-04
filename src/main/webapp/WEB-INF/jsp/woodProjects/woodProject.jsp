<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woodulike</title>
    <%@include file="../templates/header.jsp"%>
</head>
<body>
<h1>WoodProjectTitle</h1>
<h1>${woodProject.title}</h1>
<h3>WoodProjectDescription</h3>
<h3>${woodProject.description}</h3>
<h3>WoodProjectImages</h3>
<%--<c:if test="${woodProject.images != null && !woodProject.images.isEmpty()}">--%>

    <%--<c:forEach var="imagePath" items="woodProject.images.path">--%>
        <%--<img src="${imagePath}">--%>
    <%--</c:forEach>--%>

<%--</c:if>--%>

<h3>WoodProjectAuthor</h3>
<h3>${woodProject.woodulikeUser.username}</h3>


<%@include file="../templates/footer.jsp"%>
</body>
</html>
