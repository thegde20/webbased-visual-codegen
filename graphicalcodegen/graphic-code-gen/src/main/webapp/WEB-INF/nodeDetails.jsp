<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Node Details</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	<a href="eventList.html">All Event</a>
    	<h1>Node Details</h1>
    	
    	<h2>Update Node</h2>

        <form method="POST" action="editNode.html" name="editNodeForm">
            Name:	<c:out value="${node.name}" /><br/>
            Flow:	<c:out value="${node.flow.name}" /><br/>
            <input type="hidden" name="deleteId" value="${node.id}" />
            <input type="submit" value="Delete" />
        </form>
     </body>
 </html>