package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Developer;
import edu.neu.webapp.graphiccodegen.entities.Flow;

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
	public Application getApplication(int Id) {
		Application app = em.find(Application.class, Id);
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
	public List<Flow> getFlowsForApplication(int id){
		TypedQuery<Flow> query = em.createQuery(
				"SELECT a FROM Flow a where a.application.id=:id", Flow.class);
		query.setParameter("id", id);
		return query.getResultList();
	
	}
	@Transactional
	public void updateApplication(String name,int id){
		Application app = em.find(Application.class, id);
		if(app != null){
			app.setName(name);
		}
	}


}
