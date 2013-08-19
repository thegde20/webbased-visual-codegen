package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Data;
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
		
	    @Transactional
	    public void StringOperationAfterUpdatedSubstring(int stringOperationStatementId, Data newResult, Data newData1, int newStartIndex, int newEndIndex) {
		
	    	StringOperation stringOperationStatement = em.find(StringOperation.class, stringOperationStatementId);

	    	if (stringOperationStatement != null) {	
	    		stringOperationStatement.setResult(newResult);
	    		stringOperationStatement.setData1(newData1);
	    		stringOperationStatement.setIndex1(newStartIndex);
	    		stringOperationStatement.setIndex2(newEndIndex);
	    	}
	    }
	    
	    @Transactional
	    public void StringOperationAfterUpdatedConcat(int stringOperationStatementId, Data newResult, Data newData1, Data newData2) {
		
	    	StringOperation stringOperationStatement = em.find(StringOperation.class, stringOperationStatementId);

	    	if (stringOperationStatement != null) {	
	    		
	    		stringOperationStatement.setResult(newResult);
	    		stringOperationStatement.setData1(newData1);
	    		stringOperationStatement.setData2(newData2);
	    	}
	    }
		
		// Returns a StringOperation object 
		public StringOperation getStringOperation(int stringOperationStmtId) {
			StringOperation stringOperation = em.find(StringOperation.class, stringOperationStmtId);
			return stringOperation;
		}
		
		// Retrieves all the Statements belonging to the given script:
		public List<StringOperation> getAllStringOperationStatements(String scriptName) {
			TypedQuery<StringOperation> query = em.createQuery("SELECT so FROM StringOperation so WHERE so.script.scriptName=:stringScript ORDER BY so.statementId", StringOperation.class)
					.setParameter("stringScript", scriptName);
			return query.getResultList();
		}
}
