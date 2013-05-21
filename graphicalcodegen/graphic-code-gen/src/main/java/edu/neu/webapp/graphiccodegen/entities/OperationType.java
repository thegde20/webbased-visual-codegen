package edu.neu.webapp.graphiccodegen.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OperationType {

	@Id
	private String oType;
	
	@OneToMany(mappedBy = "operationType", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Operation> operations;

	public OperationType() {
		super();
	}

	public OperationType(String oType, Collection<Operation> operations) {
		super();
		this.oType = oType;
		this.operations = operations;
	}

	public String getoType() {
		return oType;
	}

	public void setoType(String oType) {
		this.oType = oType;
	}

	public Collection<Operation> getOperations() {
		return operations;
	}

	public void setOperations(Collection<Operation> operations) {
		this.operations = operations;
	}

	@Override
	public String toString() {
		return "OperationType [oType=" + oType + ", operations=" + operations
				+ "]";
	}
	
	
}
