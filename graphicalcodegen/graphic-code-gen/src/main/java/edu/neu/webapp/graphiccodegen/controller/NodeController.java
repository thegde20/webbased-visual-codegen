package edu.neu.webapp.graphiccodegen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.model.Flow;
import edu.neu.webapp.graphiccodegen.model.Node;
import edu.neu.webapp.graphiccodegen.model.NodePK;

@Controller
public class NodeController {
	
	@Autowired
	private FlowDao flowDao;
	@Autowired
	private NodeDao nodeDao;

	@RequestMapping(value = "/addNode")
	public String addNode(ModelMap model, HttpServletRequest request) {
		String name = request.getParameter("name");
		String  flowId = request.getParameter("flow");
		String  type = request.getParameter("type");

		Flow flowReq = flowDao.getFlow(flowId);
		nodeDao.persist(new Node(name,flowReq,type));
//		app.getFlows().add(f);

		model.put("allNodes", nodeDao.getAllNodes());
		model.put("allFLows", flowDao.getAllFlows());

		return "nodeList";

	}

	@RequestMapping(value = "/nodeList")
	public String getAllData(ModelMap model, HttpServletRequest request) {

		model.put("allNodes", nodeDao.getAllNodes());
		model.put("allFLows", flowDao.getAllFlows());

		return "nodeList";
	}

	@RequestMapping(value = "/editNode")
	public String editNode(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			String id = request.getParameter("deleteId");
			nodeDao.deleteById(id);
			model.put("allNodes", nodeDao.getAllNodes());
			model.put("allFLows", flowDao.getAllFlows());

			return "nodeList";
		} else {
			String id = request.getParameter("detailId");
			//String flowId = request.getParameter("flowId");
			
			Node nd = nodeDao.getNode(id);
			//model.put("allNodes", value)
			model.put("node",nd);
			return "nodeDetails";
		}

	}
}
