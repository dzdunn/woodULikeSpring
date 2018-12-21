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
<form:form method="POST" modelAttribute="woodProjectDTO" enctype="multipart/form-data" id="woodProjectDTOForm">

    <table>

        <tr>
            <td><form:label path="woodProject.title">Title</form:label></td>
            <td><form:input path="woodProject.title" id="title"/></td>
        </tr>
        <tr>
            <td><form:label path="woodProject.description">Description</form:label></td>
            <td><form:input path="woodProject.description" id="description"/></td>
        </tr>
        <tr>
            <td><form:label path="imageFile">Image</form:label></td>
            <td><input type="file" name="imageFile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Add image" class="btn btn-primary"
                       formaction="/fileUploadProcess?${_csrf.parameterName}=${_csrf.token}"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" class="btn btn-primary"
                       formaction="/fileUploadProcess?${_csrf.parameterName}=${_csrf.token}&save=true"/></td>
        </tr>

    </table>

</form:form>


<c:if test="${woodProjectDTO.imagePaths != null && !woodProjectDTO.imagePaths.isEmpty()}">
    <c:forEach var="image" items="${woodProjectDTO.getRelativeImagePaths()}">
        <c:out value="${image}"/>
        <img src="${image}"/>
    </c:forEach>
</c:if>


<%@include file="../templates/footer.jsp" %>
<script type="text/javascript">

    $(document).ready(function () {


        $("#testInput").on("focusout", function(){
            console.log("it works!");
        });

        $("input").on("focusout", function(){
            console.log("working");
            ajaxFormUpdate2();
        });
    });

    function ajaxFormUpdate2() {
        let formData = $("#woodProjectDTOForm").serializeArray().reduce((obj, field) => {
            obj[field.name] = field.value;
            return obj;
        }, {});


        $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: "POST",
                url: "/jsonTest2?${_csrf.parameterName}=${_csrf.token}",
                data: JSON.stringify(formData),
                success: null,
                dataType: "json"
            }
        );

    }

    function ajaxFormUpdate(id) {

        var data = {};
        data[id] = $(id).val();

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
                type: "POST",
                url: "/jsonTest?${_csrf.parameterName}=${_csrf.token}",
                data: JSON.stringify(data),
                success: null,
                dataType: "json"
            }
        );

    }

</script>
</body>
</html>
