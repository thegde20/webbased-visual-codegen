package edu.neu.webapp.graphiccodegen.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.ApplicationDao;
import edu.neu.webapp.graphiccodegen.dao.DeveloperDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Developer;

@Controller
public class DeveloperController {

	@Autowired
	private DeveloperDao devDao;
	@Autowired
	private ApplicationDao appDao;

	@RequestMapping(value = "/addDeveloper")
	public String addDeveloper(ModelMap model, HttpServletRequest request) {
		String email = request.getParameter("email");
		String name = request.getParameter("name");
System.out.println("------in add developer");
		devDao.persist(new Developer(email, name));

		List<Developer> allDevelopers = devDao.getAllDevelopers();
		model.put("developers", allDevelopers);

		return "developer";

	}

	@RequestMapping(value = "/developer")
	public String getAllData(ModelMap model, HttpServletRequest request) {
		System.out.println("in getAlldata");
		List<Developer> allDevelopers = devDao.getAllDevelopers();
		System.out.println("---"+allDevelopers.size());
		model.put("developers", allDevelopers);

		return "developer";
	}

	@RequestMapping(value = "/editDeveloper")
	public String editDeveloper(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			String devEmail = request.getParameter("deleteId");
			devDao.deleteByEmail(devEmail);
			List<Developer> allDevelopers = devDao.getAllDevelopers();
			model.put("developers", allDevelopers);

			return "developer";
		} else {
			System.out.println("---"+request.getParameter("detailId"));
			String devEmail = request.getParameter("detailId");
			Developer devRequested = devDao.getDeveloper(devEmail);
			//System.out.println("-----"+devRequested.getApplications().size());
			
			List<Application> allApps = appDao.getAllApplications();
			model.put("developer", devRequested);
			model.put("applications",allApps);
			return "developerDetails";
		}

	}
}
