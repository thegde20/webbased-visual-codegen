package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.OperationService;

@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType", "sessionOperationType", "sessionVariableObjects"})
public class OperationController {

	@Autowired
	private OperationService operationService;
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
	 @RequestMapping(value="/operationWithValues")
	    public String installOperation(ModelMap model, HttpServletRequest request) {
		 			
		 			operationService.insertOperation(model, request);
	        		return "scriptstatementpage";
	}

	@RequestMapping(value="/renderoperation")
	    public String renderChosenOperation(ModelMap model, HttpServletRequest request) {
	
		 	model.put("sessionOperationType",request.getParameter("oType"));
		 	codeGenUtils.renderPageValues(model);
	        return "scriptstatementpage";
	    }
	 
	@RequestMapping(value = "/editnumberoperation")
	public String editOperationStatement(ModelMap model, HttpServletRequest request) {
		
		if (request.getParameter("deleteAction") != null) {

			operationService.deleteNumberOperation(model, request);
			return "scriptstatementpage";
			
		} else {
			
			operationService.updateNumberOperation(model, request);
			return "scriptstatementpage";
		}
	}
	
	@RequestMapping(value = "/editstringoperation")
	public String editStringOperation(ModelMap model, HttpServletRequest request) {
		
		if (request.getParameter("deleteAction") != null) {

			operationService.deleteStringOperation(model, request);
			return "scriptstatementpage";
			
		} else {

			operationService.updateStringOperation(model, request);
			return "scriptstatementpage";
		}
	}

}
