package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.StatementType;
import edu.neu.webapp.graphiccodegen.model.Developer;

@Component
public class DeveloperDao {

	// Injected database connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new developer
	@Transactional
	public void persist(Developer d) {
		em.persist(d);
	}

	// Returns a Developer object whose email is = value of email
	public Developer getDeveloper(String email) {
		Developer dev = em.find(Developer.class, email);
		return dev;
	}

	// Retrieves all the StatementType:
	public List<Developer> getAllDevelopers() {
		TypedQuery<Developer> query = em.createQuery(
				"SELECT d FROM Developer d ORDER BY d.name", Developer.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteByEmail(String email) {
		Developer dev = em.find(Developer.class, email);
		if (dev != null) {
			em.remove(dev);
		}
	}

}
