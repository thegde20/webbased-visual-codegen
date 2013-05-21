package edu.neu.webapp.graphiccodegen.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Statement {

	@Id @GeneratedValue
	private int statementId;
	
	@ManyToOne// Foreign Key
    @JoinColumn(name = "statementType", referencedColumnName = "sType")
	private StatementType statementType;
    
	
}
