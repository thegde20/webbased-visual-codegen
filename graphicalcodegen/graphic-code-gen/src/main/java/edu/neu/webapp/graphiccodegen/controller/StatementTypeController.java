package edu.neu.webapp.graphiccodegen.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StatementType;

@Controller
public class StatementTypeController {
	
	@Autowired
    private StatementTypeDao statementTypeDao;

	@RequestMapping(value="/statementTypeWithValues")
    public String installCompany(ModelMap model, HttpServletRequest request) {
    	
        String sType = request.getParameter("sType");
        
        statementTypeDao.persist(new StatementType(sType, new ArrayList<Statement>()));
        
        List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
        model.put("statementTypes", stmtTypes);
        
        return "statementtypemain";
    }
	
	@RequestMapping(value="/statementtypemain")
    public String renderMainStatementTypes(ModelMap model, HttpServletRequest request) {
        
		 List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
	     model.put("statementTypes", stmtTypes);
        
        return "statementtypemain";
    }
}
