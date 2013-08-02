package edu.neu.webapp.graphiccodegen.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;

public class NodeService {

	@Autowired
	private FlowDao flowDao;
	@Autowired
	private NodeDao nodeDao;

	public List<Node> addNodeService(String name,String type,int flowId) {

		Flow flowReq = flowDao.getFlow(flowId);
		nodeDao.persist(new Node(name, flowReq, type));
		// app.getFlows().add(f);
		List<Node> ndList = nodeDao.getAllNodes();
		getSourceNodesForFlow(ndList);
		getTargetNodesForFlow(ndList);
		return ndList;

	}

	public List<Node> getAllDataService() {

		List<Node> ndList = nodeDao.getAllNodes();
		getSourceNodesForFlow(ndList);
		getTargetNodesForFlow(ndList);
		return ndList;

	}

	public List<Node> deleteNodeService(String id) {

		nodeDao.deleteById(id);
		List<Node> ndList = nodeDao.getAllNodes();
		getSourceNodesForFlow(ndList);
		getTargetNodesForFlow(ndList);
		return ndList;

	}

	public Node detailsNodeService(int id) {
		Node nd = nodeDao.getNode(id);
		// model.put("allNodes", value)
		nd.setSourceEvent(nodeDao.getSourceEventsForNode(nd.getId()));
		nd.setTargetEvent(nodeDao.getTargetEventsForNode(nd.getId()));
		return nd;
	}

	public List<Node> updateNodeService(Node nd){
		nodeDao.updateNode(nd.getName(), nd.getType(),nd.getId());		
		List<Node> ndList = nodeDao.getAllNodes();
		getSourceNodesForFlow(ndList);
		getTargetNodesForFlow(ndList);
		return ndList;
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
	public List<Node> getNodesForFlow(int id) {
		List<Node> nodes = flowDao.getNodesForFlow(id);
		return nodes;
		}
}