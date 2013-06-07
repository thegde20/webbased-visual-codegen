package edu.neu.webapp.graphiccodegen.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the node database table.
 * 
 */
@Embeddable
public class NodePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="flowID")
	private int flowID;

	public NodePK() {
	}
	public NodePK(int flowId) {
		this.flowID = flowId;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFlowID() {
		return this.flowID;
	}
	public void setFlowID(int flowID) {
		this.flowID = flowID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NodePK)) {
			return false;
		}
		NodePK castOther = (NodePK)other;
		return 
			(this.id == castOther.id)
			&& (this.flowID == castOther.flowID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.flowID;
		
		return hash;
	}
}