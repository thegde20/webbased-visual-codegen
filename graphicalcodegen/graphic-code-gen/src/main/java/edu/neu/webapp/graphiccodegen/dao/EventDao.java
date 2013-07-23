package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Event;
import edu.neu.webapp.graphiccodegen.entities.Node;

@Component
public class EventDao {


	// Injected database connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new Event
	@Transactional
	public void persist(Event f) {
		em.persist(f);
	}

	// Returns a Developer object whose email is = value of email
	public Event getEvent(String id) {
		Event evt = em.find(Event.class, Integer.parseInt(id));
		return evt;
	}

	// Retrieves all the StatementType:
	public List<Event> getAllEvents() {
		TypedQuery<Event> query = em.createQuery(
				"SELECT d FROM Event d ORDER BY d.label", Event.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteById(String id) {
		Event evt = em.find(Event.class, Integer.parseInt(id));
		if (evt != null) {
			em.remove(evt);
		}
	}
	
	
	public List<Event> getEventBySourceNode(int i){
		TypedQuery<Event> query = em.createQuery("select e from Event e where e.nodeSource.id = :id", Event.class);
		query.setParameter("id", i);
		return query.getResultList();
	}
	@Transactional
	public void updateEvent(String label,Node ndSrc,Node ndTgt,int id){
		Event evt = em.find(Event.class, id);
		if(evt!= null){
			evt.setLabel(label);
			evt.setNodeSource(ndSrc);
			evt.setNodeTarget(ndTgt);
		}
	}


}
