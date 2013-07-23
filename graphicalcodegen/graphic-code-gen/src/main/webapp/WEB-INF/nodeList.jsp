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
	var flowSelect, flowOptionTemplate;
	$(function() {
		entityList = $("#entityList");
		entityListItemTemplate = $("#entityList li").clone();

		updateEntityList();

		flowSelect = $("#flowSelect");
		flowOptionTemplate = $("#flowSelect option").clone();

		$("#createEntityLink").click(createEntity);
		getAllFlows(renderFlowOptions);
	});

	function updateEntityList(entities) {
		if (entities == null || typeof entities == "undefined")
			getAllEntitiesService(renderEntityList);
		else
			renderEntityList(entities);
	}
	function getAllFlows(callback) {
		$.ajax({
			"url" : "rest/flow/getFlows",
			"success" : function(entities) {
				callback(entities);
			}
		});
	}
	function renderFlowOptions(entities) {
		flowSelect.empty();
		var entityOption;
		for ( var i in entities) {
			var entity = entities[i];
			entityOption = flowOptionTemplate.clone();
			entityOption.html(entity.name);
			entityOption.attr("value", entity.id);
			flowSelect.append(entityOption);
		}
	}

	function renderEntityList(entities) {
		entityList.empty();
		var entityListItem;
		for ( var i in entities) {
			var entity = entities[i];
			entityListItem = entityListItemTemplate.clone();
			entityListItem.find(".entityName").html(entity.name+" "+entity.type+" "+entity.flow.name);
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
			"url" : "rest/node/" + entity.id,
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
			"url" : "rest/node/getNodes",
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
		window.location.href = "nodeDetails.html?entityId=" + entity.id;
	}

	function createEntity() {
		var name = $("#entityName").val();
		var type = $("#entityType").val();
		$.ajax({
			"url" : "rest/node/addNode/" + name + "/" + type + "/"+ flowSelect.val(),
			"type" : "POST",
			"success" : function(entities) {
				updateEntityList(entities);
			}
		});
	}
</script>
</head>

<body>
	<a href="flowList.html">Flows</a>


	<h1>Nodes</h1>

	<h2>Create New Node</h2>

	Node Name:
	<input type="text" name="name" id="entityName" />
	<br /> Node Type:
	<select id="entityType">
		<option value="IO">IO</option>
		<option value="Form">Form</option>
		<option value="DB">DB</option>
	</select>
	<br />
	<h2>Flows</h2>
	Flow:
	<select id="flowSelect">
		<option value="flowId">Flow NAME</option>
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