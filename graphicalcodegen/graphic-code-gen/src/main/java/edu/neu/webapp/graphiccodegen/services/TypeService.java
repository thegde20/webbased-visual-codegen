package edu.neu.webapp.graphiccodegen.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.entities.OperationType;
import edu.neu.webapp.graphiccodegen.entities.StatementType;

public class TypeService {
	

	@Autowired
    private OperationTypeDao operationTypeDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
/*	public void insertOperationType(ModelMap model, HttpServletRequest request){
		
		String oType = request.getParameter("oType");
        operationTypeDao.persist(new OperationType(oType, new ArrayList<Operation>()));
        codeGenUtils.renderOperationTypeValues(model);
        
	}
	*/
	public List<String> insertOperationType(OperationType operationType){
		
		operationTypeDao.persist(operationType);
        return retrieveOperationTypes();
	}
		/*public void insertStatementType(ModelMap model, HttpServletRequest request){
		
        String sType = request.getParameter("sType");
        statementTypeDao.persist(new StatementType(sType, new ArrayList<Statement>()));
        codeGenUtils.renderStatementTypeValues(model);
        
	}*/
	
	public List<String> insertStatementType(StatementType statementType){
		
        statementTypeDao.persist(statementType);
       // codeGenUtils.renderStatementTypeValues(model);-- use the one in CodeGenUtils after modifying the function if repetition of below code happens
        return retrieveStatementTypes();
	}
	
	public List<String> retrieveStatementTypes(){
		List<StatementType> statementTypes = statementTypeDao.getAllStatementTypes();
		List<String> stmtTypeStr = new ArrayList<String>();
		for(StatementType statementType : statementTypes){
			stmtTypeStr.add(statementType.getsType());
		}
		return stmtTypeStr;
	}
	
	public List<String> retrieveOperationTypes(){
		List<String> operationTypeStr = new ArrayList<String>();
		List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
		for(OperationType operationType : operationTypes){
			operationTypeStr.add(operationType.getoType());
		}
		return operationTypeStr;
	}

}
