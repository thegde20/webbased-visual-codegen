package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Event;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;

@Component
public class NodeDao {

	// Injected database connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new node
	@Transactional
	public void persist(Node nd) {
		em.flush();
		em.persist(nd);
	}

	// Returns a Developer object whose email is = value of email
	public Node getNode(int id) {
		Node nd = em.find(Node.class, id);
		return nd;
	}

	// Retrieves all the StatementType:
	public List<Node> getAllNodes() {
		TypedQuery<Node> query = em.createQuery(
				"SELECT d FROM Node d ORDER BY d.name", Node.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteById(String id) {
		Node nd = em.find(Node.class, Integer.parseInt(id));
		if (nd != null) {
			em.remove(nd);
		}
	}
	public List<Event> getSourceEventsForNode(int id){
		TypedQuery<Event> query = em.createQuery(
				"SELECT a FROM Event a where a.nodeSource.id=:id", Event.class);
		query.setParameter("id", id);
		return query.getResultList();

	}
	public List<Event> getTargetEventsForNode(int id){
		TypedQuery<Event> query = em.createQuery(
				"SELECT a FROM Event a where a.nodeTarget.id=:id", Event.class);
		query.setParameter("id", id);
		return query.getResultList();

	}
	@Transactional
	public void updateNode(String name,String type,int id){
		Node nd = em.find(Node.class, id);
		if(nd != null){
			nd.setName(name);
			nd.setType(type);
		}
	}

}