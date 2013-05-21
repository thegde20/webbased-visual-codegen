package edu.neu.webapp.graphiccodegen.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Script {

	@Id
	private String scriptName;

	public Script() {
		super();
	}

	public Script(String scriptName) {
		super();
		this.scriptName = scriptName;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	@Override
	public String toString() {
		return "Script [scriptName=" + scriptName + "]";
	}	
    
}
