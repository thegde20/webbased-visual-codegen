package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.ApplicationDao;
import edu.neu.webapp.graphiccodegen.dao.DeveloperDao;
import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Developer;
import edu.neu.webapp.graphiccodegen.entities.Flow;

@Controller
public class ApplicationController {
	@Autowired
	private ApplicationDao appDao;
	@Autowired
	private DeveloperDao devDao;
	@Autowired
	private FlowDao flowDao;

	@RequestMapping(value = "/addApplication")
	public String addApplication(ModelMap model, HttpServletRequest request) {
		String name = request.getParameter("name");
		String devEmail = request.getParameter("developer");
		Developer devRequested = devDao.getDeveloper(devEmail);
		
		Application newapp = new Application(name, devRequested);
		appDao.persist(newapp);
		System.out.println("1.email---"+devRequested.getEmail());
		devRequested.addApplication(newapp);
		List<Application> allApps = appDao.getAllApplications();
		model.put("apps", allApps);

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		model.put("developers", allDevelopers);
		
		return "applicationList";

	}

	@RequestMapping(value = "/applicationList")
	public String getAllData(ModelMap model, HttpServletRequest request) {

		List<Application> allApps = appDao.getAllApplications();
		model.put("apps", allApps);

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		model.put("developers", allDevelopers);

		return "applicationList";
	}

	@RequestMapping(value = "/editApp")
	public String editDeveloper(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			String appId = request.getParameter("deleteId");
			appDao.deleteById(appId);
			List<Application> allApps = appDao.getAllApplications();
			model.put("apps", allApps);
			List<Developer> allDevelopers = devDao.getAllDevelopers();
			model.put("developers", allDevelopers);

			return "applicationList";
		} else {
			String appId = request.getParameter("detailId");
			Application appRequested = appDao.getApplication(appId);
			
			List<Flow> allFlows = flowDao.getAllFlows();
			model.put("flows", allFlows);

			model.put("application", appRequested);
			return "applicationDetails";
		}

	}


}
