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

	public List<Developer> addDeveloperService(Developer d) {
		/*
		 * String email = request.getParameter("email"); String name =
		 * request.getParameter("firstName"); String lastName =
		 * request.getParameter("lastName");
		 */
		devDao.persist(d);

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		getAppsForDeveloper(allDevelopers);
		// model.put("developers", allDevelopers);
		return allDevelopers;

	}

	public void getAppsForDeveloper(List<Developer> allDevelopers) {
		if (allDevelopers != null) {
			for (Developer dev : allDevelopers) {
				List<Application> allApplications = devDao
						.getAllApplicationsForDeveloper(dev.getEmail());
				dev.setApplications(allApplications);
			}
		}

	}

	public List<Developer> getAllDataService() {
		List<Developer> allDevelopers = devDao.getAllDevelopers();
		getAppsForDeveloper(allDevelopers);
		// model.put("developers", allDevelopers);
		return allDevelopers;
	}

	public List<Developer> deleteDeveloperService(String id) {

		// String devEmail = request.getParameter("deleteId");
		devDao.deleteByEmail(id);

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		getAppsForDeveloper(allDevelopers);
		return allDevelopers;
		// model.put("developers", allDevelopers);
	}

	public Developer detailsDeveloperService(String email) {
		Developer devRequested = devDao.getDeveloper(email);
		// System.out.println("-----"+devRequested.getApplications().size());

		devRequested.setApplications(devDao
				.getAllApplicationsForDeveloper(devRequested.getEmail()));
		return devRequested;
	}

	public List<Developer> updateDeveloperService(Developer d) {
		devDao.updateDeveloper(d.getFirstName(), d.getLastName(), d.getEmail());

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		getAppsForDeveloper(allDevelopers);
		return allDevelopers;
	}

}
