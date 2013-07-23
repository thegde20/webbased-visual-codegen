<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Applications</title>
<script src="js/jquery-min.js"></script>
        <script>
	    	var entityList;
	        var entityListItemTemplate = null;
	        var developerSelect, developerOptionTemplate;
        	var developerList, developerListItemTemplate, applicationDetailsLink;
	        $(function(){
	        	entityList = $("#entityList");
	        	entityListItemTemplate = $("#entityList li").clone();

	        	updateEntityList();

        		developerSelect = $("#developerSelect");
        		developerOptionTemplate = $("#developerSelect option").clone();
        		
        		developerList = $("#developerList");
        		developerListItemTemplate = $("#developerList li").clone();
	        	
	        	$("#createEntityLink").click(createEntity);
	        	getAllDeveloper(renderDeveloperOptions);
	        });

	        function updateEntityList(entities) {
	        	if(entities == null || typeof entities == "undefined")
	        		getAllEntitiesService(renderEntityList);
	        	else
	        		renderEntityList(entities);
	        }
	        function getAllDeveloper(callback) {
	        	$.ajax({
	        		"url" : "rest/developer/getDevelopers",
	        		"success" : function(entities) {
	        			callback(entities);
	        		}
	        	});
	        }
	        function renderDeveloperOptions(entities) {
       			developerSelect.empty();
       			var entityOption;
       			for(var i in entities) {
       				var entity = entities[i];
       				entityOption = developerOptionTemplate.clone();
       				entityOption.html(entity.firstName);
       				entityOption.attr("value", entity.email);
       				developerSelect.append(entityOption);
       			}
	        }
	        
	        function renderEntityList(entities) {
       			entityList.empty();
       			var entityListItem;
       			for(var i in entities) {
       				var entity = entities[i];
       				entityListItem = entityListItemTemplate.clone();
       				entityListItem.find(".entityName").html(entity.name);
       				entityListItem.find(".appIdClass").attr("value",entity.id);
       				entityListItem.data("entity", entity);
       				//entityListItem.
       				entityList.append(entityListItem);
       			}
       			$(".entityDetailsLink").on("click", navigateToEntityDetails);
       			$(".deleteLink").on("click", deleteEntity);
       			$(".publishLink").on("click", publishApp);
       			//var deleteLink = $(this);
	        	//var li = deleteLink.parents("li");
	        	//var entity = li.data("entity");
       			
       			//$(".runLink").on("click", runLink);
	        }
	      
	        function deleteEntity() {
	        	var deleteLink = $(this);
	        	var li = deleteLink.parents("li");
	        	var entity = li.data("entity");
	        	var id = entity.id;
	        	$.ajax({
	        		"url" : "rest/application/"+entity.id,
	        		"type" : "DELETE",
	        		"success" : function(entities) {
	        			renderEntityList(entities);
        			},
        			"error" : function(err) {
        				console.log(err);
        			}
	        	});
	        }
	        function publishApp() {
	        	var deleteLink = $(this);
	        	var li = deleteLink.parents("li");
	        	var entity = li.data("entity");
	        	//var id = entity.id;
	        	$.ajax({
	        		"url" : "rest/publish/"+entity.id,
	        		"type" : "GET",
	        		"success" : function(entities) {
	        			//renderEntityList(entities);
	        			alert('published');
        			},
        			"error" : function(err) {
        				console.log(err);
        			}
	        	});
	        }
	        
	        function getAllEntitiesService(callback) {
	        	$.ajax({
	        		"url" : "rest/application/getApplications",
	        		"success" : function(entities) {
	        			callback(entities);
	        		}
	        	});
	        }
      
	        function navigateToEntityDetails() {
	        	var detailsLink = $(this);
	        	var li = detailsLink.parents("li");
	        	var entity = li.data("entity");
	        	window.location.href = "applicationDetails.html?entityId="+entity.id;
	        }
	        function runLink() {
	        	var runLink = $(this);
	        	var li = runLink.parents("li");
	        	var entity = li.data("entity");
	        	alert('entity.id'+entity.id);
	        	window.location.href = "runApp.html?appId="+entity.id;
	        }
	        
	        function createEntity() {
	        	var name = $("#entityName").val();
        		$.ajax({
        			"url" : "rest/application/addApp/"+name+"/"+developerSelect.val(),
        			"type" : "POST",
        			"success" : function(entities) {
        				updateEntityList(entities);
        			}
        		});
	        }
        </script>
    </head>
 
    <body>
    	<a href="applicationList.html">Applications</a>
    	<a href="developer.html">Developers</a>
    	
    	<h1>Applications</h1>
    	
    	<h2>Create New Application</h2>
    	
		Application Name: <input type="text" name="name" id="entityName"/><br/>
		<h2>Developers</h2>
		Developer:
		<select id="developerSelect">
			<option value="developerId">Developer NAME</option>
		</select>
		<a href="#" id="createEntityLink">Create</a>
 
        <hr>
        
		<h2>Existing Applications</h2>
		
        <ol id="entityList">
			<li>
				<span class="entityName"></span>
				<a href="#" class="entityDetailsLink">Details</a>
				<a href="#" class="deleteLink">Delete</a>
				<a href="#" class="publishLink">Publish</a>
				<form action="runApp.html" name="runApp">
				<input type="hidden" name="appId" class="appIdClass">
				<input type="submit" name="Run" value="RUN">
				<!-- <a href="runApp.html" class="runLink">Run</a> -->
				</form>
			</li>
		</ol>
		
	</body>
</html>