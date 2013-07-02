package edu.neu.webapp.graphiccodegen.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.BranchDao;
import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.NumberOperationDao;
import edu.neu.webapp.graphiccodegen.dao.OperationDao;
import edu.neu.webapp.graphiccodegen.dao.OperationTypeDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.dao.StringOperationDao;
import edu.neu.webapp.graphiccodegen.entities.Branch;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.NumberOperation;
import edu.neu.webapp.graphiccodegen.entities.Operation;
import edu.neu.webapp.graphiccodegen.entities.OperationType;
import edu.neu.webapp.graphiccodegen.entities.Script;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StatementType;
import edu.neu.webapp.graphiccodegen.entities.StringOperation;

public class CodeGenUtils {

	@Autowired
    private DataDao dataDao;
	
	@Autowired
    private NumberOperationDao numberOperationDao;
	
	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
	private StringOperationDao stringOperationDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
    private OperationTypeDao operationTypeDao;
	
	@Autowired
	private StatementDao statementDao;
	
	@Autowired
	private BranchDao branchDao;
	
	@Autowired
	private OperationDao operationDao;
	
	public void renderPageValues(ModelMap model){

		String scriptName = String.valueOf(model.get("sessionScriptName"));
		
		List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
		model.put("statementTypes", stmtTypes);		

		List<Data> dataStatements = dataDao.getAllDataStatements(scriptName);
		model.put("sessionVariableObjects", dataStatements);

		List<Branch> branchStatements = branchDao.getAllBranchStatements(scriptName);
		model.put("branchStatements", branchStatements);

		List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
		model.put("operationTypes", operationTypes);

		List<NumberOperation> numberOperations = numberOperationDao.getAllNumberOperationStatements(scriptName);
		model.put("numberOperations", numberOperations);

		List<StringOperation> stringOperations = stringOperationDao.getAllStringOperationStatements(scriptName);
		model.put("stringOperations", stringOperations);

		List<Statement> statements = statementDao.getAllStatements(scriptName);
		model.put("statements", statements);
		
		List<Operation> operations = operationDao.getAllOperationStatements(scriptName);
		model.put("operations", operations);
		

	}
	
	public void renderStatementTypeValues(ModelMap model){
		
		List<StatementType> stmtTypes = statementTypeDao.getAllStatementTypes();
		model.put("statementTypes", stmtTypes);
	}

	public void renderOperationTypeValues(ModelMap model){
		
		List<OperationType> operationTypes = operationTypeDao.getAllOperationTypes();
        model.put("operationTypes", operationTypes);
	}
	
	public void renderScriptValues(ModelMap model){
		
		List<Script> scripts = scriptDao.getAllScripts();
        model.put("scripts", scripts);
	}
}
