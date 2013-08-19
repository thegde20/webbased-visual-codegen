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
	
	private String type;

	private String name;

	//bi-directional many-to-one association to Flow
	@ManyToOne
	@JoinColumn(name="flowID")
	private Flow flow;
	
	//bi-directional many-to-one association to Flow
	@OneToMany(mappedBy="nodeSource",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Event> sourceEvent;

	//bi-directional many-to-one association to Flow
	@OneToMany(mappedBy="nodeTarget",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Event> targetEvent;

	public Node() {
	}

	public Node (String name,Flow flow,String type){
		this.name=name;
		this.flow = flow;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Event> getSourceEvent() {
		return sourceEvent;
	}

	public void setSourceEvent(List<Event> sourceEvent) {
		this.sourceEvent = sourceEvent;
	}

	public List<Event> getTargetEvent() {
		return targetEvent;
	}

	public void setTargetEvent(List<Event> targetEvent) {
		this.targetEvent = targetEvent;
	}

}