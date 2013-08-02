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
        		
        		
        		entityName = $("#name");
        		entityType = $("#type");
        		flow = $("#flow");
        		entityId = $("#entityId");
        		
        		$("#update").click(updateEntity);
        		$("#delete").click(deleteEntity);
        		
        	});
        	

        	function updateEntityDetails(id) {
        		$.ajax({
        			"url" : "rest/node/"+id,
        			"success" : function(entity) {
        				$("#name").val(entity.name);
        				$("#type").html(entity.type);
        				entityId.val(entity.id);
        				$("#flow").html(entity.flow.name);
   
        			}
        		});
        	}
        	
        	function updateEntity() {
        		var entity = {
           			"name"	: $("#name").val(),
           			"type": $("#type").val(),
        			"id"	: entityId.val()
        		};
        		$.ajax({
        			"url" : "rest/node",
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
        		var id = entityId.val();
        		$.ajax({
        			"url" : "rest/node/"+id,
        			"type" : "DELETE",
        			"success" : function(entities) {
        				window.location.href = "nodeList.html";
        			},
        			"error" : function(err) {
        				console.log(err);
        			}
        		});
        	}
        </script>
	</head>
	<body>
    	<a href="eventList.html">Events</a>
    	<a href="nodeList.html">Nodes</a>

		<h1>Flow Details</h1>

		<h2>Flow</h2>

		Name: <input id="name"/><br/>
		Type: <span id="type"></span><br/>
		Flow Name: <span id="flow"></span><br/>
		<input type="hidden" id="entityId"/>
		<a href="#" id="update">Update</a> <a href="#" id="delete">Delete</a>

		<hr/>


	</body>
 </html>