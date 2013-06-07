package edu.neu.webapp.graphiccodegen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Branch;

@Component
public class BranchDao {
	
	// Injected base connection:
	@PersistenceContext
	private EntityManager em;

	// Stores a new Branch:
	@Transactional
	public void persist(Branch branch) {
		em.persist(branch);
	}
    
	@Transactional
	public void deleteBranchStatementById(int branchStatementId) {
		Branch branchStatement = em.find(Branch.class, branchStatementId);
		if (branchStatement != null) {
			em.remove(branchStatement);
		}
	}
	
	// Returns a Branch object 
	public Branch getBranch(int branchStmtId) {
		Branch branch = em.find(Branch.class, branchStmtId);
		return branch;
	}
	
	// Retrieves all the Statements:
	public List<Branch> getAllBranchStatements(String scriptName) {
		
		TypedQuery<Branch> query = em.createQuery("SELECT b FROM Branch b WHERE b.script.scriptName=:branchScript ORDER BY b.statementId", Branch.class)
				.setParameter("branchScript", scriptName);
		return query.getResultList();
	}

}
