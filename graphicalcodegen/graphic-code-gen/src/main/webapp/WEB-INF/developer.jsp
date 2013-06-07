<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Developer List</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	
    	<h1>Developers</h1>

    	<h2>Create New Developer</h2>

        <form method="POST" action="addDeveloper.html" name="developerForm">
<table>
			<tr>
				<td>Email:</td>
				<td><input type="text" name="email" /></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add" /></td>
			</tr>
		</table>
      </form>
 
        <hr>
		<h2>Existing Developers</h2>
<hr>
<table border="" cellspacing="4" cellpadding="4">
		<tr>
			<th>Email</th>
			<th>Name</th>
		</tr>
		<c:forEach var="dev" items="${developers}">
			<tr>
				<td>${dev.email}</td>
				<td>${dev.name}</td>
			</tr>
			<form method="POST" action="editDeveloper.html">
			<tr>
				<td>
				<input type="hidden" name="detailId" value="${dev.email}" />
				<input type="submit" value="Edit" /></td>
			</tr>
			</form>
		</c:forEach>
	</table>
      </body>
 </html>