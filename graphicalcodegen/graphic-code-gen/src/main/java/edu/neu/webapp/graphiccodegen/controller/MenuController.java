package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class MenuController {
	
    @RequestMapping(value="/mainmenu")
    public String mainmenu(ModelMap model, HttpServletRequest request) {
		
    	return "DemoFile";
    }
    @RequestMapping(value="/userApplication")
    public String userApplication(ModelMap model, HttpServletRequest request) {
		
    	return "userApplication";
    }
    @RequestMapping(value="/userFlow")
    public String userFlow(ModelMap model, HttpServletRequest request) {
		
    	return "userFlow";
    }
    
    
}