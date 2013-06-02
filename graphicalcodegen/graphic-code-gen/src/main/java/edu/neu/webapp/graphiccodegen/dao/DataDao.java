package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Data;

@Component
public class DataDao {
	
	// Injected base connection:
				@PersistenceContext
				private EntityManager em;

				// Stores a new Data:
				@Transactional
				public void persist(Data data) {
					em.persist(data);
				}
			    
				@Transactional
				public void deleteDataStatementById(int dataStatementId) {
					Data dataStatement = em.find(Data.class, dataStatementId);
					if (dataStatement != null) {
						em.remove(dataStatement);
					}
				}
				
				// Returns a Data object 
				public Data getData(int dataStmtId) {
					Data data = em.find(Data.class, dataStmtId);
					return data;
				}
				
				public List<Data> getAllDataStatements(String scriptName) {
					TypedQuery<Data> query = em.createQuery("SELECT d FROM Data d WHERE d.script.scriptName=:abc ORDER BY d.statementId", Data.class)
							.setParameter("abc", scriptName);
					return query.getResultList();
				}
				
}
