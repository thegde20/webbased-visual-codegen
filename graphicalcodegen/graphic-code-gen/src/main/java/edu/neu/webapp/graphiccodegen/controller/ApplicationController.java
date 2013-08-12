package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.services.ApplicationService;

@Controller
@Path("/application")
public class ApplicationController {

	@Autowired
	private ApplicationService appService;

	//@RequestMapping(value = "/addApplication")
	@POST
	@Path("/addApp/{name}/{developerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Application> addApplication(@PathParam("developerId") String developerId,
			@PathParam("name") String name) {
		
		List<Application> allApps =
				appService.addApplicationService(name,developerId);
		return allApps;

	}

	//@RequestMapping(value = "/applicationList")
	@GET
	@Path("/getApplications")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Application> getAllData() {
		List<Application> allApps = appService.getAllDataService();

		return allApps;
	}
	@GET
	@Path("/getApplicationForDev/{devId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Application> getApplicationForDeveloper(@PathParam("devId") String email) {
		List<Application> allApps = appService.getApplicationsForDeveloper(email);

		return allApps;
	}
	@DELETE
	@Path("/{appId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Application> delete(@PathParam("appId") String id)
	{
		List<Application> allApps = appService.deleteApplicationService(id);
		return allApps;
	}
	//@RequestMapping(value = "/editDeveloper")
	@GET
	@Path("/{appId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Application read(@PathParam("appId") int id)
	{		

			Application a = appService.detailsApplicationService(id);
			return a;

	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Application> update(Application updatedApp)
	{
		List<Application> allApps = appService.updateApplicationService(updatedApp);
		return allApps;
	}

}
