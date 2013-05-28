<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Script Statements</title>
</head>
<body>
	<a href="scriptMain.html">Scripts</a>
	<br>
	<i>Statements of <c:out value="${sessionScriptName}" /></i> <c:set var="scriptstmtType" value="${sessionStatementType}" />
	<br>
	<h3>Enter New Statement details:</h3>
	<form name="statementform" method="POST" action="statementWithValues.html">
		<table>
			<tr>
				<td>Statement Type:</td>
				<td><select name="scriptstmtType">
						<c:forEach var="statementType" items="${statementTypes}">
							<option value="${statementType.getsType()}">${statementType.getsType()}</option>
						</c:forEach>
				</select></td>
				<td><input type="submit" value="Add" /></td>
			</tr>
		</table>
	</form>
	<hr>
	<c:choose>
		<c:when test="${scriptstmtType eq 'Declarative'}">
			<form name="variableform" method="POST" action="dataWithValues.html">
				<table cellspacing="4" cellpadding="4">
					<tr>
						<th>Declarative Statement</th>
					</tr>
					<tr>
						<td>Variable Name:</td>
						<td><input type="text" name="varName" /></td>
					</tr>
					<tr>
						<td>Type:</td>
						<td><Select name="varType">
								<option value="int">Integer</option>
								<option value="string">String</option>
								<option value="date">Date</option>
						</Select></td>
					</tr>
					<tr>
						<td>Value:</td>
						<td><input type="text" name="varValue" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Add variable" /></td>
					</tr>
				</table>
			</form>
		</c:when>
		<c:when test="${scriptstmtType eq 'Operational'}">
		<form name="operationtypeform" method="POST" action="renderoperation.html"> 
				<table border="" cellspacing="4" cellpadding="4">
					<tr>
						<td>Operation Type:</td>
						<td><select name="oType">
								<option value="unary">Unary</option>
								<option value="binary">Binary</option>
								<option value="ternary">Ternary</option>
						</select></td>
						<td><input type="submit" value="Go" /></td></tr></table></form>
					<form name="operationform" method="POST" action="operationWithValues.html">
							<c:choose>
								<c:when test="${param.oType eq 'unary'}">
									<table>
										<tr>
											<th>Operand</th>
											<th>Operator</th>
										</tr>
										<tr>
											<td><select name="data1">
													<c:forEach var="data" items="${dataStatements}">
														<option value="${data.getStatementId()}">${data.getDataName()}</option>
													</c:forEach>
											</select></td>
											<td><select name="operator1">
													<option value="++">++</option>
													<option value="--">--</option>
											</select></td>
											<td><input type="submit" value="Add operation" /></td>
										</tr>
									</table>
								</c:when>
								<c:otherwise>
									<i>Choose Operation type</i>
								</c:otherwise>
							</c:choose>
						</form>
		</c:when>
		<c:otherwise>
			<i>Choose Statement type</i>
		</c:otherwise>
	</c:choose>
	<hr>
	<b>Existing Statements:</b>
	<table border="" cellspacing="4" cellpadding="4">
		<tr>
			<th>Operation Type</th>
			<th>Delete</th>
			<th>Detail</th>
		</tr>
		<c:forEach var="dataStatement" items="${dataStatements}">
			<form method="POST" action="editdatastatement.html">
				<tr>
					<td>${dataStatement.getStatementType().getsType()}</td>
					<td><input type="submit" name="deleteAction" value="${dataStatement.getStatementId()}" /></td>
					<td><input type="submit" name="detailAction" value="${dataStatement.getStatementId()}" /></td>
				</tr>
			</form>
		</c:forEach>
		<c:forEach var="operationStatement" items="${operationStatements}">
			<form method="POST" action="editoperationstatement.html">
			<tr>
				<td>${operationStatement.getStatementType().getsType()}</td>
				<td><input type="submit" name="deleteAction" value="${operationStatement.getStatementId()}" /></td>
				<td><input type="submit" name="detailAction" value="${operationStatement.getStatementId()}" /></td>
			</tr>
			</form>
		</c:forEach>
	</table>
</body>
</html>