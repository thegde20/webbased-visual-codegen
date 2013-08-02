package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class MenuController {
	
    @RequestMapping(value="/mainmenu")
    public String mainmenu(ModelMap model, HttpServletRequest request) {
		
    	return "mainmenu";
    }
    @RequestMapping(value="/userApplication")
    public String userApplication(ModelMap model, HttpServletRequest request) {
		
    	return "userApplication";
    }
    @RequestMapping(value="/userFlow")
    public String userFlow(ModelMap model, HttpServletRequest request) {
		
    	return "userFlow";
    }
    @RequestMapping(value="/flowList")
    public String flowList(ModelMap model, HttpServletRequest request) {
		
    	return "flowList";
    }
    @RequestMapping(value="/flowDetails")
    public String flowDetails(ModelMap model, HttpServletRequest request) {
		
    	return "flowDetails";
    }
    @RequestMapping(value="/eventList")
    public String eventList(ModelMap model, HttpServletRequest request) {
		
    	return "eventList";
    }
    @RequestMapping(value="/nodeList")
    public String nodeList(ModelMap model, HttpServletRequest request) {
		
    	return "nodeList";
    }
    @RequestMapping(value="/nodeDetails")
    public String nodeDetails(ModelMap model, HttpServletRequest request) {
		
    	return "nodeDetails";
    }
    @RequestMapping(value="/modules")
    public String modules(ModelMap model, HttpServletRequest request) {
		
    	return "modules";
    }
    
}