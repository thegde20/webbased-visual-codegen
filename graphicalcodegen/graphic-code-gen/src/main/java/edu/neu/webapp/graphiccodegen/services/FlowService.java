package edu.neu.webapp.graphiccodegen.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

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

	
	public void addFlowService(ModelMap model, HttpServletRequest request) {
		String name = request.getParameter("name");
		String  desc= request.getParameter("desc");
		String appId = request.getParameter("application");

		Application app = appDao.getApplication(Integer.parseInt(appId));
		Flow f = new Flow(name, desc,app,new ArrayList<Node>());
		flowDao.persist(f);
//		app.getFlows().add(f);
		List<Flow> list =  flowDao.getAllFlows();
		getNodesForFlow(list);
		model.put("allFlows", list);
		model.put("allApps", appDao.getAllApplications());


	}


	public void getAllDataService(ModelMap model, HttpServletRequest request) {

		List<Flow> list =  flowDao.getAllFlows();
		getNodesForFlow(list);
		model.put("allFlows", list);
		model.put("allApps", appDao.getAllApplications());

	}

	public void deleteFlowService(ModelMap model, HttpServletRequest request) {

			String id = request.getParameter("deleteId");
			flowDao.deleteById(id);
			List<Flow> list =  flowDao.getAllFlows();
			getNodesForFlow(list);
			model.put("allFlows", list);
			model.put("allApps", appDao.getAllApplications());


		} 
	
	
	public void detailsFlowService(ModelMap model, HttpServletRequest request) {
			String id = request.getParameter("detailId");
			Flow flowRequested = flowDao.getFlow(id);
			//model.put("allNodes", value)
			flowRequested.setNodes(flowDao.getNodesForFlow(flowRequested.getId()));
			model.put("flow",flowRequested);
	
		}
	
	public void getNodesForFlow(List<Flow> allFlows){
		if(allFlows != null){
			for(Flow f:allFlows){
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
