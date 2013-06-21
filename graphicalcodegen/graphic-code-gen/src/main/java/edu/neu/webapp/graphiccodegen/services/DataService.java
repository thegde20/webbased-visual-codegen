package edu.neu.webapp.graphiccodegen.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.dao.StatementTypeDao;
import edu.neu.webapp.graphiccodegen.entities.Data;

public class DataService {
	
	@Autowired
    private DataDao dataDao;
	
	@Autowired
    private StatementTypeDao statementTypeDao;
	
	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
	private CodeGenUtils codeGenUtils;

	public void insertData(ModelMap model, HttpServletRequest request){
		
		String scriptName = String.valueOf(model.get("sessionScriptName"));
        String scriptStmtType = String.valueOf(model.get("sessionStatementType"));
        String dataName = request.getParameter("varName");
        String dataType = request.getParameter("varType");
        String dataValue = request.getParameter("varValue");

        dataDao.persist(new Data(statementTypeDao.getStatementType(scriptStmtType), scriptDao.getScript(scriptName), dataName, dataValue, dataType));
        
        codeGenUtils.renderPageValues(model);
	}
	
	public void deleteData(ModelMap model, HttpServletRequest request){
		
		int oldDataStatement = Integer.valueOf(request.getParameter("deleteAction"));
		dataDao.deleteDataStatementById(oldDataStatement);
		
		codeGenUtils.renderPageValues(model);
	}
	
	public void updateData(ModelMap model, HttpServletRequest request){
		
		int dataStatementId = Integer.valueOf(request.getParameter("updateAction"));
		String newDataName = request.getParameter("updatedVar");
		String newDataType = request.getParameter("updatedType");
		String newInitDataValue = request.getParameter("updatedVarValue");

		dataDao.dataAfterUpdate(dataStatementId, newDataName, newDataType, newInitDataValue);
		
		codeGenUtils.renderPageValues(model);
		
	}
	
}
