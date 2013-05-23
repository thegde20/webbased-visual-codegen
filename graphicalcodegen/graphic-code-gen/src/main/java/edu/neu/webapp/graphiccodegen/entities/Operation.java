package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Operation implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id 
	@OneToOne(optional=false)
	private Statement operationId;
	
	@ManyToOne// Foreign Key
    @JoinColumn(name = "operationType", referencedColumnName = "oType")
	private OperationType operationType;

	@OneToMany(mappedBy = "script", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Data> datas;
	
	@OneToOne(optional=false, mappedBy="dataName")
	private Data result;
	
	private String operator1;
	private String operator2;
	
	public Operation() {
		super();
	}

	public Operation(Statement operationId, OperationType operationType,
			Data data1, Data data2, Data data3, Data result, String operator1,
			String operator2) {
		super();
		this.operationId = operationId;
		this.operationType = operationType;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.result = result;
		this.operator1 = operator1;
		this.operator2 = operator2;
	}

	public Statement getOperationId() {
		return operationId;
	}

	public void setOperationId(Statement operationId) {
		this.operationId = operationId;
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

	public Data getData3() {
		return data3;
	}

	public void setData3(Data data3) {
		this.data3 = data3;
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

	public String getOperator2() {
		return operator2;
	}

	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}

	@Override
	public String toString() {
		return "Operation [operationId=" + operationId + ", operationType="
				+ operationType + ", data1=" + data1 + ", data2=" + data2
				+ ", data3=" + data3 + ", result=" + result + ", operator1="
				+ operator1 + ", operator2=" + operator2 + "]";
	}	

}
