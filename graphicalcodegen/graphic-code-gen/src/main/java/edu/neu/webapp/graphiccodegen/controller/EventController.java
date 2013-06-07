package edu.neu.webapp.graphiccodegen.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.EventDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.model.Event;
import edu.neu.webapp.graphiccodegen.model.Node;

@Controller
public class EventController {
	
	@Autowired
	private EventDao eventDao;
	@Autowired
	private NodeDao nodeDao;

	@RequestMapping(value = "/addEvent")
	public String addEvent(ModelMap model, HttpServletRequest request) {
		String label = request.getParameter("label");
		String nodeSource = request.getParameter("nodeSource");
		String nodeTarget = request.getParameter("nodeTarget");

		Node nodeSrc = nodeDao.getNode(nodeSource);
		Node nodeTgt = nodeDao.getNode(nodeTarget);
		eventDao.persist(new Event(label,nodeSrc,nodeTgt));

		model.put("allEvents", eventDao.getAllEvents());
		model.put("allNodes", nodeDao.getAllNodes());

		return "eventList";

	}

	@RequestMapping(value = "/eventList")
	public String getAllData(ModelMap model, HttpServletRequest request) {

		model.put("allNodes", nodeDao.getAllNodes());
		model.put("allEvents", eventDao.getAllEvents());

		return "eventList";
	}

	@RequestMapping(value = "/editEvent")
	public String editEvent(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			String id = request.getParameter("deleteId");
			eventDao.deleteById(id);
			model.put("allEvents", eventDao.getAllEvents());
			return "eventList";
		} else {
			String id = request.getParameter("detailId");
			Event evt = eventDao.getEvent(id);
			model.put("event",evt);
			return "eventsDetails";
		}

	}
}
