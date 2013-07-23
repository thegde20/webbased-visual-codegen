package edu.neu.webapp.graphiccodegen.controller;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.neu.webapp.graphiccodegen.entities.Event;
import edu.neu.webapp.graphiccodegen.services.EventService;

@Controller
@Path("/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	@POST
	@Path("/addEvent/{label}/{ndSrcId}/{ndTgtId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> addEvent(@PathParam("label") String label,
			@PathParam("ndSrcId") int ndSrcId, @PathParam("ndTgtId") int ndTgtId) {
		List<Event> allEvents = eventService.addEventService(label, ndSrcId, ndTgtId);
		return allEvents;

	}

	@GET
	@Path("/getEvents")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getAllData() {

		List<Event> allEvents= eventService.getAllDataService();
		return allEvents;
	}

	@DELETE
	@Path("/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> delete(@PathParam("eventId") String id) {
		List<Event> allEvents= eventService.deleteEventService(id);
		return allEvents;
	}

	// @RequestMapping(value = "/editDeveloper")
	@GET
	@Path("/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event read(@PathParam("eventId") String id) {

		Event e = eventService.detailsEventService(id);
		return e;

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> update(Event updatedEvt) {
		List<Event> allEvents= eventService.updateEventService(updatedEvt);
		return allEvents;
	}
}
