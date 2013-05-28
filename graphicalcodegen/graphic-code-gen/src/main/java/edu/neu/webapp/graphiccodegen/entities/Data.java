package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Data extends Statement implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dataName;
	
	private String dataValue;
	
	private String dataType;

	public Data(StatementType statementType, Script script, String dataName, String dataValue, String dataType) {
		super(statementType, script);
		this.dataName = dataName;
		this.dataValue = dataValue;
		this.dataType = dataType;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "Data [dataName=" + dataName + ", dataValue=" + dataValue
				+ ", dataType=" + dataType + "]";
	}

}
