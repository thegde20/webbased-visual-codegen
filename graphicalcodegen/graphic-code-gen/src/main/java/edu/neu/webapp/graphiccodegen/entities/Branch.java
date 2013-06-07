package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Branch extends Statement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToMany
    @JoinColumn(name = "branchingData", nullable=false, referencedColumnName = "dataName")
	private Data branchingData;
	
	@ManyToOne// Foreign Key
    @JoinColumn(name = "trueStatementId", referencedColumnName = "statementId")
	private Statement trueStatementId;
	
	@ManyToOne// Foreign Key
    @JoinColumn(name = "falseStatementId", referencedColumnName = "statementId")
	private Statement falseStatementId;
	
	public Branch(StatementType statementType, Script script, Data branchingData, Statement trueStatementId,
			Statement falseStatementId) {
		super(statementType, script);
		this.branchingData = branchingData;
		this.trueStatementId = trueStatementId;
		this.falseStatementId = falseStatementId;
	}

	public Data getBranchingData() {
		return branchingData;
	}

	public void setBranchingData(Data branchingData) {
		this.branchingData = branchingData;
	}

	public Statement getTrueStatementId() {
		return trueStatementId;
	}

	public void setTrueStatementId(Statement trueStatementId) {
		this.trueStatementId = trueStatementId;
	}

	public Statement getFalseStatementId() {
		return falseStatementId;
	}

	public void setFalseStatementId(Statement falseStatementId) {
		this.falseStatementId = falseStatementId;
	}	
		
}
