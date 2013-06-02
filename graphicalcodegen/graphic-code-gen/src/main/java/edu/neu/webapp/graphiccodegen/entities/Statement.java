package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Statement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int statementId;
	
	@ManyToOne// Foreign Key
    @JoinColumn(name = "statementType", referencedColumnName = "sType")
	private StatementType statementType;
	
	@ManyToOne// Foreign Key
    @JoinColumn(name = "script", referencedColumnName = "scriptName")
	public Script script;
	
	@OneToMany(mappedBy = "trueStatementId", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Branch> trueStatements;
	
	@OneToMany(mappedBy = "falseStatementId", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Branch> falseStatements;
	
	public Statement() {
		super();
	}

	public Statement(StatementType statementType, Script script) {
		super();
		this.statementType = statementType;
		this.script = script;
	}

	public int getStatementId() {
		return statementId;
	}

	public void setStatementId(int statementId) {
		this.statementId = statementId;
	}

	public StatementType getStatementType() {
		return statementType;
	}

	public void setStatementType(StatementType statementType) {
		this.statementType = statementType;
	}

	public Script getScript() {
		return script;
	}

	public void setScript(Script script) {
		this.script = script;
	}

	@Override
	public String toString() {
		return "Statement [statementId=" + statementId + ", statementType="
				+ statementType + ", script=" + script + "]";
	}	
	
}
