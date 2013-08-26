<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/jquery-min.js"></script>
<script src="js/purl.js"></script>
        <script>
        var id ;
        $(function(){
    		var url = $.url();
    		id = url.param("devId");
    		console.log("dev ID----"+id);
    		entityList = $("#entityList");
        	entityListItemTemplate = $("#entityList li").clone();

        	updateEntityList(id);
        	$("#createEntityLink").click(function(){
        		createEntity(id);
        	});
        	
        });
        function updateEntityList(id,entities) {
        	if(entities == null || typeof entities == "undefined")
        		getAllEntitiesService(id,renderEntityList);
        	else
        		renderEntityList(entities);
        }
        function renderEntityList(entities) {
   			entityList.empty();
   			var entityListItem;
   			for(var i in entities) {
   				var entity = entities[i];
   				entityListItem = entityListItemTemplate.clone();
   				entityListItem.find(".entityName").html(entity.name);
   				entityListItem.data("entity", entity);
   				//entityListItem.
   				entityList.append(entityListItem);
   			}
   			$(".entityDetailsLink").on("click", navigateToEntityDetails);
        }
        function navigateToEntityDetails() {
        	var detailsLink = $(this);
        	var li = detailsLink.parents("li");
        	var entity = li.data("entity");
        	window.location.href = "userFlow.html?appId="+entity.id;
        }
        
        function getAllEntitiesService(id,callback) {
        	$.ajax({
        		"url" : "rest/application/getApplicationForDev/"+id,
        		"success" : function(entities) {
        			callback(entities);
        		}
        	});
        }
        function createEntity(id) {
        	var name = $("#entityName").val();
    		$.ajax({
    			"url" : "rest/application/addApp/"+name+"/"+id,
    			"type" : "POST",
    			"success" : function(entities) {
    				updateEntityList(id);
    			}
    		});
    		}
        </script>
<title>Application</title>
</head>
<body>
<h2>Create New Application</h2>
    	
		Application Name: <input type="text" name="name" id="entityName"/><br/>
		<a href="#" id="createEntityLink">Create</a>
 
        <hr>
        
		<h2>Existing Applications</h2>
		<c:set var="devId" value="${param.devId}" scope="session" />
        <ol id="entityList">
			<li>
				<span class="entityName"></span>
				<a href="#" class="entityDetailsLink">Select This application to edit</a>
			</li>
		</ol>
</body>
</html>