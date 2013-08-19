<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Developer List</title>
        <script src="js/jquery-min.js"></script>
<script src="js/purl.js"></script>
        <script>
        	var entityName, firstName, lastName, entityId;
        	$(function(){
        		var url = $.url();
        		var id = url.param("entityId");
        		updateEntityDetails(id);
        		
        		
        		entityName = $("#entityName");
        		name = $("#name");
        		developer = $("#developer");
        		entityId = $("#entityId");
        		
        		$("#update").click(updateEntity);
        		$("#delete").click(deleteEntity);
        		
        	});
        	

        	function updateEntityDetails(id) {
        		$.ajax({
        			"url" : "rest/application/"+id,
        			"success" : function(entity) {
        				$("#name").val(entity.name);
        				entityId.val(entity.id);
        				$("#developer").html(entity.developer.firstName);
   
        			}
        		});
        	}
        	
        	function updateEntity() {
        		var entity = {
           			"name"	: $("#name").val(),
        			"id"	: entityId.val()
        		};
        		$.ajax({
        			"url" : "rest/application",
        			"data" : JSON.stringify(entity),
        		    "contentType" : "application/json; charset=utf-8",
        	        "dataType" : "json",
        			"type" : "POST",
        			"success" : function(entities) {
        				console.log(entities);
        			}
        		});
        	}
        	
        	function deleteEntity() {
        		var id = entityEmail.val();
        		$.ajax({
        			"url" : "rest/application/"+id,
        			"type" : "DELETE",
        			"success" : function(entities) {
        				window.location.href = "applicationList.html";
        			},
        			"error" : function(err) {
        				console.log(err);
        			}
        		});
        	}
        </script>
	</head>
	<body>
    	<a href="applicationList.html">Applications</a>
    	<a href="flowList.html">Flows</a>

		<h1>Application Details</h1>

		<h2>Application</h2>

		Name: <input id="name"/><br/>
		Developer: <span id="developer"></span><br/>
		<input type="hidden" id="entityId"/>
		<a href="#" id="update">Update</a> <a href="#" id="delete">Delete</a>

		<hr/>


	</body>
 </html>