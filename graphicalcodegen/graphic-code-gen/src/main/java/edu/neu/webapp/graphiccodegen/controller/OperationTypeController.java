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

import edu.neu.webapp.graphiccodegen.entities.OperationType;
import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.TypeService;

@Controller
@Path("/operationtype")
public class OperationTypeController {

	@Autowired
    private TypeService typeService;
	
	@Autowired
	private CodeGenUtils codeGenUtils;

/*	@RequestMapping(value="/operationTypeWithValues")
    public String installOperationType(ModelMap model, HttpServletRequest request) {
    	
		typeService.insertOperationType(model, request);
        return "operationtypemain";
    }
	*/
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public List<String> addOperationType(OperationType operationType) {
		return typeService.insertOperationType(operationType);
    }
	
	@GET
	@Path("/getOperationType")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getOperationType() {
		return typeService.retrieveOperationTypes();
	}
	
/*	@RequestMapping(value="/operationtypemain")
    public String renderMainOperationTypes(ModelMap model, HttpServletRequest request) {
       
		codeGenUtils.renderOperationTypeValues(model);
        return "operationtypemain";
    }*/
}
