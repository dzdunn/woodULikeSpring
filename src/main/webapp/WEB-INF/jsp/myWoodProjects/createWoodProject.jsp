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

    <%--<input id="woodProjectDTO.username" name="woodProjectDTO.username" value="TESTINGUSERNAME"/>--%>

</form:form>
<%--<form:form method="POST" modelAttribute="woodProjectDTO" enctype="multipart/form-data" id="woodProjectDTOForm2">--%>

    <%--<input type="text" name="woodProjectDTO.username" value="Danny"/>--%>

<%--</form:form>--%>

<c:if test="${woodProjectDTO.imagePaths != null && !woodProjectDTO.imagePaths.isEmpty()}">
    <c:forEach var="image" items="${woodProjectDTO.getRelativeImagePaths()}">
        <c:out value="${image}"/>
        <img src="${image}"/>

    </c:forEach>
</c:if>

<%--<img src="../createWoodProjectTemp/tester-34853fe9-f15d-4938-9afe-33b5052ec380/outputtedGraduation.jpg"/>--%>
<%--<img src="../createWoodProjectTemp/tester-5bf7b78c-74dc-42ff-a383-98de51a4d3b4/graduation.jpg"/>--%>


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
        //
        // $("#woodProject\\.title").on("focusout", function () {
        //         console.log("HEY")
        //         ajaxFormUpdate("#woodProject\\.title")
        //     }
        // );
        //
        // $("#woodProject\\.description").on("focusout", function () {
        //         ajaxFormUpdate("#woodProject\\.description")
        //     }
        // );


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
