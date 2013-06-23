package edu.neu.webapp.graphiccodegen.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.ApplicationDao;
import edu.neu.webapp.graphiccodegen.dao.DeveloperDao;
import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Developer;
import edu.neu.webapp.graphiccodegen.entities.Flow;

public class ApplicationService {

	@Autowired
	private ApplicationDao appDao;
	@Autowired
	private DeveloperDao devDao;
	@Autowired
	private FlowDao flowDao;

	public void addApplicationService(ModelMap model, HttpServletRequest request) {
		String name = request.getParameter("name");
		String devEmail = request.getParameter("developer");
		Developer devRequested = devDao.getDeveloper(devEmail);

		Application newapp = new Application(name, devRequested);
		appDao.persist(newapp);
		devRequested.addApplication(newapp);
		List<Application> allApps = appDao.getAllApplications();
		model.put("apps", allApps);

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		model.put("developers", allDevelopers);

	}

	public void getAllDataService(ModelMap model, HttpServletRequest request) {

		List<Application> allApps = appDao.getAllApplications();
		getFlowsForApps(allApps);
		model.put("apps", allApps);

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		//getAppsForDeveloper(allDevelopers);
		model.put("developers", allDevelopers);
		
	}
	
	public void getFlowsForApps(List<Application> allApps){
		if (allApps != null) {
			for (Application app : allApps) {
				List<Flow> flows = appDao.getFlowsForApplication(app.getId());
				app.setFlows(flows);
			}
		}
	
	}
	public void deleteDeveloperService(ModelMap model, HttpServletRequest request) {
		String appId = request.getParameter("deleteId");
		appDao.deleteById(appId);
		List<Application> allApps = appDao.getAllApplications();
		getFlowsForApps(allApps);
		model.put("apps", allApps);
		List<Developer> allDevelopers = devDao.getAllDevelopers();
		
		model.put("developers", allDevelopers);

	}
	
	public void detailsDeveloperService(ModelMap model, HttpServletRequest request) {
		String appId = request.getParameter("detailId");
		Application appRequested = appDao.getApplication(appId);
		
		List<Flow> allFlows = flowDao.getAllFlows();
		model.put("flows", allFlows);
		appRequested.setFlows(appDao.getFlowsForApplication(appRequested.getId()));
		model.put("application", appRequested);

	}
}
