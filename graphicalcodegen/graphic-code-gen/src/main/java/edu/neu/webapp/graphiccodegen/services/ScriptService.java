package edu.neu.webapp.graphiccodegen.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.entities.Script;
import edu.neu.webapp.graphiccodegen.entities.Statement;

public class ScriptService {
	
	@Autowired
    private ScriptDao scriptDao;
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
	public void insertScript(ModelMap model, HttpServletRequest request) {

		String scriptName = request.getParameter("scriptName");
        scriptDao.persist(new Script(scriptName, new ArrayList<Statement>()));
        codeGenUtils.renderScriptValues(model);
	}
	
	public void deleteScript(ModelMap model, HttpServletRequest request) {
		
		String oldScript = String.valueOf(request.getParameter("deleteAction"));
		scriptDao.deleteScriptById(oldScript);
		codeGenUtils.renderScriptValues(model);
	}
	
	public void updateScript(ModelMap model, HttpServletRequest request) {
		
		model.put("sessionScriptName", request.getParameter("detailAction"));
		codeGenUtils.renderPageValues(model);
	
		
	}

}
