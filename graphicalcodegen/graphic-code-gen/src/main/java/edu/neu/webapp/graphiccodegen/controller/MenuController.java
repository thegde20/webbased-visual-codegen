package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.entities.Data;
 
@Controller
@SessionAttributes("sessionVariableObjects")
public class MenuController {
  
	@Autowired
	private DataDao dataDao;
	
    @RequestMapping(value="/mainmenu")
    public String mainmenu(ModelMap model, HttpServletRequest request) {
    	
    	List<Data> dataStatements = dataDao.getAllDataStatements("script1");
		model.put("sessionVariableObjects", dataStatements);
		
    	return "mainmenu";
    }
    
    
}