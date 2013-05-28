package edu.neu.webapp.graphiccodegen.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.OperationDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.Operation;
import edu.neu.webapp.graphiccodegen.entities.Script;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StatementType;


@Controller
@SessionAttributes("sessionScriptName")
public class ScriptController {
	
	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
    private DataDao dataDao;
	
	@Autowired
    private OperationDao operationDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
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
			
			List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
	        model.put("statementTypes", stmtTypes);
	        
	        List<Data> dataStatements = dataDao.getAllDataStatements();
		    model.put("dataStatements", dataStatements);
		    
		 	List<Operation> operationStatements = operationDao.getAllOperationStatements();
		 	model.put("operationStatements", operationStatements);
	        
			model.put("sessionScriptName", request.getParameter("detailAction"));
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
