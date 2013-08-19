<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Operation Types</title>
<script src="js/jquery-min.js"></script>
<script>

var entityList;
$(function(){
	entityList = $("#operationTypeTable");
	updateEntityList();
	$(".createOperationType").click(createEntity);
});


function createEntity() {
	var entity = {
			"oType" : $("#oType").val()
	};
	$.ajax({
		"url" : "rest/operationtype",
		"type" : "PUT",
		"data" : JSON.stringify(entity),
	    "contentType" : "application/json; charset=utf-8",
        "dataType" : "json",
		"success" : function(entities) {
			updateEntityList(entities);
		},
		"error" : function(){
			alert("Error Occured while creating");
		}
	});
}

function updateEntityList(entities) {
	if(entities == null || typeof entities == "undefined"){
		getAllEntitiesService(renderEntityList);	
	} 
	else{
		renderEntityList(entities);
	}
		
}

function renderEntityList(entities) {
	$("#oType").val("");
	entityList.empty();
	var entityRow;
	entityRow = $('<tr><th>Operation Type</th></tr>');
	entityList.append(entityRow); 
	for(var i in entities) {
		entityRow = $('<tr><td>'+entities[i]+'</td></tr>');
		entityList.append(entityRow); 
	} 
}

function getAllEntitiesService(callback) {
$.ajax({
		"url" : "rest/operationtype/getOperationType",
	    // whether this is a POST or GET request
	    type: "GET",
	    // the type of data we expect back
	    dataType : "json",
		"success" : function(entities) {
			callback(entities);
		},
		"error" : function(){
			alert("getAllOperationType Error");
		}
	});
         
}

</script>
</head>
<body>
<a href="mainmenu.html">Main Menu</a>
<h3>Enter Operation Type Details</h3>
	<%-- <form name="operationTypeform" method="POST" action="operationTypeWithValues.html"> --%>
		<table>
			<tr>
				<td>Operation Type:</td>
				<td><input type="text" name="oType" id="oType"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add" class="createOperationType"/></td>
			</tr>
		</table>
	<%-- </form> --%>
	<hr> 
	<b>Available Operation Types:</b>
	<table border="" cellspacing="4" cellpadding="4" id="operationTypeTable">
		<%-- <c:forEach var="oType" items="${operationTypes}">
			<tr>
				<td>${oType.getoType()}</td>
			</tr>
		</c:forEach> --%>
	</table>
</body>
</html>