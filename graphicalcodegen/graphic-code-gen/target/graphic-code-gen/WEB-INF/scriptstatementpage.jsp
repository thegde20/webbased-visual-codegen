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
								<option value="double">Double</option>
								<option value="boolean">Boolean</option>
								<option value="String">String</option>
								<option value="date">Date</option>
						</Select></td>
					</tr>
					<tr>
						<td>Value:</td>
						<td><input type="text" name="varValue" value=0 /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Add variable" /></td>
					</tr>
				</table>
			</form>
		</c:when>
		<c:when test="${scriptstmtType eq 'Branch'}">
			<form name="variableform" method="POST" action="branchWithValues.html">
				<table>
					<tr>
						<th>Operand</th>
						<th>?</th>
						<th>if True</th>
						<th>:</th>
						<th>Else</th>
					</tr>
					<tr>
						<td><select name="branchingVar">
								<c:forEach var="data" items="${dataStatements}">
									<c:if test="${data.getDataType() eq 'boolean'}">
										<option value="${data.getStatementId()}">${data.getDataName()}</option>
									</c:if>
								</c:forEach>
								<c:forEach var="varObject" items="${sessionVariableObjects}">
									<c:if test="${varObject.getDataType() eq 'boolean'}">
										<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
									</c:if>
								</c:forEach>
						</select></td>
						<td>?</td>
						<td><select name="trueStatement">
								<c:forEach var="statement" items="${statements}">
									<option value="${statement.getStatementId()}">${statement.getStatementId()}</option>
								</c:forEach>
						</select></td>
						<td>:</td>
						<td><select name="falseStatement">
								<c:forEach var="statement" items="${statements}">
									<option value="${statement.getStatementId()}">${statement.getStatementId()}</option>
								</c:forEach>
						</select></td>
						<td><input type="submit" value="Add operation" /></td>
					</tr>
				</table>
			</form>
		</c:when>
		<c:when test="${scriptstmtType eq 'Operational'}">
			<form name="operationtypeform" method="POST"
				action="renderoperation.html">
				<table border="" cellspacing="4" cellpadding="4">
					<tr>
						<td>Operation Type:</td>
						<td><select name="oType">
								<c:forEach var="operationType" items="${operationTypes}">
									<option value="${operationType.getoType()}">${operationType.getoType()}</option>
								</c:forEach>
						</select></td>
						<td><input type="submit" value="Go" /></td>
					</tr>
				</table>
			</form>
			<hr>
			<form name="operationform" method="POST" action="operationWithValues.html">
							<c:choose>
								<c:when test="${param.oType eq 'Unary'}">
									<table>
										<tr>
											<th>Operand</th>
											<th>Operator</th>
										</tr>
										<tr>
											<td><select name="unaryData1">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<c:if test="${varObject.getDataType() eq 'double' or varObject.getDataType() eq 'int'}">
															<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
														</c:if>
													</c:forEach>
											</select></td>
											<td><select name="unaryOperator">
													<option value="++">++</option>
													<option value="--">--</option>
											</select></td>
											<td><input type="submit" value="Add operation" /></td>
										</tr>
									</table>
								</c:when>
								<c:when test="${param.oType eq 'Substring'}">
									<table>
										<tr>
											<th>Operand</th>
											<th>=</th>
											<th>Substring Of</th>
											<th>Start Index</th>
											<th>End Index</th>
										</tr>
										<tr>
											<td><select name="substringResult">
													<c:forEach var="data" items="${dataStatements}">
														<c:if test="${data.getDataType() eq 'String'}">
															<option value="${data.getStatementId()}">${data.getDataName()}</option>
														</c:if>
													</c:forEach>
											</select></td>
											<td>=</td>
											<td><select name="substringData1">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<c:if test="${varObject.getDataType() eq 'String'}">
															<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
														</c:if>
													</c:forEach>
												</select></td>
											<td><input type="text" name="startIndex" /></td>
											<td><input type="text" name="endIndex" /></td>
											<td><input type="submit" value="Add operation" /></td>
										</tr>
									</table>
								</c:when>
								<c:when test="${param.oType eq 'Concat'}">
									<table>
										<tr>
											<th>Operand</th>
											<th>=</th>
											<th>Operand</th>
											<th>Concatenate with</th>										
										</tr>
										<tr>
											<td><select name="concatResult">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<c:if test="${varObject.getDataType() eq 'String'}">
															<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
														</c:if>
													</c:forEach>
											</select></td>
											<td>=</td>
											<td><select name="concatData1">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<c:if test="${varObject.getDataType() eq 'String'}">
															<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
														</c:if>
													</c:forEach>
												</select></td>
											<td><select name="concatData2">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<c:if test="${varObject.getDataType() eq 'String'}">
															<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
														</c:if>
													</c:forEach>
												</select></td>
											<td><input type="submit" value="Add operation" /></td>
										</tr>
									</table>
								</c:when>
								<c:when test="${param.oType eq 'Binary'}">
									<table>
										<tr>
											<th>Operand</th>
											<th>Operator</th>
											<th>Operand</th>
											<th>=</th>
											<th>Operand</th>
										</tr>
										<tr>
											<td><select name="binaryData1">
													<c:forEach var="data" items="${dataStatements}">
														<option value="${data.getStatementId()}">${data.getDataName()}</option>
													</c:forEach>
											</select></td>
											<td><select name="binaryOperator">
													<option value="+">+</option>
													<option value="-">-</option>
													<option value="*">*</option>
													<option value="/">/</option>
													<option value="^">^</option>
													<option value=">">&gt;</option>
													<option value="<">&lt;</option>
													<option value="==">==</option>
											</select></td>
											<td><select name="binaryData2">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
													</c:forEach>
											</select></td>
											<td>=</td>
											<td><select name="result">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
													</c:forEach>
											</select></td>
											<td><input type="submit" value="Add operation" /></td>
										</tr>
									</table>
								</c:when>
								<c:when test="${param.oType eq 'Decision'}">
									<table>
										<tr>
											<th>Operand</th>
											<th>=</th>
											<th>Operand</th>
											<th>Operator</th>
											<th>Operand</th>
											<th>Operator</th>
											<th>Operand</th>
										</tr>
										<tr>
											<td><select name="result">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
													</c:forEach>
											</select></td>
											<td>=</td>
											<td><select name="decisionData1">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<c:if test="${varObject.getDataType() eq 'boolean'}">
															<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
														</c:if>
													</c:forEach>
											</select></td>
											<td>?</td>
											<td><select name="decisionData2">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
													</c:forEach>
											</select></td>
											<td>:</td>
											<td><select name="decisionData3">
													<c:forEach var="varObject" items="${sessionVariableObjects}">
														<option value="${varObject.getStatementId()}">${varObject.getDataName()}</option>
													</c:forEach>
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
			<th>Delete Statement</th>
			<th>Operation Type</th>
			<th>Statement</th>
			<th>Update</th>
		</tr>
		<c:forEach var="dataStatement" items="${sessionVariableObjects}">
			<form method="POST" action="editdatastatement.html">
				<tr>
					<td><input type="submit" name="deleteAction" value="${dataStatement.getStatementId()}" /></td>
					<td>${dataStatement.getStatementType().getsType()}</td>
					<td><input type="text" size="50" name="detail" disabled="disabled" value="${dataStatement.getDataType()} ${dataStatement.getDataName()} = ${dataStatement.getDataValue()};" /></td>
					<td><input type="submit" name="updateAction" value="${dataStatement.getStatementId()}" /></td>
				</tr>
			</form>
		</c:forEach>
		<c:forEach var="numberOperation" items="${numberOperations}">
			<form method="POST" action="editnumberoperation.html">
			<tr>
				<td><input type="submit" name="deleteAction" value="${numberOperation.getStatementId()}" /></td>
				<td>${numberOperation.getStatementType().getsType()} - ${numberOperation.getOperationType().getoType()}</td>
				<c:if test="${numberOperation.getOperationType().getoType() eq 'Unary'}">
					<td><input type="text" size="50" name="detail" disabled="disabled" value="${numberOperation.getData1().getDataName()}${numberOperation.getOperator1()};" /></td>
				</c:if>
				<c:if test="${numberOperation.getOperationType().getoType() eq 'Binary'}">
					<td><input type="text" size="50" name="detail" disabled="disabled" value="${numberOperation.getResult().getDataName()} = ${numberOperation.getData1().getDataName()} ${numberOperation.getOperator1()} ${numberOperation.getData2().getDataName()};" /></td>
				</c:if>
				<c:if test="${numberOperation.getOperationType().getoType() eq 'Decision'}">
					<td><input type="text" size="50" name="detail" disabled="disabled" value="${numberOperation.getResult().getDataName()} = ${numberOperation.getData1().getDataName()} ${numberOperation.getOperator1()} ${numberOperation.getData2().getDataName()} ${numberOperation.getOperator2()} ${numberOperation.getData3().getDataName()};" /></td>
				</c:if>
				<td><input type="submit" name="updateAction" value="${numberOperation.getStatementId()}" /></td>
			</tr>
			</form>
		</c:forEach>
		<c:forEach var="stringOperation" items="${stringOperations}">
			<form method="POST" action="editstringoperation.html">
			<tr>
				<td><input type="submit" name="deleteAction" value="${stringOperation.getStatementId()}" /></td>
				<td>${stringOperation.getStatementType().getsType()} - ${stringOperation.getOperationType().getoType()}</td>
				<c:if test="${stringOperation.getOperationType().getoType() eq 'Substring'}">
					<td><input type="text" name="detail" disabled="disabled" value="${stringOperation.getResult().getDataName()} = ${stringOperation.getData1().getDataName()}.${stringOperation.getOperator1()}(${stringOperation.getIndex1()},${stringOperation.getIndex2()});" /></td>
				</c:if>
				<c:if test="${stringOperation.getOperationType().getoType() eq 'Concat'}">
					<td><input type="text" size="50" name="detail" disabled="disabled" value="${stringOperation.getResult().getDataName()} = ${stringOperation.getData1().getDataName()}.${stringOperation.getOperator1()}(${stringOperation.getData2().getDataName()});" /></td>
				</c:if>
				<td><input type="submit" name="updateAction" value="${stringOperation.getStatementId()}" /></td>
			</tr>
			</form>
		</c:forEach>
				<c:forEach var="branchStatement" items="${branchStatements}">
			<form method="POST" action="editbranchstatement.html">
				<tr>
					<td><input type="submit" name="deleteAction" value="${branchStatement.getStatementId()}" /></td>
					<td>${branchStatement.getStatementType().getsType()}</td>
					<td><input type="text" size="50" name="detail" disabled="disabled" value="if(${branchStatement.getBranchingData().getDataName()}){${branchStatement.getTrueStatementId().getStatementId()};} else{${branchStatement.getFalseStatementId().getStatementId()};}" /></td>
					<td><input type="submit" name="updateAction" value="${branchStatement.getStatementId()}" /></td>
				</tr>
			</form>
		</c:forEach>
	</table>
</body>
</html>