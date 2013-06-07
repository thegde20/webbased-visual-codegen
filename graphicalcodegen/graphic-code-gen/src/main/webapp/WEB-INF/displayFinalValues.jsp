<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Final Variable Values</title>
</head>
<body>
	<table border="">
		<tr>
			<th>Variable</th>
			<th>Value</th>
			<c:forEach var="dataStatement" items="${sessionVariableObjects}">
		</tr>
		<tr>
			<td>${dataStatement.getDataName()}</td>
			<td>${dataStatement.getDataValue()}</td>
		</tr>
			</c:forEach>
	</table>
</body>
