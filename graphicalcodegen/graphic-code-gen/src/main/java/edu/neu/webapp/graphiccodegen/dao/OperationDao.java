package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Operation;

@Component
public class OperationDao {
	
	// Injected base connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new Operation:
	@Transactional
	public void persist(Operation operation) {
		em.persist(operation);
	}
    
	@Transactional
	public void deleteOperationStatementById(int operationStatementId) {
		Operation operationStatement = em.find(Operation.class, operationStatementId);
		if (operationStatement != null) {
			em.remove(operationStatement);
		}
	}
	
	// Returns a Operation object 
	public Operation getOperation(int operationStmtId) {
		Operation operation = em.find(Operation.class, operationStmtId);
		return operation;
	}
	
	// Retrieves all the Statements:
	public List<Operation> getAllOperationStatements() {
		
		TypedQuery<Operation> query = em.createQuery("SELECT o FROM Operation o ORDER BY o.statementId", Operation.class);
		return query.getResultList();
	}

}
