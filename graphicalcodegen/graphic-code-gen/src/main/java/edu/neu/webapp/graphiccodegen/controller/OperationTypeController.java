package edu.neu.webapp.graphiccodegen.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.entities.Operation;
import edu.neu.webapp.graphiccodegen.entities.OperationType;

@Controller
public class OperationTypeController {

	@Autowired
    private OperationTypeDao operationTypeDao;

	@RequestMapping(value="/operationTypeWithValues")
    public String installCompany(ModelMap model, HttpServletRequest request) {
    	
        String oType = request.getParameter("oType");
        
        operationTypeDao.persist(new OperationType(oType, new ArrayList<Operation>()));
        
        List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
        model.put("operationTypes", operationTypes);
        
        return "operationtypemain";
    }
	
	@RequestMapping(value="/operationtypemain")
    public String renderMainStatementTypes(ModelMap model, HttpServletRequest request) {
        
		List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
        model.put("operationTypes", operationTypes);
        
        return "operationtypemain";
    }
}
