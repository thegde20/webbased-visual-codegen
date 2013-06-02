package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.dao.BranchDao;
import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.NumberOperationDao;
import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.dao.StringOperationDao;
import edu.neu.webapp.graphiccodegen.entities.Branch;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.NumberOperation;
import edu.neu.webapp.graphiccodegen.entities.OperationType;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StatementType;
import edu.neu.webapp.graphiccodegen.entities.StringOperation;

@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType"})
public class BranchController {
	
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
	
	@Autowired
	private StatementDao statementDao;
	
	@Autowired
	private BranchDao branchDao;
	
	 @RequestMapping(value="/branchWithValues")
	    public String installBranch(ModelMap model, HttpServletRequest request) {
	    	
	        String scriptName = String.valueOf(model.get("sessionScriptName"));
	        String scriptStmtType = String.valueOf(model.get("sessionStatementType"));
	        int branchingVarId = Integer.parseInt(request.getParameter("branchingVar"));
	        int trueStatementId = Integer.parseInt(request.getParameter("trueStatement"));
	        int falseStatementId = Integer.parseInt(request.getParameter("falseStatement"));

	        branchDao.persist(new Branch(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName),
	        		dataDao.getData(branchingVarId), statementDao.getStatement(trueStatementId), statementDao.getStatement(falseStatementId)));
	        
	        renderPageValues(model);
	        return "scriptstatementpage";
	    }
	 
	@RequestMapping(value = "/editbranchstatement")
	public String editDataStatement(ModelMap model, HttpServletRequest request) {
		
		if (request.getParameter("deleteAction") != null) {

			int oldBranchStatement = Integer.valueOf(request.getParameter("deleteAction"));
			branchDao.deleteBranchStatementById(oldBranchStatement);
			
			renderPageValues(model);
			
			return "scriptstatementpage";
		} else {

			renderPageValues(model);
			return "scriptstatementpage";
		}
	}

	 public void renderPageValues(ModelMap model){
		 
			List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
			model.put("statementTypes", stmtTypes);

			String scriptName = String.valueOf(model.get("sessionScriptName"));
			 
			List<Data> dataStatements = dataDao.getAllDataStatements(scriptName);
			model.put("dataStatements", dataStatements);
			
			List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
	        model.put("operationTypes", operationTypes);
			
		 	List<NumberOperation> numberOperations = numberOperationDao.getAllNumberOperationStatements(scriptName);
		 	model.put("numberOperations", numberOperations);
		 	
		 	List<StringOperation> stringOperations = stringOperationDao.getAllStringOperationStatements(scriptName);
			model.put("stringOperations", stringOperations);
			
			List<Statement> statements = statementDao.getAllStatements(scriptName);
			model.put("statements", statements);
			
			List<Branch> branchStatements = branchDao.getAllBranchStatements(scriptName);
			model.put("branchStatements", branchStatements);
			
	 }

}
