package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class StringOperation extends Operation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int index1;
	private int index2;
	
	public StringOperation(StatementType statementType, Script script,
			OperationType operationType, Data data1, Data data2, Data result,
			String operator1, int index1, int index2) {
		super(statementType, script, operationType, data1, data2, result,
				operator1);
		this.index1 = index1;
		this.index2 = index2;
	}

	public int getIndex1() {
		return index1;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public int getIndex2() {
		return index2;
	}

	public void setIndex2(int index2) {
		this.index2 = index2;
	}
	
}
