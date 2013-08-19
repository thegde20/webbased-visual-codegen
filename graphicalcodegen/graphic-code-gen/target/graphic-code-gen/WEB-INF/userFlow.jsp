<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>
		Flow</title>
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
		var appId;
		$(function(){
			var url = $.url();
			appId = url.param("appId");
	        	entityList = $("#main");
	        	entityListItemTemplate = $("#main div").clone();

	        	getFlowsApplication(appId,populateChart);
	        	 $(".fancybox").fancybox({
	        	        'onStart': function() { $("#addFlowLightbox").css("display","block"); },            
	        	        'onClosed': function() { $("#addFlowLightbox").css("display","none"); }
	        	    });
	        	$("#createEntityLink").click(createEntity);
	        	$("#publishLink").click(function(){
	        		publishApp(appId);
	        		});
	        	
	        });
		function getFlowsApplication(id,callback){
			$.ajax({
        		"url" : "rest/flow/getFlowForApp/"+id,
        		"success" : function(entities) {
        			callback(entities);
        		}
        	});
		}
		function populateChart(entities){
			console.log("populate");
			entityList.empty();
			var top = 0;
   			var entityListItem;
   			
   			for(var i in entities) {
   				top = top +100;
   				var entity = entities[i];
   				entityListItem = entityListItemTemplate.clone().removeClass("ui-draggable");
   				entityListItem.html(entity.name);
   				//entityListItem.style.["top"] = top+"px";
   				//entityListItem.style.left = top+"px";
   				entityListItem.attr("id","window"+entity.id);
   				entityListItem.attr("style","top:"+top+"px;"+"left:"+top+"px;");
   				entityListItem.data("entity", entity);
   				entityList.append(entityListItem);
   				var name = entity.name;
   				jsPlumb.draggable($("window"+entity.id));
   				console.log(name);
   				//_addEndpoints(entityListItem, ["BottomCenter"], ["TopCenter", "RightMiddle"]);
   			}
   			for(var i in entities) {
   				var entity = entities[i];
   				//alert(entity.id);
   				_addEndpoints("window"+entity.id, ["BottomCenter"], ["TopCenter", "RightMiddle"]);
   			}
   			$.each( $('#main'), function(i, left) {
     		   $('div', left).click(function() {
					//console.log("third time"+);
				window.location.href="modules.html?flowId="+$(this).data("entity").id;
     		   });
     		});
		}
		 function createEntity() {
			 $.fancybox.close();
	        	var name = $("#entityName").val();
	        	var desc = $("#entityDesc").val();
	        	var parentId = -1;
     		$.ajax({
     			"url" : "rest/flow/addFlow/"+name+"/"+desc+"/"+appId+"/"+parentId,
     			"type" : "POST",
     			"success" : function(entities) {
     				populateChart(entities);
     			}
     		});
	        }
		 function publishApp(id) {
	        	$.ajax({
	        		"url" : "rest/publish/"+id,
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
        </script>
	</head>
		
	<body data-demo-id="flowchartConnectorsDemo" data-library="jquery">
	<div style="width:800px;float:left;">
		<div id="publish" style="float:left">
		<a href="#" id="publishLink">Publish</a></div>
		<div id="addNode" style="float:left;">
		<a href="#addFlowLightbox" class="fancybox">Add New Flow</a></div>
		<div id="addNode" style="float:left;">
		<a href="userApplication.html?devId=${sessionScope.devId}" class="fancybox">My Applications</a></div>
		</div>
		<div style="width:800px;float:left;">
		<c:set var="appId" value="${param.appId}" scope="session" />
        <div id="main" style="width:600px;float:left;">
			<!--div id="render"></div-->
			<div class="window"  style="top:100px;left:100px;"></div>
			</div>
	    <div id="addFlowLightbox"  style="display:none">
	    Flow Name: <input type="text" name="name" id="entityName"/><br/>
		Flow Description: <input type="text" name="desc" id="entityDesc"/><br/>
		<a href="#" id="createEntityLink">Create</a>
	    </div>
	    </div>
	    

        
		
	</body>
</html>
