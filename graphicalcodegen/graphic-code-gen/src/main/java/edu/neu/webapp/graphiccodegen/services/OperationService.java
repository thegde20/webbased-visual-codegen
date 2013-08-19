package edu.neu.webapp.graphiccodegen.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.NumberOperationDao;
import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.dao.StringOperationDao;
import edu.neu.webapp.graphiccodegen.entities.NumberOperation;
import edu.neu.webapp.graphiccodegen.entities.StringOperation;

public class OperationService {
	
	@Autowired
    private DataDao dataDao;

	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
	private StringOperationDao stringOperationDao;
	
	@Autowired
    private NumberOperationDao numberOperationDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
    private OperationTypeDao operationTypeDao;
	
	@Autowired
	private CodeGenUtils codeGenUtils;

	public void insertOperation(ModelMap model, HttpServletRequest request){
		
		String scriptName = String.valueOf(model.get("sessionScriptName"));
		String scriptStmtType = String.valueOf(model.get("sessionStatementType"));
		String operationType = String.valueOf(model.get("sessionOperationType"));

		if (operationType.equalsIgnoreCase("Unary")) {

			int dataStmtId = Integer.parseInt(request.getParameter("unaryData1"));
			String operator = request.getParameter("unaryOperator");

			numberOperationDao.persist(new NumberOperation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), 
					operationTypeDao.getOperationType(operationType), dataDao.getData(dataStmtId), dataDao.getData(0), dataDao.getData(0), operator, dataDao.getData(0), null));

		} else if (operationType.equalsIgnoreCase("Binary")) {

			int resultId = Integer.parseInt(request.getParameter("result"));
			int dataStmtId1 = Integer.parseInt(request.getParameter("binaryData1"));
			int dataStmtId2 = Integer.parseInt(request.getParameter("binaryData2"));
			String operator = request.getParameter("binaryOperator");

			numberOperationDao.persist(new NumberOperation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), operationTypeDao.getOperationType(operationType), 
					dataDao.getData(dataStmtId1), dataDao.getData(dataStmtId2),dataDao.getData(resultId), operator, dataDao.getData(0),null));

		} else if (operationType.equalsIgnoreCase("Decision")) {

			int resultId = Integer.parseInt(request.getParameter("result"));
			int dataStmtId1 = Integer.parseInt(request.getParameter("decisionData1"));
			int dataStmtId2 = Integer.parseInt(request.getParameter("decisionData2"));
			int dataStmtId3 = Integer.parseInt(request.getParameter("decisionData3"));
			String operator1 = "?";
			String operator2 = ":";

			numberOperationDao.persist(new NumberOperation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), 
					operationTypeDao.getOperationType(operationType), dataDao.getData(dataStmtId1), dataDao.getData(dataStmtId2),
					dataDao.getData(resultId), operator1, dataDao.getData(dataStmtId3), operator2));

		} else if (operationType.equalsIgnoreCase("Substring")) {

			int resultId = Integer.parseInt(request.getParameter("substringResult"));
			int dataStmtId1 = Integer.parseInt(request.getParameter("substringData1"));
			String operator = "substring";
			int startIndex = Integer.parseInt(request.getParameter("startIndex"));
			int endIndex = Integer.parseInt(request.getParameter("endIndex"));

			stringOperationDao.persist(new StringOperation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), operationTypeDao.getOperationType(operationType), 
					dataDao.getData(dataStmtId1), dataDao.getData(0), dataDao.getData(resultId), operator, startIndex, endIndex));
			
		} else if (operationType.equalsIgnoreCase("Concat")) {

			int resultId = Integer.parseInt(request.getParameter("concatResult"));
			int dataStmtId1 = Integer.parseInt(request.getParameter("concatData1"));
			int dataStmtId2 = Integer.parseInt(request.getParameter("concatData2"));
			String operator = "concat";

			stringOperationDao.persist(new StringOperation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), operationTypeDao.getOperationType(operationType), 
					dataDao.getData(dataStmtId1), dataDao.getData(dataStmtId2),dataDao.getData(resultId), operator, 0, 0));
		}else if(operationType.equalsIgnoreCase("Assignment")){

			int resultId = Integer.parseInt(request.getParameter("assignmentData1"));
			int dataStmtId1 = Integer.parseInt(request.getParameter("assignmentData1"));
			int dataStmtId2 = Integer.parseInt(request.getParameter("assignmentData2"));
			String operator = "=";

			numberOperationDao.persist(new NumberOperation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), operationTypeDao.getOperationType(operationType), 
					dataDao.getData(dataStmtId1), dataDao.getData(dataStmtId2),dataDao.getData(resultId), operator, dataDao.getData(0),null));
 
		}

		codeGenUtils.renderPageValues(model);

	}
	
	public void deleteNumberOperation(ModelMap model, HttpServletRequest request) {
		
		int numberOperationStatementId = Integer.valueOf(request.getParameter("deleteAction"));
		numberOperationDao.deleteNumberOperationById(numberOperationStatementId);
		codeGenUtils.renderPageValues(model);
	}
	
	public void updateNumberOperation(ModelMap model, HttpServletRequest request) {
		
		int operationStatementId = Integer.valueOf(request.getParameter("updateAction"));
		
		if(request.getParameter("numberOperationType").equalsIgnoreCase("Unary")){
		/*	updatedUnaryVar
			updatedUnaryOperator*/
		}else if(request.getParameter("numberOperationType").equalsIgnoreCase("Binary")){
			
		}else if(request.getParameter("numberOperationType").equalsIgnoreCase("Assignment")){
			
			int newAssignmentDataStmtId1 = Integer.parseInt(request.getParameter("updatedAssignmentData1"));
			int newAssignmentDataStmtId2 = Integer.parseInt(request.getParameter("updatedAssignmentData2"));
			
			numberOperationDao.numberOperationAfterUpdate(operationStatementId, "Assignment", dataDao.getData(0), dataDao.getData(newAssignmentDataStmtId1), dataDao.getData(newAssignmentDataStmtId2), dataDao.getData(0), "=", null);
			
		}
		
		codeGenUtils.renderPageValues(model);
		
	}

	public void deleteStringOperation(ModelMap model, HttpServletRequest request) {
		
		int oldStringOperation = Integer.valueOf(request.getParameter("deleteAction"));
		stringOperationDao.deleteStringOperationStatementById(oldStringOperation);
		codeGenUtils.renderPageValues(model);
	}
	
	public void updateStringOperation(ModelMap model, HttpServletRequest request) {
		
		int operationStatementId = Integer.valueOf(request.getParameter("updateAction"));
		
		//updating Substring operation
		if(request.getParameter("stringOperationType").equalsIgnoreCase("Substring")){
			
			int newResult = Integer.parseInt(request.getParameter("updatedSubstringResult"));
			int newData1 = Integer.parseInt(request.getParameter("updatedSubstringData1"));
			int newStartIndex = Integer.parseInt(request.getParameter("updatedStartIndex"));
			int newEndIndex = Integer.parseInt(request.getParameter("updatedEndIndex"));
			
			stringOperationDao.StringOperationAfterUpdatedSubstring(operationStatementId, dataDao.getData(newResult), dataDao.getData(newData1), newStartIndex, newEndIndex);
			
		}
		//updating Concat operation
		else{
			int newResultId = Integer.parseInt(request.getParameter("UpdatedConcatResult"));
			int newDataStmtId1 = Integer.parseInt(request.getParameter("UpdatedConcatData1"));
			int newDataStmtId2 = Integer.parseInt(request.getParameter("UpdatedConcatData2"));
			
			stringOperationDao.StringOperationAfterUpdatedConcat(operationStatementId, dataDao.getData(newResultId), dataDao.getData(newDataStmtId1), dataDao.getData(newDataStmtId2));
		}
		codeGenUtils.renderPageValues(model);
	}
	
}
