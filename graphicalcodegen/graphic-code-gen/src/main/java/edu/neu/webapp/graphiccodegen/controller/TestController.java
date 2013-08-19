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
import edu.neu.webapp.graphiccodegen.entities.Data;


@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType", "sessionInputVariables"})
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
        
        Data dataObject1 = new Data(statementTypeDao.getStatementType("Data"), scriptDao.getScript("script1"), var1, value1, type1, false);
        Data dataObject2 = new Data(statementTypeDao.getStatementType("Data"), scriptDao.getScript("script1"), var2, value2, type2, false);
        
		List<Data> dataArray = new ArrayList<Data>();
        
        dataArray.add(dataObject1);
        dataArray.add(dataObject2);
        
        // Put the data variables array into a session object
        model.put("sessionInputVariables", dataArray);
        
        //Persisting the new data to the Data table in database
   /*     dataDao.persist(dataObject1);
        dataDao.persist(dataObject2);*/
        
        return "displayFinalValues";
    }

	@RequestMapping(value = "/testpage")
	public String testMenu() {
		return "testpage";
	}
}

