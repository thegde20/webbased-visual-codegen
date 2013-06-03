<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scripts</title>
</head>
<body>
<a href="mainmenu.html">Main Menu</a>
<h3>Enter Script Details</h3>
	<form name="scriptform" method="POST" action="scriptWithValues.html">
		<table>
			<tr>
				<td>Script Name:</td>
				<td><input type="text" name="scriptName" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add Script" /></td>
			</tr>
		</table>
	</form>
	<hr> 
	<b>Available Scripts:</b>
	<table border="" cellspacing="4" cellpadding="4">
		<tr>
			<th>Delete</th>
			<th>Detail</th>
		</tr>
		<c:forEach var="script" items="${scripts}">
			<form method="POST" action="editscript.html">
			<tr>
				<td><input type="submit" name="deleteAction" value="${script.getScriptName()}" /></td>
				<td><input type="submit" name="detailAction" value="${script.getScriptName()}" /></td>
			</tr>
			</form>
		</c:forEach>
	</table>
</body>
</html>