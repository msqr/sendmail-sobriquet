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
	<link href="css/sobriquet.css" rel="stylesheet">
	<link href='//fonts.googleapis.com/css?family=Special+Elite' rel='stylesheet' type='text/css'>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand logo" href="#">
				<img src="images/sendmail-sobriquet-logo.svg" alt="<fmt:message key='app.name'/>">
			</a>
			<h1 class="navbar-text"><fmt:message key='app.name'/></h1>
			<form class="navbar-form navbar-left" role="search" id="search-form" method="get" action="<c:url value='/api/v1/find'/>">
				<div class="form-group">
					<input type="search" class="form-control" placeholder="<fmt:message key='search.query.placeholder'/>" name="alias">
				</div>
				<button type="submit" class="btn btn-default"><fmt:message key='action.search'/></button>
			</form>
		</div>

		<ul class="nav navbar-nav">
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="#" class="action-import"><fmt:message key='action.import'/></a></li>
				</ul>
			</li>
		</ul>

		<button class="btn btn-primary navbar-btn navbar-right btn-add-alias" aria-label="<fmt:message key='action.add'/>"
				data-toggle="modal" data-target="#add-alias-form">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		</button>
	</div>
</nav>
	<div class="container welcome">
		<div class="jumbotron">
			<h1><fmt:message key='welcome.heading'/></h1>
			<p><fmt:message key='welcome.intro'/></p>
		</div>
	</div>
	
	<div class="container hidden" id="search-results">
		<div class="row">
			<div class="col-xs-12 col-md-6">
				<p class="empty-results"><fmt:message key='search.results.empty'/></p>
				<table class="table table-striped nonempty-results">
					<thead>
						<tr>
							<th>#</th>
							<th><fmt:message key='alias.alias'/></th>
							<th><fmt:message key='alias.actual'/></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr class="alias template">
							<td class="index"></td>
							<td class="alias"></td>
							<td class="actuals"></td>
							<td>
								<button type="button" class="btn btn-default delete-alias" title="<fmt:message key='action.delete'/>">
									<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>
									<span class="confirm"><fmt:message key='delete.confirm'/></span>
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- Add new modal -->
	<form class="modal fade" action="<c:url value='/api/v1/alias/key'/>" id="add-alias-form">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="<fmt:message key='action.add'/>"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title"><fmt:message key='add.heading'/></h4>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group">
						<label for="add-alias-alias" class="col-sm-2 control-label"><fmt:message key='alias.alias'/></label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="alias" required autocapitalize="off" autocorrect="off" 
								id="add-alias-alias" placeholder="<fmt:message key='alias.alias.placeholder'/>">
						</div>
					</div>
					<div class="form-group">
						<label for="add-alias-actual" class="col-sm-2 control-label"><fmt:message key='alias.actual'/></label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="actual" required autocapitalize="off" autocorrect="off"
								 id="add-alias-actual" placeholder="<fmt:message key='alias.actual.placeholder'/>">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key='action.close'/></button>
					<button type="submit" class="btn btn-primary"><fmt:message key='action.save'/></button>
				</div>
			</div>
		</div>
	</form>

	<!-- Import modal -->
	<form class="modal fade" action="<c:url value='/import'/>" id="import-aliases-form" enctype="multipart/form-data">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="<fmt:message key='action.add'/>"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title"><fmt:message key='import.heading'/></h4>
				</div>
				<div class="modal-body form-horizontal carousel slide" id="import-carousel">
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<p><fmt:message key='import.intro'/></p>
							<div class="form-group">
								<label for="import-aliases-data" class="col-sm-2 control-label"><fmt:message key='import.aliases.aliasData'/></label>
								<div class="col-sm-10">
									<textarea class="form-control" name="aliasData" id="import-aliases-data" placeholder="<fmt:message key='import.aliases.aliasData.placeholder'/>"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="import-aliases-file" class="col-sm-2 control-label"><fmt:message key='import.aliases.file'/></label>
								<div class="col-sm-10">
									<input type="file" class="form-control" name="file" id="import-aliases-file" placeholder="<fmt:message key='import.aliases.file.placeholder'/>">
								</div>
							</div>
						</div>
						<div class="item scrolling" id="import-results">
							<p><fmt:message key='import.aliases.review.intro'/></p>
							<p class="empty-results"><fmt:message key='import.aliases.review.empty'/></p>
							<table class="table table-striped nonempty-results">
								<thead>
									<tr>
										<th>#</th>
										<th><fmt:message key='alias.alias'/></th>
										<th><fmt:message key='alias.actual'/></th>
									</tr>
								</thead>
								<tbody>
									<tr class="alias template">
										<td class="index"></td>
										<td class="alias"></td>
										<td class="actuals"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<input type="hidden" name="_to" value="verify">
					<button type="button" class="btn btn-default pull-left back"><fmt:message key='action.back'/></button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key='action.close'/></button>
					<button type="submit" class="btn btn-primary"><fmt:message key='action.import'/></button>
				</div>
			</div>
		</div>
	</form>

	<script src="js-lib/jquery-2.1.4.min.js"></script>
	<script src="js-lib/bootstrap.min.js"></script>
	<script src="js/sobriquet.js"></script>
</body>
</html>
