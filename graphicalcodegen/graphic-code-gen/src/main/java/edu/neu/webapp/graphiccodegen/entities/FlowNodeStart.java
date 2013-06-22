package edu.neu.webapp.graphiccodegen.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: FlowNodeStart
 *
 */
@Entity

public class FlowNodeStart implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne
	@JoinColumn(name="fid" , nullable = false)
	private Flow flow;
	@OneToOne
	@JoinColumn(name="nid" , nullable = false)
	private Node node;
	
	public FlowNodeStart() {
		super();
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
   
}
