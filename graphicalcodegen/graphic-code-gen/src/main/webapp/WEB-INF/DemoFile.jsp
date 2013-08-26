<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
       			$(".developerSelect").on("click", navigateToEntityDetails);
	        }
	        function navigateToEntityDetails() {
	        	var detailsLink = $(this);
	        	var li = detailsLink.parents("li");
	        	var entity = li.data("entity");
	        	window.location.href = "userApplication.html?devId="+entity.email;
	        }

	        function getAllEntitiesService(callback) {
	        	$.ajax({
	        		"url" : "rest/developer/getDevelopers",
	        		"success" : function(entities) {
	        			callback(entities);
	        		}
	        	});
	        }
	        </script>
<title>Welcome to application</title>

</head>
<body>
<a href="mainmenu.html">Main Menu</a>
Select Developer:
<ol id="entityList">
			<li>
				<span class="entityName"></span>
				<input type="radio"  class="developerSelect"></input>
			</li>
		</ol>
</body>
</html>