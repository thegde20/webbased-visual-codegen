package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class MenuController {
  
    @RequestMapping(value="/mainmenu")
    public String mainmenu(HttpServletRequest request) {
            	
    	return "mainmenu";
    }
}