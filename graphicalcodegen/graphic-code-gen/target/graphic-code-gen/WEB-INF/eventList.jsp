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
	var nodeSourceSelect, nodeSourceOptionTemplate;
	var nodeTargetSelect, nodeTargetOptionTemplate;
	$(function() {
		entityList = $("#entityList");
		entityListItemTemplate = $("#entityList li").clone();

		updateEntityList();

		nodeSourceSelect = $("#nodeSourceSelect");
		nodeSourceOptionTemplate = $("#nodeSourceSelect option").clone();
		nodeTargetSelect = $("#nodeTargetSelect");
		nodeTargetOptionTemplate = $("#nodeTargetSelect option").clone();

		$("#createEntityLink").click(createEntity);
		getAllNodes(renderNodesOptions);
	});

	function updateEntityList(entities) {
		if (entities == null || typeof entities == "undefined")
			getAllEntitiesService(renderEntityList);
		else
			renderEntityList(entities);
	}
	function getAllNodes(callback) {
		$.ajax({
			"url" : "rest/node/getNodes",
			"success" : function(entities) {
				callback(entities);
			}
		});
	}
	function renderNodesOptions(entities) {
		nodeSourceSelect.empty();
		nodeTargetSelect.empty();
		var entityOption;
		for ( var i in entities) {
			var entity = entities[i];
			entityOption = nodeSourceOptionTemplate.clone();
			entityOption2 = nodeTargetOptionTemplate.clone();
			entityOption.html(entity.name);
			entityOption2.html(entity.name);
			entityOption.attr("value", entity.id);
			entityOption2.attr("value", entity.id);
			nodeSourceSelect.append(entityOption);
			nodeTargetSelect.append(entityOption2);
		}
	}

	function renderEntityList(entities) {
		entityList.empty();
		var entityListItem;
		for ( var i in entities) {
			var entity = entities[i];
			entityListItem = entityListItemTemplate.clone();
			entityListItem.find(".entityName").html(entity.label+" "+entity.nodeSource.name+" "+entity.nodeTarget.name);
			entityListItem.data("entity", entity);
			//entityListItem.
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
			"url" : "rest/event/" + entity.id,
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
			"url" : "rest/event/getEvents",
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
		window.location.href = "eventDetails.html?entityId=" + entity.id;
	}

	function createEntity() {
		var label = $("#entityName").val();
		$.ajax({
			"url" : "rest/event/addEvent/" + label + "/" + nodeSourceSelect.val() + "/"+ nodeTargetSelect.val(),
			"type" : "POST",
			"success" : function(entities) {
				updateEntityList(entities);
			}
		});
	}
</script>
</head>

<body>
	<a href="nodeList.html">Flows</a>


	<h1>Events</h1>

	<h2>Create New Event</h2>

	Event Label:
	<input type="text" name="name" id="entityName" />
	<br /> Node Source:
	<select id="nodeSourceSelect">
		<option value="flowId">Node NAME</option>
	</select>
	<br />
	Node Target
	<select id="nodeTargetSelect">
		<option value="flowId">Node NAME</option>
	</select>
	<a href="#" id="createEntityLink">Create</a>

	<hr>

	<h2>Existing Nodes</h2>

	<ol id="entityList">
		<li><span class="entityName"></span>
		<a href="#" class="entityDetailsLink">Details</a>
		 <a href="#"
			class="deleteLink">Delete</a></li>
	</ol>

</body>
</html>