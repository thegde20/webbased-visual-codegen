package edu.neu.webapp.graphiccodegen.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.services.EventService;

@Controller
public class EventController {
	
	@Autowired
	private EventService eventService;

	@RequestMapping(value = "/addEvent")
	public String addEvent(ModelMap model, HttpServletRequest request) {
		eventService.addEventService(model, request);
		return "eventList";

	}

	@RequestMapping(value = "/eventList")
	public String getAllData(ModelMap model, HttpServletRequest request) {

		eventService.getAllDataService(model, request);
		return "eventList";
	}

	@RequestMapping(value = "/editEvent")
	public String editEvent(ModelMap model, HttpServletRequest request) {

		if (request.getParameter("deleteId") != null) {
			eventService.deleteEventService(model, request);
			return "eventList";
		} else {
			eventService.detailsEventService(model, request);
			return "eventsDetails";
		}

	}
}
