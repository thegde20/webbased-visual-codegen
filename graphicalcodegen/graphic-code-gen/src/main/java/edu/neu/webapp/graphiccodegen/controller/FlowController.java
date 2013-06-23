package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.services.FlowService;

@Controller
public class FlowController {

	@Autowired
	private FlowService flowService;

	@RequestMapping(value = "/addFlow")
	public String addFlow(ModelMap model, HttpServletRequest request) {
		flowService.addFlowService(model, request);
		return "flowList";

	}

	@RequestMapping(value = "/flowList")
	public String getAllData(ModelMap model, HttpServletRequest request) {
		flowService.getAllDataService(model, request);
		return "flowList";
	}

	@RequestMapping(value = "/editFlow")
	public String editFlow(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			flowService.deleteFlowService(model, request);
			return "developer";
		} else {
			flowService.detailsFlowService(model, request);
			return "flowDetails";
		}

	}
}
