package edu.neu.webapp.graphiccodegen.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.neu.webapp.graphiccodegen.dao.ApplicationDao;
import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;

public class FlowService {

	@Autowired
	private FlowDao flowDao;
	@Autowired
	private ApplicationDao appDao;
	@Autowired
	private NodeDao nodeDao;

	public List<Flow> addFlowService(String name, String desc, 
			String appId,int parentFlowId) {
		Application app = appDao.getApplication(Integer.parseInt(appId));
		Flow f = new Flow(name, desc, app, new ArrayList<Node>());
		if(parentFlowId != -1){
			System.out.println("parent fkow ID"+parentFlowId);
			Flow parentFlow = flowDao.getFlow(parentFlowId);
			f.setParentFlow(parentFlow);
		}
		flowDao.persist(f);

		return getAllDataService();

	}

	public List<Flow> getAllDataService() {

		List<Flow> list = flowDao.getAllFlows();
		getNodesForFlow(list);
		getSubFlows(list);
		return list;
	}

	public List<Flow>  deleteFlowService(String flowId) {
		flowDao.deleteById(flowId);
		List<Flow> list = flowDao.getAllFlows();
		getNodesForFlow(list);
		getSubFlows(list);
		return list;

	}

	public Flow detailsFlowService(int flowId) {
		Flow flowRequested = flowDao.getFlow(flowId);
		// model.put("allNodes", value)
		if(flowRequested != null){
			flowRequested.setNodes(flowDao.getNodesForFlow(flowRequested.getId()));
			flowRequested.setChildrenFlows(flowDao.getSubFlows(flowRequested.getId()));
		}
		return flowRequested;
	}

	public List<Flow> updateFlowService(Flow flow){
		flowDao.updateFlow(flow.getName(), flow.getDescription(), flow.getId());

		List<Flow> list = flowDao.getAllFlows();
		getNodesForFlow(list);
		getSubFlows(list);
		return list;
	}
	public void getNodesForFlow(List<Flow> allFlows) {
		if (allFlows != null && allFlows.size() != 0) {
			for (Flow f : allFlows) {
				f.setNodes(flowDao.getNodesForFlow(f.getId()));
			}
		}

	}
	public void getSubFlows(List<Flow> allFlows){
		if(allFlows != null && allFlows.size() != 0){
			for(Flow f: allFlows){
				f.setChildrenFlows(flowDao.getSubFlows(f.getId()));
			}
		}
	}
}