package edu.neu.webapp.graphiccodegen.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.neu.webapp.graphiccodegen.dao.EventDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.entities.Event;
import edu.neu.webapp.graphiccodegen.entities.Node;

public class EventService {

	@Autowired
	private EventDao eventDao;
	@Autowired
	private NodeDao nodeDao;

	public List<Event> addEventService(String label, int nodeSource,
			int nodeTarget) {

		Node nodeSrc = nodeDao.getNode(nodeSource);
		Node nodeTgt = nodeDao.getNode(nodeTarget);
		eventDao.persist(new Event(label, nodeSrc, nodeTgt));

		List<Event> events = eventDao.getAllEvents();
		return events;
	}

	public List<Event> getAllDataService() {

		List<Event> events = eventDao.getAllEvents();
		return events;

	}

	public List<Event> deleteEventService(String id) {

		eventDao.deleteById(id);
		List<Event> events = eventDao.getAllEvents();
		return events;

	}

	public Event detailsEventService(String id) {
		Event evt = eventDao.getEvent(id);
		return evt;
	}
	public List<Event> updateEventService(Event evt){
		eventDao.updateEvent(evt.getLabel(),evt.getNodeSource(),evt.getNodeTarget(),evt.getId());		
		List<Event> events = eventDao.getAllEvents();
		return events;
	}

}