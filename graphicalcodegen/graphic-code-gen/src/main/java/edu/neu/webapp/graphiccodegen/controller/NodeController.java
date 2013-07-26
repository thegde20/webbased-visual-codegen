package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.services.NodeService;

@Controller
public class NodeController {

	@Autowired
	private NodeService nodeService;

	@RequestMapping(value = "/addNode")
	public String addNode(ModelMap model, HttpServletRequest request) {
		nodeService.addNodeService(model, request);
		return "nodeList";

	}

	@RequestMapping(value = "/nodeList")
	public String getAllData(ModelMap model, HttpServletRequest request) {
		nodeService.getAllDataService(model, request);
		return "nodeList";
	}

	@RequestMapping(value = "/editNode")
	public String editNode(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			nodeService.deleteNodeService(model, request);
			return "nodeList";
		} else {
			nodeService.detailsNodeService(model, request);
			return "nodeDetails";
		}

	}
}
