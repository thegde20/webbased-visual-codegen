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
	
	private String initDataValue;
	
	private String finalDataValue;
	
	private String dataType;

	public Data(StatementType statementType, Script script, String dataName, String dataValue, String dataType) {
		super(statementType, script);
		this.dataName = dataName;
		this.initDataValue = dataValue;
		this.finalDataValue = dataValue;
		this.dataType = dataType;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getInitDataValue() {
		return initDataValue;
	}

	public void setInitDataValue(String initDataValue) {
		this.initDataValue = initDataValue;
	}

	public String getFinalDataValue() {
		return finalDataValue;
	}

	public void setFinalDataValue(String finalDataValue) {
		this.finalDataValue = finalDataValue;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
		
}
