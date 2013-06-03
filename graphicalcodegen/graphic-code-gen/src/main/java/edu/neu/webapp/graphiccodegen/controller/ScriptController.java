package edu.neu.webapp.graphiccodegen.controller;

import java.util.ArrayList;
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
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.dao.StringOperationDao;
import edu.neu.webapp.graphiccodegen.entities.Branch;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.NumberOperation;
import edu.neu.webapp.graphiccodegen.entities.Script;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StatementType;
import edu.neu.webapp.graphiccodegen.entities.StringOperation;


@Controller
@SessionAttributes({"sessionScriptName", "sessionVariableObjects"})
public class ScriptController {
	
	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
	private StringOperationDao stringOperationDao;
	
	@Autowired
    private NumberOperationDao numberOperationDao;
	
	@Autowired
    private DataDao dataDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
	private StatementDao statementDao;
	
	@Autowired
	private BranchDao branchDao;
	
    @RequestMapping(value="/scriptWithValues")
    public String installScript(ModelMap model, HttpServletRequest request) {
    	
        String scriptName = request.getParameter("scriptName");
        
        scriptDao.persist(new Script(scriptName, new ArrayList<Statement>()));
        
        List<Script> scripts = scriptDao.getAllScripts();
        model.put("scripts", scripts);
        
        return "scriptMain";
    }
    
    @RequestMapping(value="/editscript")
    public String editScript(ModelMap model, HttpServletRequest request) {
    		
		if (request.getParameter("deleteAction") != null) {

			String oldScript = String.valueOf(request.getParameter("deleteAction"));
			scriptDao.deleteScriptById(oldScript);

			List<Script> scripts = scriptDao.getAllScripts();
			model.put("scripts", scripts);

			return "scriptMain";
		} else {
			
			model.put("sessionScriptName", request.getParameter("detailAction"));
			
			List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
	        model.put("statementTypes", stmtTypes);
	        
			String scriptName = String.valueOf(model.get("sessionScriptName"));
			 
			List<Data> dataStatements = dataDao.getAllDataStatements(scriptName);
		    model.put("sessionVariableObjects", dataStatements);
		    
		 	List<NumberOperation> numberOperations = numberOperationDao.getAllNumberOperationStatements(scriptName);
		 	model.put("numberOperations", numberOperations);
		 	
			List<Statement> statements = statementDao.getAllStatements(scriptName);
			model.put("statements", statements);
			
			List<Branch> branchStatements = branchDao.getAllBranchStatements(scriptName);
			model.put("branchStatements", branchStatements);
		 	
		 	List<StringOperation> stringOperations = stringOperationDao.getAllStringOperationStatements(scriptName);
			model.put("stringOperations", stringOperations);
	        
			return "scriptstatementpage";
		}
    }
        
    @RequestMapping(value="/scriptMain")
    public String renderMainScripts(ModelMap model, HttpServletRequest request) {
        
        List<Script> scripts = scriptDao.getAllScripts();
        model.put("scripts", scripts);
        
        return "scriptMain";
    }
    
}
