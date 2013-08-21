<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scripts</title>
<script src="js/jquery-min.js"></script>
<script>
var entityList;
$(function(){
	entityList = $("#scriptTable");
	updateEntityList();
	$(".createScript").click(createEntity);
});


function createEntity() {
	var entity = {
			"scriptName" : $("#scriptName").val()
	};
	$.ajax({
		"url" : "rest/script",
		"type" : "PUT",
		"data" : JSON.stringify(entity),
	    "contentType" : "application/json; charset=utf-8",
        "dataType" : "json",
		"success" : function(entities) {
			updateEntityList(entities);
		},
		"error" : function(){
			alert("Error Occured while creating Script");
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

function renderEntityList(scriptEntities) {
	$("#scriptName").val("");
	entityList.empty();
	var entityRow;
	entityRow = $('<tr><th>Delete</th><th>Detail</th></tr>');
	entityList.append(entityRow); 
	for(var i in scriptEntities) {
		entityRow = $('<tr><td><input type="submit" class="deleteAction" value="'+scriptEntities[i]+'" /></td><td><input type="submit" class="detailAction" value="'+scriptEntities[i]+'" /></td></tr>');
		entityList.append(entityRow); 
	} 
	$(".deleteAction").on("click", deleteEntity);
	$(".detailAction").on("click", navigateToScriptDetails);
}

function deleteEntity() {
	var deleteId = $(this).val();
	$.ajax({
		"url" : "rest/script/"+deleteId,
		"type" : "DELETE",
		"success" : function(entities) {
			renderEntityList(entities);
		},
		"error" : function(err) {
			console.log(err);
		}
	});
}

function navigateToScriptDetails() {
	var detailId = $(this).val();
	window.location.href = "scriptstatementpage.html?scriptName="+detailId;
}

function getAllEntitiesService(callback) {
$.ajax({
		"url" : "rest/script/getScripts",
	    // whether this is a POST or GET request
	    "type": "GET",
	    // the type of data we expect back
	    "dataType" : "json",
		"success" : function(entities) {
			callback(entities);
		},
		"error" : function(){
			alert("getAllStatementType Error");
		}
	});
         
}

</script>
</head>
<body>
<a href="mainmenu.html">Main Menu</a>
<h3>Enter Script Details</h3>
	<%-- <form name="scriptform" method="POST" action="scriptWithValues.html"> --%>
		<table>
			<tr>
				<td>Script Name:</td>
				<td><input type="text" name="scriptName" id="scriptName"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add Script" class="createScript"/></td>
			</tr>
		</table>
	<%-- </form> --%>
	<hr> 
	<b>Available Scripts:</b>
	<table border="" cellspacing="4" cellpadding="4" id="scriptTable">
		<%-- <c:forEach var="script" items="${scripts}">
			<form method="POST" action="editscript.html">
			<tr>
				<td><input type="submit" name="deleteAction" value="${script.getScriptName()}" /></td>
				<td><input type="submit" name="detailAction" value="${script.getScriptName()}" /></td>
			</tr>
			</form>
		</c:forEach> --%>
	</table>
</body>
</html>