package edu.neu.webapp.graphiccodegen.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.OperationDao;
import edu.neu.webapp.graphiccodegen.dao.StatementDao;
import edu.neu.webapp.graphiccodegen.dao.StringOperationDao;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.Operation;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.entities.StringOperation;
import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.DataService;
import edu.neu.webapp.graphiccodegen.services.PublishParam;

@Controller
@Path("/data")
@SessionAttributes({"sessionScriptName", "sessionStatementType", "sessionVariableObjects", "sessionInputVariables"})
public class DataController {
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
	@Autowired
	private DataService dataService;
	
	// Shift to service class
	@Autowired
	private StatementDao statementDao;
	
	@Autowired
    private DataDao dataDao;
	
	@Autowired
	private OperationDao operationDao;
	
	@Autowired
	private StringOperationDao stringOperationDao;
	
	 @RequestMapping(value="/statementWithValues")
	    public String renderChosenStatement(ModelMap model, HttpServletRequest request) {
		 	
		 	model.put("sessionStatementType", request.getParameter("scriptstmtType"));
		 	codeGenUtils.renderPageValues(model);
	        return "scriptstatementpage";
	    }
	
	 @RequestMapping(value="/dataWithValues")
	    public String installData(ModelMap model, HttpServletRequest request) {
		 	dataService.insertData(model,request);
	        return "scriptstatementpage";
	    }
	 
	@RequestMapping(value = "/editdatastatement")
	public String editDataStatement(ModelMap model, HttpServletRequest request) {
		if (request.getParameter("deleteAction") != null) {
			dataService.deleteData(model, request);			
		} else {
			dataService.updateData(model, request);
		}
		return "scriptstatementpage";
	}
	 
	@RequestMapping(value="/scriptstatementpage", params="scriptName")
	public String renderMainStatements(@RequestParam(value="scriptName") String scriptName, ModelMap model,HttpServletRequest request) {
		model.put("sessionScriptName", scriptName);
		codeGenUtils.renderPageValues(model);
		return "scriptstatementpage";
	}
	
	//@RequestMapping(value="/displayVariableValues")
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public String generateScriptContent(PublishParam param){
	//public String generateScriptContent(ModelMap model){
		 	try {
			// Shift to Service class		
		 		String scriptName = param.getNodeName();
			//String scriptName = String.valueOf(model.get("sessionScriptName"));
			List<Statement> statements = statementDao.getAllStatements(scriptName);
			StringBuilder fileData = new StringBuilder();
			fileData.append("<%@page import =\"java.util.HashMap\"%>\n" +
					"<%@page import=\"edu.neu.webapp.graphiccodegen.entities.*\"%>\n" +
					"<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>\n<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>" +
					"\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" +
					"\n\t<title>Final Values</title>\n</head>\n<body>\n\t<%\n");
			if (statements != null && statements.size() != 0) {
				fileData.append("\tboolean x = true;\n" +
						"\tint state = " + statements.get(0).getStatementId() + ";\n" +
						"\t@SuppressWarnings(\"unchecked\")" +
						"\n\tHashMap<String, String> inputElements = (HashMap<String, String>) session.getAttribute(\"sessionInputVariables\");\n" 
						+ displayInputVariables(scriptName)
						+ displayVariables(scriptName) 
						+ "\twhile(x){\n" +
						"\t\tswitch(state){\n");
				for (Statement statement : statements) {
					fileData.append("\t\t\tcase " + statement.getStatementId() + ": \n");
					if (statement.getStatementType().getsType().equalsIgnoreCase("Declarative")){
						fileData.append(displayDeclarationStatement(statement.getStatementId(), scriptName));
					}else{
						fileData.append(displayOperationStatement(statement.getStatementId(), scriptName));
					}
					
					if(statements.indexOf(statement)<statements.size()-1){
						
						fileData.append("\t\t\t\tstate = " + statements.get(statements.indexOf(statement) + 1).getStatementId() + ";\n");
								}else{
									fileData.append("\t\t\t\tstate = 0;\n");
								}
					fileData.append("\t\t\t\tbreak;\n");
				}
				fileData.append("\t\t\tdefault: x = false;\n" +
						"\t\t\t\tbreak;\n\t\t}\n\t}\n\n"+displayUpdatedVars(scriptName)+"\n\t%>\n");
				fileData.append(updateSessionFunction());
				fileData.append(sessionChecker());
			}
			else{
				fileData.append("System.out.println(\"No Statements!\n\");");
			}
			fileData.append("\n</body>\n</html>");
	
			/*ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL url = classLoader.getResource("");
			String path = url.getPath().substring(1, url.getPath().length()-8);
			System.out.println(path);*/
			//File jspFile = new File(path+"displayFinalValues.jsp");
			File jspFile = new File(param.getPath()+"/"+scriptName+".jsp");
			
			// if file does not exist, then create it
			if (!jspFile.exists()) {
				jspFile.createNewFile();
				System.out.println("File Created at "+jspFile.getAbsolutePath());
			}
			System.out.println("File present at "+jspFile.getAbsolutePath());
			FileWriter fileWritter = new FileWriter(jspFile.getAbsoluteFile());
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(fileData.toString());
			bufferWritter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//codeGenUtils.renderPageValues(model);
		}
		return "Success";
		//return "displayFinalValues";
	 }
	 
	private String displayUpdatedVars(String scriptName) {
		 
		 StringBuilder variablesToUpdate = new StringBuilder();
		 List<Data> dataStatements = dataDao.getAllDataStatements(scriptName);
		 for(Data dataStatement : dataStatements){
			 variablesToUpdate.append("\tupdateSession(\""+dataStatement.getDataName()+"\", String.valueOf("+dataStatement.getDataName()+"), inputElements);\n"); 
		 }
		 	
		 return variablesToUpdate.toString();
	}

	// This function puts all the Input and Declarative variables(which are likely to change) into the session
	 public String updateSessionFunction(){
		
		 StringBuilder updateVariables = new StringBuilder();
		updateVariables.append("\n\t<%!\n\t\tpublic void updateSession(String inputVar, String finalValue, HashMap<String, String> inputElements){\n");
		updateVariables.append("\t\t\tinputElements.put(inputVar, finalValue);\n");
		updateVariables.append("\t\t\tSystem.out.println(\"Value of \"+inputVar+\" = \"+inputElements.get(inputVar));\n\t\t}\n\t%>\n");
						
		return updateVariables.toString();
	}

	 /*This function writes to the dynamically generated file, all the input variables that are present in the given script and also calls a function to check if the
		input variable value is present in the session, if yes, then initializes it to the value in the session, else initializes to default value.*/
	 public String displayInputVariables(String scriptName) {
			
		StringBuilder declaredInputs = new StringBuilder();
		List<Data> inputStatements = dataDao.getAllInputStatements(scriptName);
		for(Data inputStatement : inputStatements){
			if(inputStatement.getDataType().equalsIgnoreCase("int")){
				declaredInputs.append("\tint "+ inputStatement.getDataName() + " = 0;\n\t"+inputStatement.getDataName()+" = Integer.parseInt(getValueFromSessionOnFind(\""+inputStatement.getDataName()+"\", \""+ inputStatement.getInitDataValue() +"\", inputElements));\n\n");				
			}else if(inputStatement.getDataType().equalsIgnoreCase("float")){
				declaredInputs.append("\tfloat "+ inputStatement.getDataName() + " = 0.0;\n\t"+inputStatement.getDataName()+" = Float.parseFloat(getValueFromSessionOnFind(\""+inputStatement.getDataName()+"\", \""+ inputStatement.getInitDataValue() +"\", inputElements));\n\n");
			}else if(inputStatement.getDataType().equalsIgnoreCase("boolean")){
				declaredInputs.append("\tboolean "+ inputStatement.getDataName() + " = false;\n\t"+inputStatement.getDataName()+" = Boolean.parseBoolean(getValueFromSessionOnFind(\""+inputStatement.getDataName()+"\", \""+ inputStatement.getInitDataValue() +"\", inputElements));\n\n"); 
			}else if(inputStatement.getDataType().equalsIgnoreCase("String")){
				declaredInputs.append("\tString "+ inputStatement.getDataName() + " = \" \";\n\t" +inputStatement.getDataName()+" = getValueFromSessionOnFind(\""+inputStatement.getDataName()+"\", \""+ inputStatement.getInitDataValue() +"\", inputElements);\n\n"); 
			}
		}
		return declaredInputs.toString();
	}

	 
	// This function writes to the dynamically generated file, all the variable(data statements) that are present in the script and initializes them to a default value.
	 public String displayVariables(String scriptName) {
		StringBuilder declaredDataVariables = new StringBuilder();
		List<Data> dataStatements = dataDao.getAllDeclarativeStatements(scriptName);
		for(Data dataStatement : dataStatements){
			if(dataStatement.getDataType().equalsIgnoreCase("int")){
				declaredDataVariables.append("\tint "+ dataStatement.getDataName() + " = 0;\n");
			}else if(dataStatement.getDataType().equalsIgnoreCase("float")){
				declaredDataVariables.append("\tfloat "+ dataStatement.getDataName() + " = 0.0;\n");
			}else if(dataStatement.getDataType().equalsIgnoreCase("boolean")){
				declaredDataVariables.append("\tboolean "+ dataStatement.getDataName() + " = false;\n");
			}else if(dataStatement.getDataType().equalsIgnoreCase("String")){
				declaredDataVariables.append("\tString "+ dataStatement.getDataName() + " = \"\";\n");
			}
		}
		return declaredDataVariables.toString();
	}

	/* This function writes to the file a function with parameters, which checks if the given variable 
	 is anywhere in the given session object list, if yes, then it assigns the variable the session value, else it assigns the given default value*/
	public String sessionChecker(){
		
		StringBuilder sessionFunction = new StringBuilder();
		sessionFunction.append("\t<%!\n\t\tpublic String getValueFromSessionOnFind(String inputVar, String defaultValue, HashMap<String, String> inputElements){\n");
		sessionFunction.append("\t\t\tif(inputElements.containsKey(inputVar)){\n");
		sessionFunction.append("\t\t\t\tSystem.out.println(\"Contains \"+ inputVar);\n");
		sessionFunction.append("\t\t\t\tinputVar = inputElements.get(inputVar);\n");
		sessionFunction.append("\t\t\t}else{\n");
		sessionFunction.append("\t\t\t\tinputVar = defaultValue;\n\t\t\t}\n\t\t\treturn inputVar;\n\t\t}\n\t%>");
				 
		return sessionFunction.toString();
	 }
		 
	//This writes to the file, inside the switch case, the various declarative statements
	 public String displayDeclarationStatement(int stmtId, String scriptName){
		 
		 StringBuilder declarativeStatement = new StringBuilder();
		 List<Data> dataStatements = dataDao.getAllDeclarativeStatements(scriptName);
		 for (Data dataStatement : dataStatements) {
			 if ((dataStatement.getStatementId() == stmtId) && (dataStatement.getDataType().equalsIgnoreCase("String"))){
				 	declarativeStatement.append("\t\t\t\t" + dataStatement.getDataName() + " = \"" + dataStatement.getInitDataValue() + "\";\n");
					declarativeStatement.append("\t\t\t\tSystem.out.println(\"Value of variable \" + " + dataStatement.getDataName() + ");\n");
					break;
				}else if(dataStatement.getStatementId() == stmtId){
					declarativeStatement.append("\t\t\t\t" + dataStatement.getDataName() + " = " + dataStatement.getInitDataValue() + ";\n");
					declarativeStatement.append("\t\t\t\tSystem.out.println(\"Value of variable \" + " + dataStatement.getDataName() + ");\n");
					break;
				}
			}
		 return declarativeStatement.toString();
		}
	 
	//This writes to the file, inside the switch case, the various operational statements
	 public String displayOperationStatement(int stmtId, String scriptName){
		 
		 StringBuilder operationStatement = new StringBuilder();	
		 List<Operation> operations = operationDao.getAllOperationStatements(scriptName);
		 for (Operation operation : operations) {
			 if ((operation.getStatementId() == stmtId) && (operation.getOperationType().getoType().equalsIgnoreCase("Binary"))){
				 operationStatement.append("\t\t\t\t" + operation.getResult().getDataName() + " = " + operation.getData1().getDataName() + " " + operation.getOperator1() + " " + operation.getData2().getDataName() + ";\n");
				 operationStatement.append("\t\t\t\tSystem.out.println(\"Value of resultant \" + " + operation.getResult().getDataName() + ");\n");
				 break;
			 }else if((operation.getStatementId() == stmtId) && (operation.getOperationType().getoType().equalsIgnoreCase("Assignment"))){
				 operationStatement.append("\t\t\t\t"+ operation.getData1().getDataName() + " = " + operation.getData2().getDataName() + ";\n");
				 operationStatement.append("\t\t\t\tSystem.out.println(\"Value of resultant \" + " + operation.getResult().getDataName() + ");\n");
				 break;
			 }else if((operation.getStatementId() == stmtId) && (operation.getOperationType().getoType().equalsIgnoreCase("Unary"))){
				 
			 }
			 
			 }
		 List<StringOperation> stringOperations = stringOperationDao.getAllStringOperationStatements(scriptName);
		 for(StringOperation stringOperation : stringOperations){
			 if (stringOperation.getStatementId() == stmtId){
				 if(stringOperation.getOperationType().getoType().equalsIgnoreCase("Substring")){
					 operationStatement.append("\t\t\t\t" + stringOperation.getResult().getDataName() + " = " + stringOperation.getData1().getDataName() + "." + stringOperation.getOperator1() + "(" + stringOperation.getIndex1() + "," + stringOperation.getIndex2() + ");\n");
					 operationStatement.append("\t\t\t\tSystem.out.println(\"Value of resultant \" + " + stringOperation.getResult().getDataName() + ");\n");
					 break;
				 }else if(stringOperation.getOperationType().getoType().equalsIgnoreCase("Concat")){
					 operationStatement.append("\t\t\t\t" + stringOperation.getResult().getDataName() + " = " + stringOperation.getData1().getDataName() + "." + stringOperation.getOperator1() + "(" + stringOperation.getData2().getDataName() + ");\n");
					 operationStatement.append("\t\t\t\tSystem.out.println(\"Value of resultant \" + " + stringOperation.getResult().getDataName() + ");\n");
					 break;
				 }
			 }
		 }
		 return operationStatement.toString();
	}
	 
	 @RequestMapping(value="/displayFinalValues")
	 public String displayFinalValues(){
		 
		 return "displayFinalValues";
	 }
	 
}
