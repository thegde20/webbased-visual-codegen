package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.services.BranchService;

@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType"})
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	 @RequestMapping(value="/branchWithValues")
	    public String installBranch(ModelMap model, HttpServletRequest request) {
	    	
		 	branchService.insertBranch(model, request);
	        return "scriptstatementpage";
	    }
	 
	@RequestMapping(value = "/editbranchstatement")
	public String editDataStatement(ModelMap model, HttpServletRequest request) {
		
		if (request.getParameter("deleteAction") != null) {

			branchService.deleteBranch(model, request);
			return "scriptstatementpage";
			
		} else {

			branchService.updateBranch(model, request);
			return "scriptstatementpage";
		}
	}

}
