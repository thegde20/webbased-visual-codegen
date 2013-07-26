package edu.neu.webapp.graphiccodegen.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;

public class NodeService {

	@Autowired
	private FlowDao flowDao;
	@Autowired
	private NodeDao nodeDao;

	public void addNodeService(ModelMap model, HttpServletRequest request) {
		String name = request.getParameter("name");
		String flowId = request.getParameter("flow");
		String type = request.getParameter("type");

		Flow flowReq = flowDao.getFlow(flowId);
		nodeDao.persist(new Node(name, flowReq, type));
		// app.getFlows().add(f);
		List<Node> ndList = nodeDao.getAllNodes();
		getSourceNodesForFlow(ndList);
		getTargetNodesForFlow(ndList);
		model.put("allNodes", ndList);
		model.put("allFLows", flowDao.getAllFlows());

	}

	public void getAllDataService(ModelMap model, HttpServletRequest request) {

		List<Node> ndList = nodeDao.getAllNodes();
		getSourceNodesForFlow(ndList);
		getTargetNodesForFlow(ndList);
		model.put("allNodes", ndList);
		model.put("allFLows", flowDao.getAllFlows());

	}

	public void deleteNodeService(ModelMap model, HttpServletRequest request) {

		String id = request.getParameter("deleteId");
		nodeDao.deleteById(id);
		List<Node> ndList = nodeDao.getAllNodes();
		getSourceNodesForFlow(ndList);
		getTargetNodesForFlow(ndList);
		model.put("allNodes", ndList);
		model.put("allFLows", flowDao.getAllFlows());

	}

	public void detailsNodeService(ModelMap model, HttpServletRequest request) {
		String id = request.getParameter("detailId");

		Node nd = nodeDao.getNode(id);
		// model.put("allNodes", value)
		nd.setSourceEvent(nodeDao.getSourceEventsForNode(nd.getId()));
		nd.setTargetEvent(nodeDao.getTargetEventsForNode(nd.getId()));
		model.put("node", nd);

	}

	public void getSourceNodesForFlow(List<Node> allNodes) {
		if (allNodes != null) {
			for (Node nd : allNodes) {
				nd.setSourceEvent(nodeDao.getSourceEventsForNode(nd.getId()));
			}
		}

	}

	public void getTargetNodesForFlow(List<Node> allNodes) {
		if (allNodes != null) {
			for (Node nd : allNodes) {
				nd.setTargetEvent(nodeDao.getTargetEventsForNode(nd.getId()));
			}
		}

	}

}
