package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.ScriptService;


@Controller
@SessionAttributes({"sessionScriptName", "sessionVariableObjects"})
public class ScriptController {
	
	@Autowired
	private ScriptService scriptService;
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
    @RequestMapping(value="/scriptWithValues")
    public String installScript(ModelMap model, HttpServletRequest request) {
    	
        scriptService.insertScript(model, request);
        return "scriptMain";
    }
    
    @RequestMapping(value="/editscript")
    public String editScript(ModelMap model, HttpServletRequest request) {
    		
		if (request.getParameter("deleteAction") != null) {

			scriptService.deleteScript(model, request);
			return "scriptMain";
		} else {			
			scriptService.updateScript(model, request);
			return "scriptstatementpage";
		}
    }
        
    @RequestMapping(value="/scriptMain")
    public String renderMainScripts(ModelMap model, HttpServletRequest request) {
        
    	codeGenUtils.renderScriptValues(model);
        return "scriptMain";
    }
    
}
