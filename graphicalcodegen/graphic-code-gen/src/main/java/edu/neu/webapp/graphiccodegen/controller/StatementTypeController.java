package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.neu.webapp.graphiccodegen.entities.StatementType;
import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.TypeService;

@Controller
@Path("/statementtype")
public class StatementTypeController {
	
	@Autowired
    private TypeService typeService;
	
	@Autowired
	private CodeGenUtils codeGenUtils;

	/*@RequestMapping(value="/statementTypeWithValues")
    public String installStatementType(ModelMap model, HttpServletRequest request) {
    	
		typeService.insertStatementType(model, request);
        return "statementtypemain";
    }
	*/
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public List<String> addStatementType(StatementType statementType) {
		return typeService.insertStatementType(statementType);
    }
	
	@GET
	@Path("/getStatementType")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String>  getStatementType() {
		return typeService.retrieveStatementTypes();
	}

	/*@RequestMapping(value="/statementtypemain")
    public String renderMainStatementTypes(ModelMap model, HttpServletRequest request) {
        
		codeGenUtils.renderStatementTypeValues(model);
        return "statementtypemain";
    }*/
}
