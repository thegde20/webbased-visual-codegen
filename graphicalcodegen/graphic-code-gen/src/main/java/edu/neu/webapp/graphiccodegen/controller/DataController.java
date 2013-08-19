package edu.neu.webapp.graphiccodegen.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

@Controller
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
	 
	 @RequestMapping(value="/displayVariableValues")
	 public String displayDataValues(ModelMap model){
		 	try {
			// Shift to Service class		
			String scriptName = String.valueOf(model.get("sessionScriptName"));
			List<Statement> statements = statementDao.getAllStatements(scriptName);
			StringBuilder fileData = new StringBuilder();
			fileData.append("<%@page import=\"java.util.List\"%>\n" +
					"<%@page import =\"java.util.ArrayList\"%>\n" +
					"<%@page import=\"edu.neu.webapp.graphiccodegen.entities.*\"%>\n" +
					"<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>\n<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>" +
					"\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" +
					"\n\t<title>Final Values</title>\n</head>\n<body>\n\t<%\n");
			if (statements != null) {
				fileData.append("\tboolean x = true;\n" +
						"\tint state = " + statements.get(0).getStatementId() + ";\n" +
						"\t@SuppressWarnings(\"unchecked\")" +
						"\n\tList<Data> inputElements = (List<Data>) session.getAttribute(\"sessionInputVariables\");\n" 
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
						"\t\t\t\tbreak;\n\t\t}\n\t}\n" + updateSessionVariable(scriptName) +
								"\tsession.setAttribute(\"sessionVariableObjects\", dataElements);\n" +
								"\n\t%>\n");
			}
			else{
				fileData.append("System.out.println(\"No Statements!\n\");");
			}
			fileData.append("</body>\n</html>");
	
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL url = classLoader.getResource("");
			String path = url.getPath().substring(1, url.getPath().length()-8);
			System.out.println(path);
			File jspFile = new File(path+"displayFinalValues.jsp");
			
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
			codeGenUtils.renderPageValues(model);
		}
		 return "displayFinalValues";
	 }
	 
	 // This function puts all the Data variables(which are likely to change) into the session
	 public String updateSessionVariable(String scriptName){
		StringBuilder updateVariables = new StringBuilder();
		List<Data> dataStatements = dataDao.getAllDeclarativeStatements(scriptName);
		updateVariables.append("\t@SuppressWarnings(\"unchecked\")" +
						"\n\tList<Data> dataElements = (List<Data>) session.getAttribute(\"sessionVariableObjects\");\n" );
		for(Data dataStatement : dataStatements){
			updateVariables.append("\tfor(int i = 0; i<dataElements.size(); i++){\n" +
					"\t\tif(dataElements.get(i).getDataName().equalsIgnoreCase(\"" + dataStatement.getDataName() + "\")){\n" +
	 				"\t\t\tdataElements.get(i).setInitDataValue(String.valueOf("+ dataStatement.getDataName() +"));\n" +
	 						"\t\t\tbreak;}}\n");
		}
				
		return updateVariables.toString();
	}

	/*This function writes to the dynamically generated file, all the input variables that are present in the given script and also calls a function to check if the
	input variable value is present in the session, if yes, then initializes it to the value in the session.*/
	 public String displayInputVariables(String scriptName) {
	
		StringBuilder declaredInputs = new StringBuilder();
		List<Data> inputStatements = dataDao.getAllInputStatements(scriptName);
		for(Data inputStatement : inputStatements){
			if(inputStatement.getDataType().equalsIgnoreCase("int")){
				declaredInputs.append("\tint "+ inputStatement.getDataName() + " = "+inputStatement.getInitDataValue()+";\n" + checkInSession(inputStatement.getDataName(), "Integer.parseInt"));
			}else if(inputStatement.getDataType().equalsIgnoreCase("float")){
				declaredInputs.append("\tfloat "+ inputStatement.getDataName() + " = "+inputStatement.getInitDataValue()+";\n" + checkInSession(inputStatement.getDataName(), "Float.parseFloat"));
			}else if(inputStatement.getDataType().equalsIgnoreCase("boolean")){
				declaredInputs.append("\tboolean "+ inputStatement.getDataName() + " = "+inputStatement.getInitDataValue()+";\n" + checkInSession(inputStatement.getDataName(), "Boolean.parseBoolean"));
			}else if(inputStatement.getDataType().equalsIgnoreCase("String")){
				declaredInputs.append("\tString "+ inputStatement.getDataName() + " = \""+inputStatement.getInitDataValue()+"\";\n" + checkInSession(inputStatement.getDataName(), ""));
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

	// This function writes to the file a for loop, which checks if the variables are anywhere in the session, if yes, then it assigns the variable the session value
	public String checkInSession(String varName, String parserType){
		 StringBuilder intVariables = new StringBuilder();
		 intVariables.append("\tfor(int i = 0; i<inputElements.size(); i++){\n" +
					 "\t\tif(inputElements.get(i).getDataName().equalsIgnoreCase(\"" + varName + "\")){\n" +					 
					 "\t\t\t" + varName + " = " + parserType+ "(inputElements.get(i).getInitDataValue().toString());\n\t\t\tbreak;\n\t\t}\n\t}\n");		 
		 return intVariables.toString();
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
			 }/*else if((operation.getStatementId() == stmtId) && (operation.getOperationType().getoType().equalsIgnoreCase("Substring"))){
				 operationStatement.append("\t\t\t\t" + operation.getResult().getDataName() + " = " + operation.getData1().getDataName() + " " + operation.getOperator1() + " " + operation.getData2().getDataName() + ";\n");
				 operationStatement.append("\t\t\t\tSystem.out.println(\"Value of resultant \" + " + operation.getResult().getDataName() + ");\n");*/
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
