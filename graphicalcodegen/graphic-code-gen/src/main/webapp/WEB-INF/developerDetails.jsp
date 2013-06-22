<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Developer Details</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	<a href="applicationList.html">All Applications</a>
    	<h1>Developer Details</h1>
    	
    	<h2>Update Developer</h2>

        <form method="POST" action="editDeveloper.html" name="editDeveloperForm">
            Name:	<c:out value="${developer.email}" /><br/>
            Email:	<c:out value="${developer.name}" /><br/>
            <input type="hidden" name="deleteId" value="${developer.email}" />
            <input type="submit" value="Delete" />
        </form>
        <select name="develperApps">
<c:forEach var="devApp" items="${developer.applications}">
						<option value="${devApp.id}">${devApp.name}</option>
</c:forEach>
</select>
     </body>
 </html>