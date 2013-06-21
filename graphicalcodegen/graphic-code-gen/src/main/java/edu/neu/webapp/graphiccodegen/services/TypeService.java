package edu.neu.webapp.graphiccodegen.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.entities.Operation;
import edu.neu.webapp.graphiccodegen.entities.OperationType;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StatementType;

public class TypeService {
	

	@Autowired
    private OperationTypeDao operationTypeDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
	public void insertOperationType(ModelMap model, HttpServletRequest request){
		
		String oType = request.getParameter("oType");
        operationTypeDao.persist(new OperationType(oType, new ArrayList<Operation>()));
        codeGenUtils.renderOperationTypeValues(model);
        
	}
	
	public void insertStatementType(ModelMap model, HttpServletRequest request){
		
        String sType = request.getParameter("sType");
        statementTypeDao.persist(new StatementType(sType, new ArrayList<Statement>()));
        codeGenUtils.renderStatementTypeValues(model);
        
	}

}
