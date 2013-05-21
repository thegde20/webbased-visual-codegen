package edu.neu.webapp.graphiccodegen.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class StatementType {

	@Id
	private String sType;
	
	@OneToMany(mappedBy = "statementType", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Statement> statements;

	public StatementType() {
		super();
	}

	
}

