package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the node database table.
 * 
 */
@Entity
public class Node implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;

	//bi-directional many-to-one association to Flow
	@ManyToOne
	@JoinColumn(name="flowID")
	private Flow flow;
	
	//bi-directional many-to-one association to Flow
	@OneToMany(mappedBy="nodeSource")
	private List<Event> sourceEvent;

	//bi-directional many-to-one association to Flow
	@OneToMany(mappedBy="nodeTarget")
	private List<Event> targetEvent;

	public Node() {
	}

	public Node (String name,Flow flow){
		this.name=name;
		this.flow = flow;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Flow getFlow() {
		return this.flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

}