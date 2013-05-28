<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statement Types</title>
</head>
<body>
<a href="mainmenu.html">Main Menu</a>
<h3>Enter Statement Type Details</h3>
	<form name="statementTypeform" method="POST" action="statementTypeWithValues.html">
		<table>
			<tr>
				<td>Statement Type:</td>
				<td><input type="text" name="sType" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add" /></td>
			</tr>
		</table>
	</form>
	<hr> 
	<b>Available Statement Types:</b>
	<table border="" cellspacing="4" cellpadding="4">
		<tr>
			<th>Statement Type</th>
		</tr>
		<c:forEach var="sType" items="${statementTypes}">
			<tr>
				<td>${sType.getsType()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>