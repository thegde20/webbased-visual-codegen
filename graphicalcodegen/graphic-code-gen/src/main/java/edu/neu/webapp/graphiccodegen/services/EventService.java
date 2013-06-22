package edu.neu.webapp.graphiccodegen.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import edu.neu.webapp.graphiccodegen.dao.EventDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.entities.Event;
import edu.neu.webapp.graphiccodegen.entities.Node;

public class EventService {

	@Autowired
	private EventDao eventDao;
	@Autowired
	private NodeDao nodeDao;

	public void addEventService(ModelMap model, HttpServletRequest request) {
		String label = request.getParameter("label");
		String nodeSource = request.getParameter("nodeSource");
		String nodeTarget = request.getParameter("nodeTarget");

		Node nodeSrc = nodeDao.getNode(nodeSource);
		Node nodeTgt = nodeDao.getNode(nodeTarget);
		eventDao.persist(new Event(label, nodeSrc, nodeTgt));

		model.put("allEvents", eventDao.getAllEvents());
		model.put("allNodes", nodeDao.getAllNodes());

	}

	public void getAllDataService(ModelMap model, HttpServletRequest request) {

		model.put("allNodes", nodeDao.getAllNodes());
		model.put("allEvents", eventDao.getAllEvents());

	}

	public void deleteEventService(ModelMap model, HttpServletRequest request) {

		String id = request.getParameter("deleteId");
		eventDao.deleteById(id);
		model.put("allEvents", eventDao.getAllEvents());

	}

	public void detailsEventService(ModelMap model, HttpServletRequest request) {
		String id = request.getParameter("detailId");
		Event evt = eventDao.getEvent(id);
		model.put("event", evt);
	}

}
