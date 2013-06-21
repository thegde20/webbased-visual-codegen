package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.TypeService;

@Controller
public class OperationTypeController {

	@Autowired
    private TypeService typeService;
	
	@Autowired
	private CodeGenUtils codeGenUtils;

	@RequestMapping(value="/operationTypeWithValues")
    public String installOperationType(ModelMap model, HttpServletRequest request) {
    	
		typeService.insertOperationType(model, request);
        return "operationtypemain";
    }
	
	@RequestMapping(value="/operationtypemain")
    public String renderMainStatementTypes(ModelMap model, HttpServletRequest request) {
       
		codeGenUtils.renderOperationTypeValues(model);
        return "operationtypemain";
    }
}
