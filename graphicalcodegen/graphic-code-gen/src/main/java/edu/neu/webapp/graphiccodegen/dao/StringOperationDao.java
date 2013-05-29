package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.StringOperation;

@Component
public class StringOperationDao {
	
	// Injected base connection:
		@PersistenceContext
		private EntityManager em;

		// Stores a new StringOperation:
		@Transactional
		public void persist(StringOperation stringOperation) {
			em.persist(stringOperation);
		}
	    
		@Transactional
		public void deleteStringOperationStatementById(int stringOperationStatementId) {
			StringOperation stringOperationStatement = em.find(StringOperation.class, stringOperationStatementId);
			if (stringOperationStatement != null) {
				em.remove(stringOperationStatement);
			}
		}
		
		// Returns a StringOperation object 
		public StringOperation getStringOperation(int stringOperationStmtId) {
			StringOperation stringOperation = em.find(StringOperation.class, stringOperationStmtId);
			return stringOperation;
		}
		
		// Retrieves all the Statements:
		public List<StringOperation> getAllStringOperationStatements() {
			
			TypedQuery<StringOperation> query = em.createQuery("SELECT so FROM StringOperation so ORDER BY so.statementId", StringOperation.class);
			return query.getResultList();
		}
}
