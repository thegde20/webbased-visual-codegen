package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Statement;

@Component
public class StatementDao {

	// Injected database connection:
			@PersistenceContext
			private EntityManager em;

			// Stores a new Statement:
			@Transactional
			public void persist(Statement statement) {
				em.persist(statement);
			}

			@Transactional
			public void deleteStatementById(int statementId) {
				Statement statement = em.find(Statement.class, statementId);
				if (statement != null) {
					em.remove(statement);
				}
			}
			
			public Statement getStatement(int dataStmtId) {
				Statement statement = em.find(Statement.class, dataStmtId);
				return statement;
			}
			
			// Retrieves all the Statements belonging to the given Script:
			public List<Statement> getAllStatements(String scriptName) {
				TypedQuery<Statement> query = em.createQuery("SELECT s FROM Statement s WHERE s.script.scriptName=:stmtScript ORDER BY s.statementId", Statement.class)
						.setParameter("stmtScript", scriptName);
				return query.getResultList();
			}
}
