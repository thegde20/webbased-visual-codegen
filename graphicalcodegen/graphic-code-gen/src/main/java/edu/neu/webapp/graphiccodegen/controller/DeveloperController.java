package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.services.DeveloperService;

@Controller
public class DeveloperController {

	@Autowired
	private DeveloperService devService;

	@RequestMapping(value = "/addDeveloper")
	public String addDeveloper(ModelMap model, HttpServletRequest request) {
		devService.addDeveloperService(model, request);
		return "developer";

	}

	@RequestMapping(value = "/developer")
	public String getAllData(ModelMap model, HttpServletRequest request) {
		devService.getAllDataService(model, request);
		return "developer";
	}

	@RequestMapping(value = "/editDeveloper")
	public String editDeveloper(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			devService.deleteDeveloperService(model, request);
			return "developer";
		} else {
			devService.detailsDeveloperService(model, request);
			return "developerDetails";
		}

	}
}
