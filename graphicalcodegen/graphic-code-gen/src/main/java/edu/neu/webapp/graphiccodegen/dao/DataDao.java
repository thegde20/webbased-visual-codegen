package edu.neu.webapp.graphiccodegen.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.webapp.graphiccodegen.entities.Data;

@Component
public class DataDao {
	
	// Injected base connection:
				@PersistenceContext
				private EntityManager em;

				// Stores a new Data:
				@Transactional
				public void persist(Data data) {
					em.persist(data);
				}
			    
				@Transactional
				public void deleteDataStatementById(int dataStatementId) {
					Data dataStatement = em.find(Data.class, dataStatementId);
					if (dataStatement != null) {
						em.remove(dataStatement);
					}
				}
				
				/* // Updates the Data object after an operation is performed -- commented as the value of data should change on running the application
			    @Transactional
			    public void updateDataById(int dataStatementId, int operand1, int operand2, String operator) {
			    	
			    	Data modifyData = getData(dataStatementId);
			    	Data data1 = getData(operand1);
			    	Data data2 = getData(operand2);
			    	
			        if (modifyData != null && data1 != null && data2 != null) {
			        	
			        	if(operator.equals("+")){
			        		modifyData.setFinalDataValue(String.valueOf((Integer.parseInt(data1.getFinalDataValue())+Integer.parseInt(data2.getFinalDataValue()))));
			        	}else if(operator.equals("-")){
			        		modifyData.setFinalDataValue(String.valueOf((Integer.parseInt(data1.getFinalDataValue())-Integer.parseInt(data2.getFinalDataValue()))));
			        	}else if(operator.equals("*")){
			        		modifyData.setFinalDataValue(String.valueOf((Integer.parseInt(data1.getFinalDataValue())*Integer.parseInt(data2.getFinalDataValue()))));
			        	}else if(operator.equals("/")){
			        		modifyData.setFinalDataValue(String.valueOf((Integer.parseInt(data1.getFinalDataValue())/Integer.parseInt(data2.getFinalDataValue()))));
			        	}else if(operator.equals("^")){
			        		modifyData.setFinalDataValue(String.valueOf(Math.pow(Integer.parseInt(data1.getFinalDataValue()), Integer.parseInt(data2.getFinalDataValue()))));
			        	}else if(operator.equals("<")){
			        		modifyData.setFinalDataValue(String.valueOf((Integer.parseInt(data1.getFinalDataValue())<Integer.parseInt(data2.getFinalDataValue()))?Integer.parseInt(data1.getFinalDataValue()):Integer.parseInt(data2.getFinalDataValue())));
			        	}else if(operator.equals(">")){
			        		modifyData.setFinalDataValue(String.valueOf((Integer.parseInt(data1.getFinalDataValue())>Integer.parseInt(data2.getFinalDataValue()))?Integer.parseInt(data1.getFinalDataValue()):Integer.parseInt(data2.getFinalDataValue())));
			        	}else{
			        		System.out.println("Resultant not modified");
			        	}
			        	
			        } 
			    }
			    
			    */
				
			    @Transactional
				public void dataAfterUpdate(int dataStatementId, String dataName, String dataType, String initDataValue){
			    	
			    	Data data = em.find(Data.class, dataStatementId);
			    	
			    	if (data != null) {
			    		
			    		data.setDataName(dataName);
				    	data.setDataType(dataType);
				    	data.setInitDataValue(initDataValue);
				    	
			    	}	
				}
			    
				// Returns a Data object 
				public Data getData(int dataStmtId) {
					Data data = em.find(Data.class, dataStmtId);
					return data;
				}
				
				public List<Data> getAllDataStatements(String scriptName) {
					TypedQuery<Data> query = em.createQuery("SELECT d FROM Data d WHERE d.script.scriptName=:dataScript ORDER BY d.statementId", Data.class)
							.setParameter("dataScript", scriptName);
					return query.getResultList();
				}
				
				public List<Data> getAllDataStatements() {
					TypedQuery<Data> query = em.createQuery("SELECT d FROM Data d ORDER BY d.statementId", Data.class);
					return query.getResultList();
				}
				
				public List<Data> getAllInputStatements(String scriptName){
					
					List<Data> allData = getAllDataStatements(scriptName);
					List<Data> inputData = new ArrayList<Data>();
					for(Data data : allData){
						if(!data.getIsData()){
							inputData.add(data);
						}
					}
					return inputData;
				}
				
				public List<Data> getAllDeclarativeStatements(String scriptName){
					
					List<Data> allData = getAllDataStatements(scriptName);
					List<Data> declarativeData = new ArrayList<Data>();
					for(Data data : allData){
						if(data.getIsData()){
							declarativeData.add(data);
						}
					}
					return declarativeData;
				}
}
