<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TemplateTitle</title>
    <%@include file="templates/header.jsp"%>
</head>
<body>

<h2>Submitted File</h2>
<table>
    <%--<img src="${imagePath}" alt="BLOB TEST"/>--%>
    <%--<tr>--%>
        <%--<td>OriginalFileName:</td>--%>
        <%--<td>${woodProject.title}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td>OriginalFileName:</td>--%>
        <%--<td>${file.originalFilename}</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td>Type:</td>--%>
        <%--<td>${file.contentType}</td>--%>
    <%--</tr>--%>
   <!-- <img src="${fileDirectory}" alt="uploadedImage"> -->

    <img src="/image" alt="test"/>
</table>

<%@include file="templates/footer.jsp"%>
</body>
</html>
