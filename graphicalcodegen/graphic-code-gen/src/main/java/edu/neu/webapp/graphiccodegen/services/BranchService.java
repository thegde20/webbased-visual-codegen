package edu.neu.webapp.graphiccodegen.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.BranchDao;
import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.entities.Branch;

public class BranchService {
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
	@Autowired
	private BranchDao branchDao;
	
	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
	private StatementDao statementDao;
	
	@Autowired
    private DataDao dataDao;
	
	public void insertBranch(ModelMap model, HttpServletRequest request){
		
		String scriptName = String.valueOf(model.get("sessionScriptName"));
		String scriptStmtType = String.valueOf(model.get("sessionStatementType"));
		int branchingVarId = Integer.parseInt(request.getParameter("branchingVar"));
		int trueStatementId = Integer.parseInt(request.getParameter("trueStatement"));
		int falseStatementId = Integer.parseInt(request.getParameter("falseStatement"));

	    branchDao.persist(new Branch(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName),dataDao.getData(branchingVarId),
	    		statementDao.getStatement(trueStatementId), statementDao.getStatement(falseStatementId)));
	        
	    codeGenUtils.renderPageValues(model);
	}
	
	public void deleteBranch(ModelMap model, HttpServletRequest request){
	
		int oldBranchStatement = Integer.valueOf(request.getParameter("deleteAction"));
		branchDao.deleteBranchStatementById(oldBranchStatement);
	    codeGenUtils.renderPageValues(model);
	}
	
	public void updateBranch(ModelMap model, HttpServletRequest request){
		
		codeGenUtils.renderPageValues(model);
	}

}
