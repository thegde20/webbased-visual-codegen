package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Script extends Node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//@Id
	private String scriptName;
	
	@OneToMany(mappedBy = "script", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Statement> scriptStatements;

	public Script() {
		super();
	}

	public Script(String scriptName, Collection<Statement> scriptStatements) {
		super();
		this.scriptName = scriptName;
		this.scriptStatements = scriptStatements;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public Collection<Statement> getScriptStatements() {
		return scriptStatements;
	}

	public void setScriptStatements(Collection<Statement> scriptStatements) {
		this.scriptStatements = scriptStatements;
	}

	@Override
	public String toString() {
		return "Script [scriptName=" + scriptName + ", scriptStatements="
				+ scriptStatements + "]";
	}	
	
}
