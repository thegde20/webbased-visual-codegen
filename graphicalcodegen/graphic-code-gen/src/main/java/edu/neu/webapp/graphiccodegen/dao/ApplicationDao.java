package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.model.Application;
import edu.neu.webapp.graphiccodegen.model.Developer;

@Component
public class ApplicationDao {

	// Injected database connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new application
	@Transactional
	public void persist(Application app) {
		em.flush();
		em.persist(app);
	}

	// Returns a application object whose id is = value of id
	public Application getApplication(String Id) {
		Application app = em.find(Application.class, Integer.parseInt(Id));
		return app;
	}

	// Retrieves all the applications:
	public List<Application> getAllApplications() {
		TypedQuery<Application> query = em.createQuery(
				"SELECT a FROM Application a ORDER BY a.name", Application.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteById(String Id) {
		Application app = em.find(Application.class, Integer.parseInt(Id));
		if (app != null) {
			em.remove(app);
		}
	}

}
