<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>FLow List</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	
    	<h1>Flows</h1>

    	<h2>Create New FLow</h2>

        <form method="POST" action="addFlow.html" name="flowForm">
<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><input type="text" name="desc" /></td>
			</tr>
			<tr>
				<td>Application:</td>
				<td><select name="application">
				<c:forEach var="app" items="${allApps}">
				<option value="${app.id}">${app.name}</option>
				</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add" /></td>
			</tr>
		</table>
      </form>
 
        <hr>
		<h2>Existing FLows</h2>
<hr>
<table border="" cellspacing="4" cellpadding="4">
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Application</th>
		</tr>
		<c:forEach var="flow" items="${allFlows}">
			<tr>
			<td>${flow.name}</td>
				<td>${flow.description}</td>
				<td>${flow.application.name}</td>
			</tr>
			<form method="POST" action="editFlow.html">
			<tr>
				<td>
				<input type="hidden" name="detailId" value="${flow.id}" />
				<input type="submit" value="Edit" /></td>
			</tr>
			</form>
		</c:forEach>
	</table>
      </body>
 </html>