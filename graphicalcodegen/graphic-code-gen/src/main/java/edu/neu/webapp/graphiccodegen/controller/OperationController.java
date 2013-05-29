package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.OperationDao;
import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.Operation;
import edu.neu.webapp.graphiccodegen.entities.OperationType;
import edu.neu.webapp.graphiccodegen.entities.StatementType;

@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType", "sessionOperationType"})
public class OperationController {
	
	@Autowired
    private DataDao dataDao;

	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
    private OperationDao operationDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
    private OperationTypeDao operationTypeDao;
	
	 @RequestMapping(value="/operationWithValues")
	    public String installData(ModelMap model, HttpServletRequest request) {
	    	
	        String scriptName = String.valueOf(model.get("sessionScriptName"));
	        String scriptStmtType = String.valueOf(model.get("sessionStatementType"));
	        String operationType = String.valueOf(model.get("sessionOperationType"));
	        System.out.println("Chosen Operation Type "+operationType);
	        System.out.println("------------------------------------");
	        
		if(operationType.equalsIgnoreCase("Unary")) {

			int dataStmtId = Integer.parseInt(request.getParameter("unaryData1"));
			String operator = request.getParameter("unaryOperator");
			operationDao.persist(new Operation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), 
					operationTypeDao.getOperationType(operationType), dataDao.getData(dataStmtId), dataDao.getData(0), dataDao.getData(0),
					dataDao.getData(0), operator, null));
		}else if(operationType.equalsIgnoreCase("Binary")){
			
			int dataStmtId1 = Integer.parseInt(request.getParameter("binaryData1"));
			int dataStmtId2 = Integer.parseInt(request.getParameter("binaryData2"));
			String operator = request.getParameter("binaryOperator");
			operationDao.persist(new Operation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), 
					operationTypeDao.getOperationType(operationType), dataDao.getData(dataStmtId1), dataDao.getData(dataStmtId2), dataDao.getData(0),
					dataDao.getData(0), operator, null));
		}else  if(operationType.equalsIgnoreCase("Ternary")){
			int dataStmtId1 = Integer.parseInt(request.getParameter("ternaryData1"));
			int dataStmtId2 = Integer.parseInt(request.getParameter("ternaryData2"));
			int dataStmtId3 = Integer.parseInt(request.getParameter("ternaryData3"));
			String operator1 = request.getParameter("ternaryOperator1");
			String operator2 = request.getParameter("ternaryOperator2");
			operationDao.persist(new Operation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), 
					operationTypeDao.getOperationType(operationType), dataDao.getData(dataStmtId1), dataDao.getData(dataStmtId2), dataDao.getData(dataStmtId3),
					dataDao.getData(0), operator1, operator2));
		}

		renderPageValues(model);
		return "scriptstatementpage";
	}
	 
	 @RequestMapping(value="/renderoperation")
	    public String renderChosenOperation(ModelMap model, HttpServletRequest request) {
	
		 	model.put("sessionOperationType",request.getParameter("oType"));
		 	
		 	renderPageValues(model);
	        
	        return "scriptstatementpage";
	    }
	 
	@RequestMapping(value = "/editoperationstatement")
	public String editDataStatement(ModelMap model, HttpServletRequest request) {
		
		if (request.getParameter("deleteAction") != null) {

			int oldOperationStatement = Integer.valueOf(request.getParameter("deleteAction"));
			operationDao.deleteOperationStatementById(oldOperationStatement);
			
			renderPageValues(model);
			
			return "scriptstatementpage";
		} else {

			renderPageValues(model);
			return "scriptstatementpage";
		}
	}

	 public void renderPageValues(ModelMap model){
		 
		 	List<Operation> operationStatements = operationDao.getAllOperationStatements();
		 	model.put("operationStatements", operationStatements);
		 	
			List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
	        model.put("operationTypes", operationTypes);
		 
			List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
			model.put("statementTypes", stmtTypes);

			List<Data> dataStatements = dataDao.getAllDataStatements();
			model.put("dataStatements", dataStatements);
	 }

}
