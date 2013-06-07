<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Applications</title>
</head>

<body>
    	<a href="mainmenu.html">Main Menu</a>
	<h1>Applications</h1>
	<h2>Create New Application</h2>
	<form method="POST" action="addApplication.html">
		Application Name: <input type="text" name="name" /><br /> Developer:
		<select name="developer">
			<c:forEach var="dev" items="${developers}">
				<option value="${dev.email}">${dev.name}</option>
			</c:forEach>
		</select> 
		<input type="submit" value="Create" />
	</form>

	<hr>
	<h2>Existing Applications</h2>
	<table border="" cellspacing="4" cellpadding="4">
		<tr>
			<th>Name</th>
			<th>Developer</th>
			<th>Flows</th>
		</tr>
		<c:forEach var="app" items="${apps}">
			<tr>
				<td>${app.name}</td>
				<td>${app.developer.name}</td>
				<td>
				<c:forEach var="flow" items="${app.flows}">
				${flow.name}
				</c:forEach>
				</td>
			</tr>
			<form method="POST" action="editApp.html">
			<tr>
				<td>
				<input type="hidden" name="detailId" value="${app.id}" />
				<input type="submit" value="Edit" /></td>
			</tr>
			</form>
		</c:forEach>
	</table>
	<hr>
</body>
</html>