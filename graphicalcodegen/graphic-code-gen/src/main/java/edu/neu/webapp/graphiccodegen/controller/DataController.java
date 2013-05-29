package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.NumberOperationDao;
import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.dao.StringOperationDao;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.NumberOperation;
import edu.neu.webapp.graphiccodegen.entities.OperationType;
import edu.neu.webapp.graphiccodegen.entities.StatementType;
import edu.neu.webapp.graphiccodegen.entities.StringOperation;

@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType"})
public class DataController {

	@Autowired
    private DataDao dataDao;
	
	@Autowired
    private NumberOperationDao numberOperationDao;
	
	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
	private StringOperationDao stringOperationDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
    private OperationTypeDao operationTypeDao;
	
	
	 @RequestMapping(value="/statementWithValues")
	    public String renderChosenStatement(ModelMap model, HttpServletRequest request) {
		 	
		 	model.put("sessionStatementType", request.getParameter("scriptstmtType"));
		 	
		 	renderPageValues(model);
	        
	        return "scriptstatementpage";
	    }
	
	 @RequestMapping(value="/dataWithValues")
	    public String installData(ModelMap model, HttpServletRequest request) {
	    	
	        String scriptName = String.valueOf(model.get("sessionScriptName"));
	        System.out.println("script chosen "+scriptName);
	        String scriptStmtType = String.valueOf(model.get("sessionStatementType"));
	        System.out.println("scriptStmtType chosen "+scriptStmtType);
	        String dataName = request.getParameter("varName");
	        String dataType = request.getParameter("varType");
	        String dataValue = request.getParameter("varValue");
	        
	        System.out.println("data chosen "+dataName);
	        System.out.println("data type chosen "+dataType);
	        System.out.println("dataValue chosen "+dataValue);
	        
	        dataDao.persist(new Data(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), dataName, dataValue, dataType));
	        
	        renderPageValues(model);
	        return "scriptstatementpage";
	    }
	 
	@RequestMapping(value = "/editdatastatement")
	public String editDataStatement(ModelMap model, HttpServletRequest request) {
		
		if (request.getParameter("deleteAction") != null) {

			int oldDataStatement = Integer.valueOf(request.getParameter("deleteAction"));
			dataDao.deleteDataStatementById(oldDataStatement);
			
			renderPageValues(model);
			
			return "scriptstatementpage";
		} else {

			renderPageValues(model);
			return "scriptstatementpage";
		}
	}
	 
	 @RequestMapping(value="/scriptstatementpage")
	public String renderMainStatements(ModelMap model,HttpServletRequest request) {

		renderPageValues(model);
		return "scriptstatementpage";
	}
	 
	 public void renderPageValues(ModelMap model){
		 
			List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
			model.put("statementTypes", stmtTypes);

			List<Data> dataStatements = dataDao.getAllDataStatements();
			model.put("dataStatements", dataStatements);
			
			List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
	        model.put("operationTypes", operationTypes);
			
		 	List<NumberOperation> numberOperations = numberOperationDao.getAllNumberOperationStatements();
		 	model.put("numberOperations", numberOperations);
		 	
		 	List<StringOperation> stringOperations = stringOperationDao.getAllStringOperationStatements();
			model.put("stringOperations", stringOperations);
		 
	 }
}
