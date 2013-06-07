package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.NumberOperation;

@Component
public class NumberOperationDao {
	
	// Injected base connection:
			@PersistenceContext
			private EntityManager em;

			// Stores a new NumberOperation:
			@Transactional
			public void persist(NumberOperation numberOperation) {
				em.persist(numberOperation);
			}
		    
			@Transactional
			public void deleteNumberOperationById(int numberOperationStatementId) {
				NumberOperation numberOperationStatement = em.find(NumberOperation.class, numberOperationStatementId);
				if (numberOperationStatement != null) {
					em.remove(numberOperationStatement);
				}
			}
			
			// Returns a NumberOperation object 
			public NumberOperation getNumberOperation(int numberOperationStmtId) {
				NumberOperation numberOperation = em.find(NumberOperation.class, numberOperationStmtId);
				return numberOperation;
			}
			
			// Retrieves all the Statements belonging to the given script:
			public List<NumberOperation> getAllNumberOperationStatements(String scriptName) {
				TypedQuery<NumberOperation> query = em.createQuery("SELECT no FROM NumberOperation no WHERE no.script.scriptName=:numScript ORDER BY no.statementId", NumberOperation.class)
						.setParameter("numScript", scriptName);
				return query.getResultList();
			}

}
