package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.TypeService;

@Controller
public class StatementTypeController {
	
	@Autowired
    private TypeService typeService;
	
	@Autowired
	private CodeGenUtils codeGenUtils;

	@RequestMapping(value="/statementTypeWithValues")
    public String installCompany(ModelMap model, HttpServletRequest request) {
    	
		typeService.insertStatementType(model, request);
        return "statementtypemain";
    }
	
	@RequestMapping(value="/statementtypemain")
    public String renderMainStatementTypes(ModelMap model, HttpServletRequest request) {
        
		codeGenUtils.renderStatementTypeValues(model);
        return "statementtypemain";
    }
}
