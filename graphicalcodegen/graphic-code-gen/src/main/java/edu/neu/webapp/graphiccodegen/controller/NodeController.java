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

import edu.neu.webapp.graphiccodegen.entities.Node;
import edu.neu.webapp.graphiccodegen.services.NodeService;

@Controller
@Path("/node")
public class NodeController {

	@Autowired
	private NodeService nodeService;

	//@RequestMapping(value = "/addNode")
	@POST
	@Path("/addNode/{name}/{type}/{flowId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Node> addNode(@PathParam("flowId") int flowId,
			@PathParam("name") String name, @PathParam("type") String type) {
		List<Node> allNodes = nodeService.addNodeService(name, type, flowId);
		return allNodes;

	}

	@GET
	@Path("/getNodes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Node> getAllData() {
		List<Node> allNodes= nodeService.getAllDataService();
		return allNodes;
	}

	@DELETE
	@Path("/{nodeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Node> delete(@PathParam("nodeId") String id) {
		List<Node> allNodes= nodeService.deleteNodeService(id);
		return allNodes;
	}

	// @RequestMapping(value = "/editDeveloper")
	@GET
	@Path("/{nodeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Node read(@PathParam("nodeId") int id) {

		Node nd = nodeService.detailsNodeService(id);
		return nd;

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Node> update(Node updatedNode) {
		List<Node> allNodes = nodeService.updateNodeService(updatedNode);
		return allNodes;
	}
}
