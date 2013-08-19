package edu.neu.webapp.graphiccodegen.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.neu.webapp.graphiccodegen.dao.ScriptDao;
import edu.neu.webapp.graphiccodegen.entities.Script;

public class ScriptService {

	@Autowired
	private ScriptDao scriptDao;

	@Autowired
	private CodeGenUtils codeGenUtils;

	/*
	 * public void insertScript(ModelMap model, HttpServletRequest request) {
	 * 
	 * String scriptName = request.getParameter("scriptName");
	 * scriptDao.persist(new Script(scriptName, new ArrayList<Statement>()));
	 * codeGenUtils.renderScriptValues(model); }
	 */
	public List<String> insertScript(Script script) {

		scriptDao.persist(script);
		return retrieveScripts();
	}
	
	public List<String> retrieveScripts(){
	
		List<String> scriptStr = new ArrayList<String>();
		List<Script> scripts = scriptDao.getAllScripts();
		for(Script script : scripts){
			scriptStr.add(script.getScriptName());
		}
		return scriptStr;
	}
/*
	public void deleteScript(ModelMap model, HttpServletRequest request) {

		String oldScript = String.valueOf(request.getParameter("deleteAction"));
		scriptDao.deleteScriptById(oldScript);
		codeGenUtils.renderScriptValues(model);
	}*/

	public List<String> deleteScript(String scriptName){

		scriptDao.deleteScriptById(scriptName);
		return retrieveScripts();
	}
	
/*	public void updateScript(ModelMap model, HttpServletRequest request) {

		model.put("sessionScriptName", request.getParameter("detailAction"));
		codeGenUtils.renderPageValues(model);

	}*/

	/*//Replacement for above?
	public void updateScript(ModelMap model, String scriptName){
		model.put("sessionScriptName", scriptName);
	}*/

}
