<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statement Types</title>
<script src="js/jquery-min.js"></script>
<script>

var entityList;
$(function(){
	entityList = $("#stmtTypeTable");
	updateEntityList();
	$(".createStmtType").click(createEntity);
});


function createEntity() {
	var entity = {
			"sType" : $("#sType").val()
	};
	$.ajax({
		"url" : "rest/statementtype",
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
	$("#sType").val("");
	entityList.empty();
	var entityRow;
	entityRow = $('<tr><th>Statement Type</th></tr>');
	entityList.append(entityRow); 
	for(var i in entities) {
		entityRow = $('<tr><td>'+entities[i]+'</td></tr>');
		entityList.append(entityRow); 
	} 
}

function getAllEntitiesService(callback) {
$.ajax({
		"url" : "rest/statementtype/getStatementType",
	    // whether this is a POST or GET request
	    type: "GET",
	    // the type of data we expect back
	    dataType : "json",
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
<h3>Enter Statement Type Details</h3>
	<%-- <form name="statementTypeform" method="POST" action="statementTypeWithValues.html"> --%>
		<table>
			<tr>
				<td>Statement Type:</td>
				<td><input type="text" name="sType" id="sType"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="Add" class="createStmtType"/></td>
			</tr>
		</table>
	<%-- </form> --%>
	<hr> 
	<b><u>Existing Statement Types:</u></b>
	<table border="" cellspacing="4" cellpadding="4" id="stmtTypeTable">
		<%-- <c:forEach var="sType" items="${statementTypes}">
			<tr>
				<td>${sType.getsType()}</td>
			</tr>
		</c:forEach> --%>
	</table>
</body>
</html>