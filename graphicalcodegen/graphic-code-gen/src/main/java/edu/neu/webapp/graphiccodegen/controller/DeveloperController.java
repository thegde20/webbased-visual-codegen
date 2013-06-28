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

import edu.neu.webapp.graphiccodegen.entities.Developer;
import edu.neu.webapp.graphiccodegen.services.DeveloperService;

@Controller
@Path("/developer")
public class DeveloperController {

	@Autowired
	private DeveloperService devService;

	//@RequestMapping(value = "/addDeveloper")

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> addDeveloper(Developer developer) {
		List<Developer> allDevelopers=
			devService.addDeveloperService(developer);
		//return "developer";
		return allDevelopers;
	}

	//@RequestMapping(value = "/developer")
	@GET
	@Path("/getDevelopers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> getAllData() {
		List<Developer> allDevelopers =devService.getAllDataService();
		return allDevelopers;
	}

	@DELETE
	@Path("/{developerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> delete(@PathParam("developerId") String id)
	{
		List<Developer> allDevelopers = devService.deleteDeveloperService(id);
		return allDevelopers;
	}
	//@RequestMapping(value = "/editDeveloper")
	@GET
	@Path("/{developerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Developer read(@PathParam("developerId") String developerId)
	{		

			Developer d = devService.detailsDeveloperService(developerId);
			return d;

	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> update(Developer updatedDev)
	{
		List<Developer> allDevelopers = devService.updateDeveloperService(updatedDev);
		return allDevelopers;
	}
}
