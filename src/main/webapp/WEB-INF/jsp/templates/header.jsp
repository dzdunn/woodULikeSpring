<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dunn.controller.path.ViewName" %>
<head>
    <link href="../static/bootstrap/css/bootstrap.min.css" type="text/css"
          rel="stylesheet"/>
    <link href="../static/font-awesome/css/all.css" type="text/css"
          rel="stylesheet"/>
    <link href="../static/css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<nav class="navbar navbar-expand-lg">
    <a class="navbar-brand" href="${ViewName.HOMEPAGE}">
        <spring:eval expression="@uiProperties.getProperty('brand')"/>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target=".dual-collapse2" aria-controls="dual-collapse2"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon">
            <i id="collapsedMenuIcon" class="fas fa-bars"></i>
        </span>
    </button>
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2" id="headerNavbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active"><a class="nav-link" href="${ViewName.ABOUT}">About
                <span class="sr-only">(current)</span>
            </a></li>
            <li class="nav-item"><a class="nav-link" href="${ViewName.CONTACT}">Contact</a></li>


            <sec:authorize access="isAuthenticated()">
                <li class="nav-item dropdown"><a
                        class="nav-link dropdown-toggle" href="#" id="dropdown04"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">My WoodProjects</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown04">
                        <a class="dropdown-item" href="${ViewName.CREATE_WOOD_PROJECT}">Create WoodProject</a>
                        <a class="dropdown-item" href="${ViewName.MANAGE_WOOD_PROJECTS}">Manage WoodProjects</a>
                        <a class="dropdown-item" href="${ViewName.MY_PROFILE}">My Profile</a>
                    </div>
                </li>
            </sec:authorize>
        </ul>


    </div>

    <div class="navbar-collapse collapse w-100 order-2 dual-collapse2">
        <ul class="navbar-nav mr-auto">
            <form:form class="form-inline">
                <li>
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </li>
            </form:form>
        </ul>
    </div>

    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <sec:authorize access="isAnonymous()">
                <li class="nav-item">
                    <a class="nav-link" href="${ViewName.LOGIN}">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${ViewName.REGISTER}">Register</a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="#"><c:out value="${pageContext.request.remoteUser}"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${ViewName.LOGOUT}">Logout</a>
                </li>
            </sec:authorize>
        </ul>
    </div>
</nav>
</body>

