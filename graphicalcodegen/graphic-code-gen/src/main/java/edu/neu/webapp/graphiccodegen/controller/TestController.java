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
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StatementType;
import edu.neu.webapp.graphiccodegen.entities.StringOperation;


@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType", "sessionVariableObjects"})
public class TestController {
	
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
	
	@RequestMapping(value="/insertVariableValues")
    public String insertVariableValues(ModelMap model, HttpServletRequest request) {
    	
        String var1 = request.getParameter("var1");
        String value1 = request.getParameter("value1");
        String type1 = request.getParameter("type1");
        
        String var2 = request.getParameter("var2");
        String value2 = request.getParameter("value2");
        String type2 = request.getParameter("type2");
        
        Data dataObject1 = new Data(statementTypeDao.getStatementType("Declarative"), scriptDao.getScript("script1"), var1, value1, type1);
        Data dataObject2 = new Data(statementTypeDao.getStatementType("Declarative"), scriptDao.getScript("script1"), var2, value2, type2);
        
        dataDao.persist(dataObject1);
        dataDao.persist(dataObject2);
        
        List<Data> dataArray = new ArrayList<Data>();
        dataArray.add(dataObject1);
        dataArray.add(dataObject2);
        
  	  	model.put("sessionScriptName","script1");
        
        model.put("sessionVariableObjects", dataArray);
        
        List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
        model.put("statementTypes", stmtTypes);
        
        String scriptName = String.valueOf(model.get("sessionScriptName"));
        
        List<Data> dataStatements = dataDao.getAllDataStatements(scriptName);
	    model.put("dataStatements", dataStatements);
	    
	 	List<NumberOperation> numberOperations = numberOperationDao.getAllNumberOperationStatements(scriptName);
	 	model.put("numberOperations", numberOperations);
	 	
		List<Statement> statements = statementDao.getAllStatements(scriptName);
		model.put("statements", statements);
		
		List<Branch> branchStatements = branchDao.getAllBranchStatements(scriptName);
		model.put("branchStatements", branchStatements);
	 	
	 	List<StringOperation> stringOperations = stringOperationDao.getAllStringOperationStatements(scriptName);
		model.put("stringOperations", stringOperations);
        
        return "mainmenu";
    }

	@RequestMapping(value = "/testpage")
	public String testMenu(ModelMap model, HttpServletRequest request) {

		List<Data> dataStatements = dataDao.getAllDataStatements("script1");
		model.put("sessionVariableObjects", dataStatements);

		return "testpage";
	}
}

