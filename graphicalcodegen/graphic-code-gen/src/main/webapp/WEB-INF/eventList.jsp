<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Event List</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	
    	<h1>Events</h1>

    	<h2>Create New Event</h2>

        <form method="POST" action="addEvent.html" name="flowForm">
<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="label" /></td>
			</tr>
			<tr>
				<td>Node Source:</td>
				<td><select name="nodeSource">
				<c:forEach var="nd" items="${allNodes}">
				<option value="${nd.id}">${nd.name}</option>
				</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Node Target:</td>
				<td><select name="nodeTarget">
				<c:forEach var="nd" items="${allNodes}">
				<option value="${nd.id}">${nd.name}</option>
				</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add" /></td>
			</tr>
		</table>
      </form>
 
        <hr>
		<h2>Existing Nodes</h2>
<hr>
<table border="" cellspacing="4" cellpadding="4">
		<tr>
			<th>Name</th>
			<th>Node Source</th>
			<th>Node Target</th>
		</tr>
		<c:forEach var="event" items="${allEvents}">
			<tr>
			<td>${event.label}</td>
				<td>${event.nodeSource.name}</td>
				<td>${event.nodeTarget.name}</td>
			</tr>
			<form method="POST" action="editFlow.html">
			<tr>
				<td>
				<input type="hidden" name="detailId" value="${event.id}" />
				<input type="submit" value="Edit" /></td>
			</tr>
			</form>
		</c:forEach>
	</table>
      </body>
 </html>