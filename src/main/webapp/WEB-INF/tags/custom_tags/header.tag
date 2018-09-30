<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="text" required="true"%>
<head>
<link href="static/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="static/css/style.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<nav class="navbar navbar-expand-md">
		<a class="navbar-brand" href="${viewName.HOME}"> 
		<spring:eval expression="@uiProperties.getProperty('brand')" />
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarsExample04" aria-controls="navbarsExample04"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarsExample04">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">About
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Contact</a></li>

				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="dropdown04"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
					<div class="dropdown-menu" aria-labelledby="dropdown04">
						<a class="dropdown-item" href="#">Action</a> <a
							class="dropdown-item" href="#">Another action</a> <a
							class="dropdown-item" href="#">Something else here</a>
					</div></li>
			</ul>
			<form class="form-inline my-2 my-md-0">
				<input class="form-control" type="text" placeholder="Search">
			</form>
		</div>
	</nav>
</body>

