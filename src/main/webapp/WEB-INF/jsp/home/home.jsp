<%@taglib prefix="wul" uri="http://woodulike.com/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <%@include file="../templates/header.jsp" %>
</head>
<body>

<div id="homeCarousel" class="carousel slide" data-ride="carousel" data-interval="1000">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img class="d-block w-100" src="../img/carousel/curvyDrawers.jpg" alt="First slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100" src="../img/carousel/supermanChoppingBoard.jpg" alt="Second slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100" src="../img/carousel/phoneHolder.jpg" alt="Third slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100" src="../img/carousel/beerTable.jpg" alt="Third slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100" src="../img/carousel/choppingBlock.jpg" alt="Third slide">
        </div>
    </div>
</div>

    <c:out value="${username}"/>

    <%
        out.println(request.getRemoteHost());
        out.println(request.getHttpServletMapping());
        out.println(session.getId());
    %>

    <h1>HOME PAGE</h1>

    <i class="fas fa-user"></i> <!-- uses solid style -->
    <i class="far fa-user"></i> <!-- uses regular style -->
    <i class="fal fa-user"></i> <!-- uses light style -->
    <!--brand icon-->
    <i class="fab fa-github-square"></i> <!-- uses brands style -->

    <form:form action="inputData" method="POST">
        <label>Type your name: </label>
        <input type="text" name="name"/>
        <label>Type
        your age: </label>
        <input type="text" name="age"/> <input type="submit" value="submit"/>
    </form:form>

    <h3>
        The date is:
        <%=(new java.util.Date()).toLocaleString()%>
    </h3>
    <i class="fas fa-address-book"></i>



<%@include file="../templates/footer.jsp" %>

<script type="text/javascript">

    $(document).ready(function () {

        alert("works");
    });
</script>

</body>
</html>