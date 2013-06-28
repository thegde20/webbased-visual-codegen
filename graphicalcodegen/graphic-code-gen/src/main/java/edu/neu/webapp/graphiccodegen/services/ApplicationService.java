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

	public List<Application> addApplicationService(String name,String email) {
		Developer devRequested = devDao.getDeveloper(email);
		Application app = new Application(name,devRequested);
		appDao.persist(app);
		List<Application> allApps = appDao.getAllApplications();
		 return allApps;

	}
	

	public List<Application>  getAllDataService() {

		List<Application> allApps = appDao.getAllApplications();
		getFlowsForApps(allApps);
		return allApps;
		
	}
	
	public void getFlowsForApps(List<Application> allApps){
		if (allApps != null) {
			for (Application app : allApps) {
				List<Flow> flows = appDao.getFlowsForApplication(app.getId());
				app.setFlows(flows);
			}
		}
	
	}
	public List<Application> deleteApplicationService(String appId) {
		appDao.deleteById(appId);
		List<Application> allApps = appDao.getAllApplications();
		getFlowsForApps(allApps);
		return allApps;

	}
	
	public Application detailsApplicationService(int appId) {
		Application appRequested = appDao.getApplication(appId);
		
		
		appRequested.setFlows(appDao.getFlowsForApplication(appRequested.getId()));
		//model.put("application", appRequested);
		return appRequested;

	}
	public List<Application> updateApplicationService(Application a){
		appDao.updateApplication(a.getName(),a.getId());
		
		List<Application> allApps = appDao.getAllApplications();
		getFlowsForApps(allApps);
		return allApps;
	}
}
