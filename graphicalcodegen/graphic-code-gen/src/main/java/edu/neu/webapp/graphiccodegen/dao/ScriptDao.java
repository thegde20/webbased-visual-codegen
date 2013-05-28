package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Script;

@Component
public class ScriptDao {

	// Injected database connection:
		@PersistenceContext
		private EntityManager em;

		// Stores a new Script:
		@Transactional
		public void persist(Script script) {
			em.persist(script);
		}

		@Transactional
		public void deleteScriptById(String scriptName) {
			Script script = em.find(Script.class, scriptName);
			if (script != null) {
				em.remove(script);
			}
		}
	    
		// Returns a Script object whose scriptName is = value of sName
		public Script getScript(String sName) {
			Script script = em.find(Script.class, sName);
			return script;
		}

		// Retrieves all the Scripts:
		public List<Script> getAllScripts() {
			TypedQuery<Script> query = em.createQuery("SELECT s FROM Script s ORDER BY s.scriptName", Script.class);
			return query.getResultList();
		}
}
