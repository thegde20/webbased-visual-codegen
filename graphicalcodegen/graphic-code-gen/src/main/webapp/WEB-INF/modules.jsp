<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Flow Modules</title>

<link rel="stylesheet" href="css/demo-all.css">
<link rel="stylesheet" href="css/flowchartDemo.css">
<link rel="stylesheet" href="css/jquery.fancybox.css">
<script src="js/jquery-min.js"></script>

<!-- DEP -->
<script src="js/jsPlumb/lib/jquery-1.9.0.js"></script>
<script src="js/jsPlumb/lib/jquery-ui-1.9.2-min.js"></script>
<script src="js/jsPlumb/lib/jquery.ui.touch-punch.min.js"></script>
<script src="js/jquery.fancybox.pack.js"></script>
<!-- /DEP -->

<!-- JS -->
<!-- support lib for bezier stuff -->
<script src="js/jsPlumb/lib/jsBezier-0.6.js"></script>
<!-- jsplumb util -->
<script src="js/jsPlumb/src/jsPlumb-util.js"></script>
<!-- base DOM adapter -->
<script src="js/jsPlumb/src/jsPlumb-dom-adapter.js"></script>
<!-- jsplumb drag-->
<script src="js/jsPlumb/src/jsPlumb-drag.js"></script>
<!-- main jsplumb engine -->
<script src="js/jsPlumb/src/jsPlumb.js"></script>
<!-- endpoint -->
<script src="js/jsPlumb/src/jsPlumb-endpoint.js"></script>
<!-- connection -->
<script src="js/jsPlumb/src/jsPlumb-connection.js"></script>
<!-- anchors -->
<script src="js/jsPlumb/src/jsPlumb-anchors.js"></script>
<!-- connectors, endpoint and overlays  -->
<script src="js/jsPlumb/src/jsPlumb-defaults.js"></script>
<!-- state machine connectors -->
<script src="js/jsPlumb/src/jsPlumb-connectors-statemachine.js"></script>
<!-- flowchart connectors -->
<script src="js/jsPlumb/src/jsPlumb-connectors-flowchart.js"></script>
<!-- SVG renderer -->
<script src="js/jsPlumb/src/jsPlumb-renderers-svg.js"></script>
<!-- canvas renderer -->
<script src="js/jsPlumb/src/jsPlumb-renderers-canvas.js"></script>
<!-- vml renderer -->
<script src="js/jsPlumb/src/jsPlumb-renderers-vml.js"></script>

<!-- jquery jsPlumb adapter -->
<script src="js/jsPlumb/src/jquery.jsPlumb.js"></script>
<!-- /JS -->

<!--  demo code -->
<!--script src="../js/jsPlumb/demo/js/flowchartConnectorsDemo.js"></!--script-->
<script src="js/flowchartConnectorsDemo.js"></script>

<!--  demo helper code -->
<script src="js/demo-list.js"></script>
<script src="js/jsPlumb/demo/js/demo-helper-jquery.js"></script>
<script src="js/purl.js"></script>
<script>
	//Setting up drop option
	var flowId;
	$(function() {
		var url = $.url();
		flowId = url.param("flowId");
		entityList = $("#main");
		entityListItemTemplate = $("#main").find('.window').clone();

		getNodesForFlow(flowId, populateChart);
		$(".fancybox").fancybox({
			'onStart' : function() {
				$("#addNodeLightbox").css("display", "block");
			},
			'onClosed' : function() {
				$("#addNodeLightbox").css("display", "none");
			}
		});
		
		$("#createEntityLink").click(function(){
    		createEntity(flowId);
    	});
		$("#updateEntityLink").click(updateEntity);
		$("#updateLabel").click(editLabel);
		//$("#createEntityLink").click(createEntity);
		$("#publishLink").click(function() {
			console.log('publish');
			var id = $("input#sessionAppId").val();
			publishApp(id);
		});	
		
	});
	function editProperties(element){
		console.log("in edit method"+this.parentNode);
		var div = $(this).parent();
		var entity = div.data("entity");
		$("#editNodeDiv").data("enitity",entity);
		$.fancybox({
			'href'   : '#editNodeDiv',
	        'titleShow'  : false,
	        'transitionIn'  : 'elastic',
	        'transitionOut' : 'elastic'
		});
	}
	function deleteNode() {
		var div = $(this).parent();
		//var li = deleteLink.parents("li");
		var entityId = div.data("entityId");
		console.log("delete");
		$.ajax({
			"url" : "rest/node/" + entityId,
			"type" : "DELETE",
			"success" : function(entities) {
				populateChart(entities);
			},
			"error" : function(err) {
				console.log(err);
			}
		});
	}
	function getNodesForFlow(id, callback) {
		$.ajax({
			"url" : "rest/node/getNodesForFlow/" + id,
			"success" : function(entities) {
				callback(entities);
			}
		});
	}
	function getEvents(callback) {
		$.ajax({
			"url" : "rest/event/getEvents",
			"type" : "GET",
			"dataType" : "json",
			"success" : function(entities) {
				callback(entities);
			}
		});
	}
	function populateChart(entities) {
		console.log("populate");
		entityList.empty();
		var top = 0;
		var entityListItem;
		for ( var i in entities) {
			top = top + 100;
			var entity = entities[i];
			entityListItem = entityListItemTemplate.clone();
			entityListItem.find("#imageDiv").data("entityId", entity.id);
			entityListItem.find("#imageClose").attr("src","css/close.jpeg");
			entityListItem.find('.value').text(entity.name);
			entityListItem.find("#editPropDiv").data("entity", entity);
			entityListItem.attr("id", "window" + entity.id);
			entityListItem.attr("style", "top:" + top + "px;" + "left:" + top
					+ "px;width:100px;height:50px");
			entityListItem.data("entity", entity);
			entityList.append(entityListItem);
		}
		for ( var i in entities) {
			var entity = entities[i];
			_addEndpoints("window" + entity.id, [ "BottomCenter" ],
					[ "TopCenter" ]);
			$("#window"+entity.id).removeClass("ui-draggable");
			jsPlumb.draggable($("#window"+entity.id));
		}
		$(".edit").on("click", editProperties);
		$(".imgClass").on("click",deleteNode);
		getEvents(populateConnections);
	}
	function populateConnections(entities) {
		jsPlumb.detachEveryConnection();
		for ( var i in entities) {
			var entity = entities[i];
			var sourceId = "window" + entity.nodeSource.id;
			var targetId = "window" + entity.nodeTarget.id;
			if (document.getElementById(sourceId)
					&& document.getElementById(targetId)) {
				_connect(sourceId, targetId, entity.id,entity.label);
			}

		}
	}
	function createEntity(flowId) {
		$.fancybox.close();
		var name = $("#entityName").val();
		var type = $("#entityType").val();
		$.ajax({
			"url" : "rest/node/addNode/" + name + "/" + type + "/" + flowId,
			"type" : "POST",
			"success" : function(entities) {
				getNodesForFlow(flowId, populateChart);
			}
		});
	}
	function publishApp(id) {
		$.ajax({
			"url" : "rest/publish/" + id,
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
	function updateEntity() {
		$.fancybox.close();
		var data = $("#editNodeDiv").data("enitity");
	//	console.log(data.id);
		vartype = data.type;
		var entity = {
   			"name"	: $("#editEntityName").val(),
   			"type": data.type,
			"id"	: data.id
		};
		$.ajax({
			"url" : "rest/node",
			"data" : JSON.stringify(entity),
		    "contentType" : "application/json; charset=utf-8",
	        "dataType" : "json",
			"type" : "POST",
			"success" : function(entity) {
				$("#window"+entity.id).find('.value').text(entity.name);
				
			}
		});
	}
	function labelClick(label,event){
		$("#editLabel").data("editLabel",label.component);
		$.fancybox({
			'href'   : '#editLabel',
	        'titleShow'  : false,
	        'transitionIn'  : 'elastic',
	        'transitionOut' : 'elastic'
		});
	}
	function editLabel() {
		$.fancybox.close();
		var data = $("#editLabel").data("editLabel");
		var eId = data.getParameter("eventId");
		var sId=data.getParameter("sId");
		var tId=data.getParameter("tId");
		console.log(eId+"----"+sId+"---"+tId);
		var label =$("#label").val();
		$.ajax({
			"url" : "rest/event/update/" + eId + "/" + sId
			+ "/" + tId+"/"+label,
			"type" : "POST",
			"success" : function(entities) {
				populateConnections(entities);
				
			}
		});
	}
</script>
</head>
<body data-demo-id="flowchartConnectorsDemo" data-library="jquery">
<input type="hidden" name="appId" id="sessionAppId" value="${sessionScope.appId}"></input>
	<div style="width: 800px; float: left;">
		<div id="publish" style="float: left">
			<a href="#" id="publishLink">Publish</a>
		</div>
	<div id="addNode" style="float: left;">
		<a href="#addNodeLightbox" class="fancybox">Add Node</a>
	</div>
	<div id="flows" style="float: left;">
		<a href="userFlow.html?appId=${sessionScope.appId}" id="flowLink">Flow</a>
	</div>
	</div>
	<div style="width: 1000px; float: left;">
		<div id="main" style="width: 1000px; float: left;">
			<div class="window" style="top: 100px; left: 100px;float:left;width:200px;height:50px;">
			<div id="imageDiv">
			<img id="imageClose" src="" style="position:absolute;top:0px;right:0px;background-color:gray;" class="imgClass"></div>
			<div class="value" style="width:100px;height:10px;float:left;"></div>
			<div style="width:100px;float:left;height:10px;">
			<a href="" id="editContents" style="width:100px;height:10px;float:left">Edit contents</a>
			</div>
			<div style="width:100px;float:left;height:10px;" id="editPropDiv">
			<a href="#" class="edit"  style="width:100px;height:10px;float:left;">Edit properties</a>
			</div>
			</div>
		</div>
	</div>
	<div id="editNodeDiv" style="display: none;width:400px;">
	Node Name: <input type="text" name="editEntityName" id="editEntityName" /> <br />
	<a href="#" id="updateEntityLink">Update</a>
	</div>
	<div id="addNodeLightbox" style="display: none;width:400px;">
		Node Name: <input type="text" name="name" id="entityName" /> <br />
		Node Type: <select id="entityType">
			<option value="IO">IO</option>
			<option value="Script">Script</option>
			<option value="DB">DB</option>
		</select> <br /> <a href="#" id="createEntityLink">Create</a>
	</div>
	<div id="editLabel" style="display:none;width:400px;">
	Label:<input type="text" name="label" id="label"/>
	<a href="#" id="updateLabel">Update Label</a>
	</div>




</body>
</html>
