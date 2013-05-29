package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class NumberOperation extends Operation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToMany
    @JoinColumn(name = "data3", nullable=true, referencedColumnName = "dataName")
	private Data data3;
	
	private String operator2;

	public NumberOperation(StatementType statementType, Script script,
			OperationType operationType, Data data1, Data data2, Data result,
			String operator1, Data data3, String operator2) {
		super(statementType, script, operationType, data1, data2, result,
				operator1);
		this.data3 = data3;
		this.operator2 = operator2;
	}

	public Data getData3() {
		return data3;
	}

	public void setData3(Data data3) {
		this.data3 = data3;
	}

	public String getOperator2() {
		return operator2;
	}

	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}
}
