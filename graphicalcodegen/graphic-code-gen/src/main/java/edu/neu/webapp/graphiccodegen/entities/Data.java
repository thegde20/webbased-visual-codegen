package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Data implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String dataName;
	
	private String dataValue;
	
	private String dataType;

	public Data() {
		super();
	}

	public Data(String dataName, String dataValue, String dataType) {
		super();
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
