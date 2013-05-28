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
	        System.out.println("script chosen "+scriptName);
	        String scriptStmtType = String.valueOf(model.get("sessionStatementType"));
	        System.out.println("scriptStmtType chosen "+scriptStmtType);
	        String operationType = String.valueOf(model.get("sessionOperationType"));
	        System.out.println("operationType chosen "+operationType);
	        int dataStmtId = Integer.parseInt(request.getParameter("data1"));
	        System.out.println("data1 chosen "+dataStmtId);
	       
	        operationDao.persist(new Operation(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), operationTypeDao.getOperationType(operationType), dataDao.getData(dataStmtId), 
	        		dataDao.getData(0), dataDao.getData(0), dataDao.getData(0), "++", "--"));
	        
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
		 
			List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
			model.put("statementTypes", stmtTypes);

			List<Data> dataStatements = dataDao.getAllDataStatements();
			model.put("dataStatements", dataStatements);
	 }

}
