package edu.neu.webapp.graphiccodegen.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.services.FlowService;

@Controller
@Path("/flow")
public class FlowController {

	@Autowired
	private FlowService flowService;

	// @RequestMapping(value = "/addFlow")
	@POST
	@Path("/addFlow/{name}/{desc}/{appId}/{parentFlowId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flow> addFlow(@PathParam("appId") String appId,
			@PathParam("name") String name, @PathParam("desc") String desc,
			@PathParam("parentFlowId") int parentFlowId) {
		List<Flow> allFlows = flowService.addFlowService(name, desc, appId,parentFlowId);
		return allFlows;

	}

	@GET
	@Path("/getFlows")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flow> getAllData() {
		List<Flow> allFlows = flowService.getAllDataService();
		return allFlows;
	}

	@DELETE
	@Path("/{flowId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flow> delete(@PathParam("flowId") String id) {
		List<Flow> allFlows = flowService.deleteFlowService(id);
		return allFlows;
	}

	// @RequestMapping(value = "/editDeveloper")
	@GET
	@Path("/{flowId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Flow read(@PathParam("flowId") int id) {

		Flow flow = flowService.detailsFlowService(id);
		return flow;

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flow> update(Flow updatedFlow) {
		List<Flow> allFlows = flowService.updateFlowService(updatedFlow);
		return allFlows;
	}
	@GET
	@Path("/getFlowForApp/{appId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flow> readFlowForApp(@PathParam("appId") int appId) {

		List<Flow> flows = flowService.getAllFlowForApplication(appId);
		return flows;

	}

}
