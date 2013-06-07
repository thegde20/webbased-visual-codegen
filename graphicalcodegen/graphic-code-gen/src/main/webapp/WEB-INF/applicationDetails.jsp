<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Application Details</title>
    </head>
 
    <body>
    	<a href="mainmenu.html">Main Menu</a>
    	<a href="flowList.html">All Flows</a>
    	<h1>Application Details</h1>
    	
    	<h2>Update Application</h2>

        <form method="POST" action="editApp.html" name="appForm">
            Name:	<c:out value="${application.name}"/><br/>
            <input type="hidden" name="deleteId" value="${application.id}" />
            <input type="submit" value="Delete" />
            <select name="appFlowList">
            <c:forEach var="appFlow" items="${application.flows}">
            <option value="${appFlow.id}">${appFlow.name}</option> 
            </c:forEach>
            </select>
        </form>        <hr>
        
        <h2>Applications</h2>

		<form action="addFlow.html" method="POST">
			<select name="flowId">
			<c:forEach items="${flows}" var="flow">
			<option value="${flow.id}">${flow.name}</option>
			</c:forEach>
			</select>
			<input type="submit" value="Add Flow"/>
		</form>
        <ol>
</ol><hr>
      </body>
 </html>