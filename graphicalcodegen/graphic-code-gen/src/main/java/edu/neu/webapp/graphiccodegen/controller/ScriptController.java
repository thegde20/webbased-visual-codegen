package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.entities.Script;
import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.ScriptService;


@Controller
@Path("/script")
@SessionAttributes({"sessionScriptName", "sessionVariableObjects"})
public class ScriptController {
	
	@Autowired
	private ScriptService scriptService;
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
/*    @RequestMapping(value="/scriptWithValues")
    public String installScript(ModelMap model, HttpServletRequest request) {
    	
        scriptService.insertScript(model, request);
        return "scriptMain";
    }*/
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public List<String> addScript(Script script) {
		return scriptService.insertScript(script);
    }
    
/*    @RequestMapping(value="/editscript")
    public String editScript(ModelMap model, HttpServletRequest request) {
    		
		if (request.getParameter("deleteAction") != null) {

			scriptService.deleteScript(model, request);
			return "scriptMain";
		} else {			
			scriptService.updateScript(model, request);
			return "scriptstatementpage";
		}
    }*/
     
	@DELETE
	@Path("/{scriptName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> delete(@PathParam("scriptName") String scriptName)
	{
		return scriptService.deleteScript(scriptName);
	}
	
	@GET
	@Path("/getScripts")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String>  getScripts() {
		return scriptService.retrieveScripts();
	}
/*    @RequestMapping(value="/scriptMain")
    public String renderMainScripts(ModelMap model, HttpServletRequest request) {
        
    	codeGenUtils.renderScriptValues(model);
        return "scriptMain";
    }*/
    
}
