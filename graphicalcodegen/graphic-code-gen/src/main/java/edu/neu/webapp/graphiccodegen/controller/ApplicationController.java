package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.services.ApplicationService;

@Controller
public class ApplicationController {

	@Autowired
	private ApplicationService appService;

	@RequestMapping(value = "/addApplication")
	public String addApplication(ModelMap model, HttpServletRequest request) {
		appService.addApplicationService(model, request);
		return "applicationList";

	}

	@RequestMapping(value = "/applicationList")
	public String getAllData(ModelMap model, HttpServletRequest request) {
		appService.getAllDataService(model, request);

		return "applicationList";
	}

	@RequestMapping(value = "/editApp")
	public String editDeveloper(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			appService.deleteDeveloperService(model, request);
			return "applicationList";
		} else {
			appService.detailsDeveloperService(model, request);
			return "applicationDetails";
		}

	}

}
