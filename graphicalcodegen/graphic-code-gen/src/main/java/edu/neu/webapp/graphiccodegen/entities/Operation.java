package edu.neu.webapp.graphiccodegen.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Operation {
	
	 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	    Long id;

}
