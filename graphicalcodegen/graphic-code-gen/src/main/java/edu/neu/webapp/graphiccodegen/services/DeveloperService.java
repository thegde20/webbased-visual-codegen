package edu.neu.webapp.graphiccodegen.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.ApplicationDao;
import edu.neu.webapp.graphiccodegen.dao.DeveloperDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Developer;

public class DeveloperService {


	@Autowired
	private DeveloperDao devDao;
	@Autowired
	private ApplicationDao appDao;

	public void addDeveloperService(ModelMap model, HttpServletRequest request) {
		String email = request.getParameter("email");
		String name = request.getParameter("name");

		devDao.persist(new Developer(email, name));

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		getAppsForDeveloper(allDevelopers);
		model.put("developers", allDevelopers);


	}
	public void getAppsForDeveloper(List<Developer> allDevelopers){
		if (allDevelopers != null) {
			for (Developer dev : allDevelopers) {
				List<Application> allApplications = devDao
						.getAllApplicationsForDeveloper(dev.getEmail());
				dev.setApplications(allApplications);
			}
		}
	
	}
	

	public void getAllDataService(ModelMap model, HttpServletRequest request) {
		System.out.println("in getAlldata");
		List<Developer> allDevelopers = devDao.getAllDevelopers();
		getAppsForDeveloper(allDevelopers);
		model.put("developers", allDevelopers);

	}


	public void deleteDeveloperService(ModelMap model, HttpServletRequest request) {

			String devEmail = request.getParameter("deleteId");
			devDao.deleteByEmail(devEmail);
			
			List<Developer> allDevelopers = devDao.getAllDevelopers();
			getAppsForDeveloper(allDevelopers);
			model.put("developers", allDevelopers);
		} 
		
		
		
		public void detailsDeveloperService(ModelMap model,HttpServletRequest request)
		{
			String devEmail = request.getParameter("detailId");
			Developer devRequested = devDao.getDeveloper(devEmail);
			//System.out.println("-----"+devRequested.getApplications().size());
			
			List<Application> allApps = appDao.getAllApplications();
			devRequested.setApplications(devDao.getAllApplicationsForDeveloper(devRequested.getEmail()));
			model.put("developer", devRequested);
			model.put("applications",allApps);
		}

	}
