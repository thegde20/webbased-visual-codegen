package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Operation extends Statement implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne// Foreign Key
    @JoinColumn(name = "operationType", referencedColumnName = "oType")
	private OperationType operationType;

	@ManyToMany
    @JoinColumn(name = "data1", nullable=false, referencedColumnName = "dataName")
	private Data data1;

	@ManyToMany
    @JoinColumn(name = "data2", nullable=true, referencedColumnName = "dataName")
	private Data data2;
	
	@OneToOne
	@JoinColumn(name="result", nullable=true)
	private Data result;
	
	private String operator1;
	
	public Operation(StatementType statementType, Script script, OperationType operationType, Data data1, Data data2, Data result, String operator1) {
		super(statementType, script);
		this.operationType = operationType;
		this.data1 = data1;
		this.data2 = data2;
		this.result = result;
		this.operator1 = operator1;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Data getData1() {
		return data1;
	}

	public void setData1(Data data1) {
		this.data1 = data1;
	}

	public Data getData2() {
		return data2;
	}

	public void setData2(Data data2) {
		this.data2 = data2;
	}

	public Data getResult() {
		return result;
	}

	public void setResult(Data result) {
		this.result = result;
	}

	public String getOperator1() {
		return operator1;
	}

	public void setOperator1(String operator1) {
		this.operator1 = operator1;
	}	
}
