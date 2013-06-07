<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Flow Details</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	<a href="nodeList.html">All Nodes</a>
    	<h1>Flow Details</h1>
    	
    	<h2>Update flow</h2>

        <form method="POST" action="editFlow.html" name="editFlowForm">
            Name:	<c:out value="${flow.name}" /><br/>
            description:	<c:out value="${flow.description}" /><br/>
            Application:	<c:out value="${flow.application.name}" /><br/>
            <input type="hidden" name="deleteId" value="${flow.id}" />
            <input type="submit" value="Delete" />
        </form>
     </body>
 </html>