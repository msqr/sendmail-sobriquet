<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><fmt:message key='app.name'/></title>
	<link href="images/favicon.png" type="image/png" rel="icon">
	<link href="images/favicon.png" type="image/png" rel="shortcut icon"> 
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/app.css" rel="stylesheet">
	<link href='//fonts.googleapis.com/css?family=Special+Elite' rel='stylesheet' type='text/css'>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>    
      <a class="navbar-brand logo" href="#">
        <img src="images/sendmail-sobriquet-logo.svg" title="<fmt:message key='app.name'/>">
      </a>
      <h1 class="navbar-text"><fmt:message key='app.name'/></h1>
      <form class="navbar-form navbar-left" role="search" method="get" action="<c:url value='/api/v1/find'/>">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search" name="alias">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
    </div>
  </div>
</nav>

	<script src="js-lib/jquery-2.1.4.min.js"></script>
	<script src="js-lib/bootstrap.min.js"></script>
	<script src="js/sobriquet.js"></script>
</body>
</html>
