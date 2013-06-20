package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Flow;

@Component
public class FlowDao {

	// Injected database connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new flow
	@Transactional
	public void persist(Flow f) {
		em.persist(f);
	}

	// Returns a Developer object whose email is = value of email
	public Flow getFlow(String id) {
		Flow fl = em.find(Flow.class, Integer.parseInt(id));
		return fl;
	}

	// Retrieves all the StatementType:
	public List<Flow> getAllFlows() {
		TypedQuery<Flow> query = em.createQuery(
				"SELECT d FROM Flow d ORDER BY d.name", Flow.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteById(String id) {
		Flow fl = em.find(Flow.class, Integer.parseInt(id));
		if (fl != null) {
			em.remove(fl);
		}
	}

}
