package edu.neu.webapp.graphiccodegen.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.neu.webapp.graphiccodegen.services.CodeGenUtils;
import edu.neu.webapp.graphiccodegen.services.DataService;

@Controller
@SessionAttributes({"sessionScriptName", "sessionStatementType", "sessionVariableObjects"})
public class DataController {
	
	@Autowired
	private CodeGenUtils codeGenUtils;
	
	@Autowired
	private DataService dataService;
	
	
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
			
			String fileData = "<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>\n<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n\t<title>Final Variable Values</title>\n</head>\n<body>\n<a href=\"scriptstatementpage.html\">Previous Script</a>\n\t<table border=\"\">\n\t\t<tr>\n\t\t\t<th>Variable</th>\n\t\t\t<th>Value</th>\n\t\t\t<c:forEach var=\"dataStatement\" items=\"${sessionVariableObjects}\">\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>${dataStatement.getDataName()}</td>\n\t\t\t<td>${dataStatement.getFinalDataValue()}</td>\n\t\t</tr>\n\t\t\t</c:forEach>\n\t</table>\n</body>\n</html>";
			
			
			/*String fileData = "<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>\n<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n\t<title>Final Variable Values</title>\n</head>\n<body>\n\t<table border=\"\">\n\t\t<tr>\n\t\t\t<th>Variable</th>\n\t\t\t<th>Value</th>\n\t\t\t<c:forEach var=\"dataStatement\" items=\"${sessionVariableObjects}\">\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>${dataStatement.getDataName()}</td>\n\t\t\t<td>${dataStatement.getFinalDataValue()}</td>\n\t\t</tr>\n\t\t\t</c:forEach>\n\t</table>\n</body>\n</html>";
			
			<%
			int state = 1;
			boolean x = true;
			while(x){
			switch(state){
			while()
			case : 
			 
			}
			}
			*/
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
			bufferWritter.write(fileData);
			bufferWritter.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			codeGenUtils.renderPageValues(model);
		}
		 return "displayFinalValues";
	 }
	 
	 @RequestMapping(value="/displayFinalValues")
	 public String displayFinalValues(){
		 
		 return "displayFinalValues";
	 }
	 
}
