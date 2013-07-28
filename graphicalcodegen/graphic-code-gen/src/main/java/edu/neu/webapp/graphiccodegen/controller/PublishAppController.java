package edu.neu.webapp.graphiccodegen.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.neu.webapp.graphiccodegen.services.PublishAppService;

@Controller
@Path("/publish")
public class PublishAppController {

	@Autowired
	private PublishAppService publishAppService;

	@Autowired
	private ServletContext servletContext;

	// @RequestMapping(value = "/publishApp")
	@GET
	@Path("/{appId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void publishApp(@PathParam("appId") String appID) {
		try {
			publishAppService.createDirs(servletContext, appID);
		} catch (IOException ioException) {
			System.out.println("Exception IO in PublishAppService---");
			ioException.printStackTrace();
		}
	}

}
