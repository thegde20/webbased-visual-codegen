package edu.neu.webapp.graphiccodegen.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.ApplicationDao;
import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;

@Controller
public class FlowController {

	@Autowired
	private FlowDao flowDao;
	@Autowired
	private ApplicationDao appDao;
	@Autowired
	private NodeDao nodeDao;

	@RequestMapping(value = "/addFlow")
	public String addFlow(ModelMap model, HttpServletRequest request) {
		String name = request.getParameter("name");
		String  desc= request.getParameter("desc");
		String appId = request.getParameter("application");

		Application app = appDao.getApplication(appId);
		Flow f = new Flow(name, desc,app,new ArrayList<Node>());
		flowDao.persist(f);
//		app.getFlows().add(f);

		model.put("allFlows", flowDao.getAllFlows());
		model.put("allApps", appDao.getAllApplications());

		return "flowList";

	}

	@RequestMapping(value = "/flowList")
	public String getAllData(ModelMap model, HttpServletRequest request) {

		model.put("allFlows", flowDao.getAllFlows());
		model.put("allApps", appDao.getAllApplications());

		return "flowList";
	}

	@RequestMapping(value = "/editFlow")
	public String editFlow(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			String id = request.getParameter("deleteId");
			flowDao.deleteById(id);
			model.put("allFlows", flowDao.getAllFlows());
			model.put("allApps", appDao.getAllApplications());

			return "developer";
		} else {
			String id = request.getParameter("detailId");
			Flow flowRequested = flowDao.getFlow(id);
			//model.put("allNodes", value)
			model.put("flow",flowRequested);
			return "flowDetails";
		}

	}
}
