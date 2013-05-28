package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.StatementType;

@Component
public class StatementTypeDao {
	
	// Injected database connection:
			@PersistenceContext
			private EntityManager em;

			//Stores a new StatementType
			@Transactional
			public void persist(StatementType stmtType) {
				em.persist(stmtType);
			}
		    
			// Returns a StatementType object whose sType is = value of stmtType
			public StatementType getStatementType(String stmtType) {
				StatementType statementType = em.find(StatementType.class, stmtType);
				return statementType;
			}

			// Retrieves all the StatementType:
			public List<StatementType> getAllStatementTypes() {
				TypedQuery<StatementType> query = em.createQuery("SELECT st FROM StatementType st ORDER BY st.sType", StatementType.class);
				return query.getResultList();
			}
}
