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
	        var applicationSelect, applicationOptionTemplate;
	        var flowSelect, flowOptionTemplate;
	        $(function(){
	        	entityList = $("#entityList");
	        	entityListItemTemplate = $("#entityList li").clone();

	        	updateEntityList();

	        	applicationSelect = $("#applicationSelect");
	        	applicationOptionTemplate = $("#applicationSelect option").clone();
	        	flowSelect = $("#flowSelect");
	        	flowOptionTemplate = $("#flowSelect option").clone();
        		
	        	
	        	$("#createEntityLink").click(createEntity);
	        	getAllApplications(renderApplicationOptions);
	        });

	        function updateEntityList(entities) {
	        	if(entities == null || typeof entities == "undefined")
	        		getAllEntitiesService(renderEntityList);
	        	else
	        		renderEntityList(entities);
	        }
	        function getAllApplications(callback) {
	        	$.ajax({
	        		"url" : "rest/application/getApplications",
	        		"success" : function(entities) {
	        			callback(entities);
	        		}
	        	});
	        }
	        function renderApplicationOptions(entities) {
	        	applicationSelect.empty();
       			var entityOption;
       			for(var i in entities) {
       				var entity = entities[i];
       				entityOption = applicationOptionTemplate.clone();
       				entityOption.html(entity.name);
       				entityOption.attr("value", entity.id);
       				applicationSelect.append(entityOption);
       			}
	        }
	        
	        function renderEntityList(entities) {
       			entityList.empty();
       			flowSelect.empty();
       			var entityListItem;
       			var entityOption;
       			entityOption = flowOptionTemplate.clone();
   				entityOption.html("No parennt");
   				entityOption.attr("value",-1);
   				flowSelect.append(entityOption);
       		
       			for(var i in entities) {
       				var entity = entities[i];
       				entityListItem = entityListItemTemplate.clone();
       				entityListItem.find(".entityName").html(entity.name);
       				entityListItem.data("entity", entity);
       				//entityListItem.
       				entityList.append(entityListItem);
       				//flow list for selecing parent flow if needed 
       				
       				
       				entityOption = flowOptionTemplate.clone();
       				entityOption.html(entity.name);
       				entityOption.attr("value", entity.id);
       				flowSelect.append(entityOption);
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
	        		"url" : "rest/flow/"+entity.id,
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
	        		"url" : "rest/flow/getFlows",
	        		"type" : "GET",
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
	        	window.location.href = "flowDetails.html?entityId="+entity.id;
	        }
	        
	        function createEntity() {
	        	var name = $("#entityName").val();
	        	var desc = $("#entityDesc").val();
	        	var parentId = flowSelect.val();
	        	if(parentId == null){
	        		parentId = -1;
	        	}
        		$.ajax({
        			"url" : "rest/flow/addFlow/"+name+"/"+desc+"/"+applicationSelect.val()+"/"+parentId,
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

    	
    	<h1>Flows</h1>
    	
    	<h2>Create New Flow</h2>
    	
		Flow Name: <input type="text" name="name" id="entityName"/><br/>
		Flow Description: <input type="text" name="desc" id="entityDesc"/><br/>
		<h2>Applications</h2>
		Application:
		<select id="applicationSelect">
			<option value="applicationId">Application NAME</option>
		</select>
		Parent Flow:
		<select id="flowSelect">
			<option value="-1">Flow NAME</option>
		</select>
		<a href="#" id="createEntityLink">Create</a>
 
        <hr>
        
		<h2>Existing Flows</h2>
		
        <ol id="entityList">
			<li>
				<span class="entityName"></span>
				<a href="#" class="entityDetailsLink">Details</a>
				<a href="#" class="deleteLink">Delete</a>
			</li>
		</ol>
		
	</body>
</html>