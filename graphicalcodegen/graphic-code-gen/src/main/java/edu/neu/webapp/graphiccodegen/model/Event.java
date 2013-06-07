package edu.neu.webapp.graphiccodegen.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Event
 *
 */
@Entity

public class Event implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Event() {
		super();
	}
	public Event(String label,Node ndSrc,Node ndTarget) {
		//super();
		this.label = label;
		this.nodeSource = ndSrc;
		this.nodeTarget = ndTarget;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private String label;
	
	@ManyToOne
	@JoinColumn(name="nodeSrc")
	private Node nodeSource;
	
	@ManyToOne
	@JoinColumn(name="nodeTarget")
	private Node nodeTarget;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Node getNodeSource() {
		return nodeSource;
	}

	public void setNodeSource(Node nodeSource) {
		this.nodeSource = nodeSource;
	}

	public Node getNodeTarget() {
		return nodeTarget;
	}

	public void setNodeTarget(Node nodeTarget) {
		this.nodeTarget = nodeTarget;
	}

   
}
