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
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.dao.DataDao;
import edu.neu.webapp.graphiccodegen.dao.OperationDao;
import edu.neu.webapp.graphiccodegen.dao.StatementDao;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.Operation;
import edu.neu.webapp.graphiccodegen.entities.Statement;
import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.DataService;

@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType", "sessionVariableObjects"})
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
	 
	 @RequestMapping(value="/scriptstatementpage")
	public String renderMainStatements(ModelMap model,HttpServletRequest request) {
		
		codeGenUtils.renderPageValues(model);
		return "scriptstatementpage";
		
	}
	 
	 
	 @RequestMapping(value="/displayVariableValues")
	 public String displayDataValues(ModelMap model){
		 
		try {
			// Shift to Service class
			//String fileData = "<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>\n<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n\t<title>Final Variable Values</title>\n</head>\n<body>\n<a href=\"scriptstatementpage.html\">Previous Script</a>\n\t<table border=\"\">\n\t\t<tr>\n\t\t\t<th>Variable</th>\n\t\t\t<th>Value</th>\n\t\t\t<c:forEach var=\"dataStatement\" items=\"${sessionVariableObjects}\">\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>${dataStatement.getDataName()}</td>\n\t\t\t<td>${dataStatement.getFinalDataValue()}</td>\n\t\t</tr>\n\t\t\t</c:forEach>\n\t</table>\n</body>\n</html>";
			//codeGenUtils.renderPageValues(model);
			
			String scriptName = String.valueOf(model.get("sessionScriptName"));
			
			List<Statement> statements = statementDao.getAllStatements(scriptName);
			
			StringBuilder fileData = new StringBuilder();
			
			fileData.append("<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>\n<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>" +
					"\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" +
					"\n\t<title>Final Values</title>\n</head>\n<body>\n\t<%\n");
			if (statements != null) {
				fileData.append("\tboolean x = true;\n" +
						"\tint state = " + statements.get(0).getStatementId() + ";\n" +
						"\tint " + displayIntVariables(scriptName) + ";\n" +
						"\twhile(x){\n" +
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
						"\t\t\t\tbreak;\n\t\t}\n\t}\n\t%>\n");
			}
			else{
				fileData.append("System.out.println(\"No Statements!\n\");");
			}
		/*	fileData.append("\t<table border=\"\">\n" +
					"\t\t<tr>\n" +
					"\t\t\t<th>Variable</th>\n" +
					"\t\t\t<th>Value</th>\n" +
					"\t\t\t");*/
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
	 
	 public String displayIntVariables(String scriptName){
		 
		 StringBuilder intVariables = new StringBuilder();
		 List<Data> dataStatements = dataDao.getAllDataStatements(scriptName);
		 for (Data dataStatement : dataStatements){
			 intVariables.append(dataStatement.getDataName() + " = " + dataStatement.getInitDataValue());
			 if (dataStatements.indexOf(dataStatement)<dataStatements.size()-1){
				 intVariables.append(", ");
			 }
		 }
		 
		 return intVariables.toString();
	 }
	 
	 public String displayDeclarationStatement(int stmtId, String scriptName){
		 
		 StringBuilder declarativeStatement = new StringBuilder();
		 List<Data> dataStatements = dataDao.getAllDataStatements(scriptName);
		 for (Data dataStatement : dataStatements) {
			 if (dataStatement.getStatementId() == stmtId){
					//declarativeStatement.append(dataStatement.getDataType() + " " + dataStatement.getDataName() + " = " + dataStatement.getInitDataValue() + ";\n");
				 	declarativeStatement.append("\t\t\t\t" + dataStatement.getDataName() + " = " + dataStatement.getInitDataValue() + ";\n");
					declarativeStatement.append("\t\t\t\tSystem.out.println(\"Value of variable \" + " + dataStatement.getDataName() + ");\n");
				}else{
					declarativeStatement.append("");
				}
			}
		 return declarativeStatement.toString();
		}
	 
	 public String displayOperationStatement(int stmtId, String scriptName){
		 
		 StringBuilder operationStatement = new StringBuilder();	
		 List<Operation> operations = operationDao.getAllOperationStatements(scriptName);
		 for (Operation operation : operations) {
			 if ((operation.getStatementId() == stmtId) && (operation.getOperationType().getoType().equalsIgnoreCase("Binary"))){
				 operationStatement.append("\t\t\t\t" + operation.getResult().getDataName() + " = " + operation.getData1().getDataName() + " " + operation.getOperator1() + " " + operation.getData2().getDataName() + ";\n");
				 operationStatement.append("\t\t\t\tSystem.out.println(\"Value of resultant \" + " + operation.getResult().getDataName() + ");\n");
			 }else{
				 operationStatement.append("");
			 }
		 }
		 return operationStatement.toString();
	}
	 
	 @RequestMapping(value="/displayFinalValues")
	 public String displayFinalValues(){
		 
		 return "displayFinalValues";
	 }
	 
}
