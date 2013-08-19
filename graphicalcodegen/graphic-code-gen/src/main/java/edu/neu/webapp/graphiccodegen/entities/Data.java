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
	
	private String dataType;
	
	private Boolean isData;

	public Data() {
		super();
	}
	
	public Data(StatementType statementType, Script script, String dataName, String dataValue, String dataType, Boolean isData) {
		super(statementType, script);
		this.dataName = dataName;
		this.initDataValue = dataValue;
		this.dataType = dataType;
		this.isData = isData;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Boolean getIsData() {
		return isData;
	}

	public void setIsData(Boolean isData) {
		this.isData = isData;
	}
	
}
