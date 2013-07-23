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
        <script>
	    	var entityList;
	        var entityListItemTemplate = null;
	        
	        $(function(){
	        	entityList = $("#entityList");
	        	entityListItemTemplate = $("#entityList li").clone();

	        	updateEntityList();
	        	
	        	$("#createEntityLink").click(createEntity);
	        });

	        function updateEntityList(entities) {
	        	if(entities == null || typeof entities == "undefined")
	        		getAllEntitiesService(renderEntityList);
	        	else
	        		renderEntityList(entities);
	        }
	        
	        function renderEntityList(entities) {
       			entityList.empty();
       			var entityListItem;
       			for(var i in entities) {
       				var entity = entities[i];
       				entityListItem = entityListItemTemplate.clone();
       				entityListItem.find(".entityName").html(entity.firstName + " " + entity.lastName + " " + entity.email);
       				entityListItem.data("entity", entity);
       				entityList.append(entityListItem);
       			}
       			$(".entityDetailsLink").on("click", navigateToEntityDetails);
       			$(".deleteLink").on("click", deleteEntity);
	        }
	        
	        function deleteEntity() {
	        	var deleteLink = $(this);
	        	var li = deleteLink.parents("li");
	        	var entity = li.data("entity");
	        	var id = entity.id;
	        	$.ajax({
	        		"url" : "rest/developer/"+entity.email,
	        		"type" : "DELETE",
	        		"success" : function(entities) {
	        			renderEntityList(entities);
        			},
        			"error" : function(err) {
        				console.log(err);
        			}
	        	});
	        }
	        
	        function getAllEntitiesService(callback) {
	        	$.ajax({
	        		"url" : "rest/developer/getDevelopers",
	        		"type" : "GET",
        		    "contentType" : "application/json; charset=utf-8",
        	        "dataType" : "json",
	        		"success" : function(entities) {
	        			callback(entities);
	        		}
	        	});
	        }
      
	        function navigateToEntityDetails() {
	        	var detailsLink = $(this);
	        	var li = detailsLink.parents("li");
	        	var entity = li.data("entity");
	        	//alert(''+entity.email);
	        	//alert('name--'+enitiy.firstName);
	        	window.location.href = "developerDetails.html?entityId="+entity.email;
	        }
	        
	        function createEntity() {
	        	var entity = {
	       			"firstName" : $("#firstName").val(),
	       			"lastName" : $("#lastName").val(),
	       			"email":$("#email").val()
	        	};
        		$.ajax({
        			"url" : "rest/developer",
        			"type" : "PUT",
        			"data" : JSON.stringify(entity),
        		    "contentType" : "application/json; charset=utf-8",
        	        "dataType" : "json",
        			"success" : function(entities) {
        				updateEntityList(entities);
        			}
        		});
	        }
        </script>
    </head>
 
    <body>
    	<a href="applicationList.html">Applications</a>
  
    	
    	<h1>Developers</h1>
    	
    	<h2>Create New Developer</h2>
    	
		First Name: <input type="text" name="firstName" id="firstName"/><br/>
		Last Name: <input type="text" name="lastName" id="lastName"/><br/>
		Email:<input type="text" name="email" id="email"/>
		<a href="#" id="createEntityLink">Create</a>
 
        <hr>
        
		<h2>Existing Developers</h2>
		
        <ol id="entityList">
			<li>
				<span class="entityName"></span>
				<a href="#" class="entityDetailsLink">Details</a>
				<a href="#" class="deleteLink">Delete</a>
			</li>
		</ol>
		
	</body></html>