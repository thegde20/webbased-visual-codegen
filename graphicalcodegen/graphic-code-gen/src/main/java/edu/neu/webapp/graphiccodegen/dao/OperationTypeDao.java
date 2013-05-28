package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.OperationType;

@Component
public class OperationTypeDao {
	
	// Injected database connection:
				@PersistenceContext
				private EntityManager em;

				//Stores a new OperationType
				@Transactional
				public void persist(OperationType operationType) {
					em.persist(operationType);
				}
			    
				// Returns a OperationType object 
				public OperationType getOperationType(String opType) {
					OperationType operationType = em.find(OperationType.class, opType);
					return operationType;
				}

				// Retrieves all the OperationType:
				public List<OperationType> getAllOperationTypes() {
					TypedQuery<OperationType> query = em.createQuery("SELECT ot FROM OperationType ot ORDER BY ot.oType", OperationType.class);
					return query.getResultList();
				}
	}

