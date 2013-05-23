package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class StatementType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String sType;
	
	@OneToMany(mappedBy = "statementType", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Statement> statements;

	public StatementType() {
		super();
	}

	public StatementType(String sType, Collection<Statement> statements) {
		super();
		this.sType = sType;
		this.statements = statements;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public Collection<Statement> getStatements() {
		return statements;
	}

	public void setStatements(Collection<Statement> statements) {
		this.statements = statements;
	}

	@Override
	public String toString() {
		return "StatementType [sType=" + sType + ", statements=" + statements
				+ "]";
	}

}

