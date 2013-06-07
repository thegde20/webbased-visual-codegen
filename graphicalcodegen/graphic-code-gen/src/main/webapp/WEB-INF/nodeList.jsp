<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Node List</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	
    	<h1>Nodes</h1>

    	<h2>Create New Node</h2>

        <form method="POST" action="addNode.html" name="nodeForm">
<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Flow:</td>
				<td><select name="flow">
				<c:forEach var="fl" items="${allFLows}">
				<option value="${fl.id}">${fl.name}</option>
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
			<th>Flow</th>
		</tr>
		<c:forEach var="nd" items="${allNodes}">
			<tr>
			<td>${nd.name}</td>
				<td>${nd.flow.name}</td>
			</tr>
			<form method="POST" action="editNode.html">
			<tr>
				<td>
				<input type="hidden" name="detailId" value="${nd.id}" />
				<input type="submit" value="Edit" /></td>
			</tr>
			</form>
		</c:forEach>
	</table>
      </body>
 </html>