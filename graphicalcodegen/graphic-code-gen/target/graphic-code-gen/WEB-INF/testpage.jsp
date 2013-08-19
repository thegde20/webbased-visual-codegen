<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test page</title>
</head>
<body>
	<form name="testform" method="POST" action="insertVariableValues.html">
		<table border="">
			<tr>
				<th>Variable</th>
				<th>Value</th>
				<th>Type</th>
			</tr>
			<tr>
				<td><input type="text" name="var1" /></td>
				<td><input type="text" name="value1" value=0 /></td>
				<td><Select name="type1">
						<option value="int">Integer</option>
						<option value="double">Double</option>
						<option value="boolean">Boolean</option>
						<option value="String">String</option>
						<option value="date">Date</option>
				</Select></td>
			</tr>
			<tr>
				<td><input type="text" name="var2" /></td>
				<td><input type="text" name="value2" value=0 /></td>
				<td><Select name="type2">
						<option value="int">Integer</option>
						<option value="double">Double</option>
						<option value="boolean">Boolean</option>
						<option value="String">String</option>
						<option value="date">Date</option>
				</Select></td>
			</tr>
			<tr><td><input type="submit" name="Go" value="Send" /></td></tr>
		</table>
	</form>
	<marquee>Sample input form</marquee>
</body>
</html>