package edu.neu.webapp.graphiccodegen.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.webapp.graphiccodegen.dao.ApplicationDao;
import edu.neu.webapp.graphiccodegen.dao.EventDao;
import edu.neu.webapp.graphiccodegen.dao.FlowDao;
import edu.neu.webapp.graphiccodegen.dao.NodeDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Data;
import edu.neu.webapp.graphiccodegen.entities.Event;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;

@Controller
@Path("/run")
public class RunAppController {

	@Autowired
	private FlowDao flowDao;
	@Autowired
	private ApplicationDao appDao;
	@Autowired
	private NodeDao nodeDao;

	@Autowired
	private EventDao eventDao;

	@RequestMapping(value = "/runApp")
	public void runApp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String returnPage = "";

		String appID = request.getParameter("appId");
		//System.out.println("run app----"+appID);
		if (appID != null) {
			Application app = appDao.getApplication(Integer.parseInt(appID));
			returnPage = app.getName() + "/startFlow/index.jsp";
			System.out.println("returnPage"+returnPage);
		}
		request.getRequestDispatcher(returnPage).forward(request, response);
	}

	// @RequestMapping
	@RequestMapping(value = "/renderNextPage")
	public void nextEvent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String eventLabel = request.getParameter("eventLabel");
		Node sessionNodeSrc =
				(Node) request.getSession().getAttribute("currentNode");
		//if node is of type i/o take from request and set to session
		HashMap<String,String> sessionInputVariables = new HashMap<String, String>();
		if(sessionNodeSrc.getType() == "IO"){
			Map params = request.getParameterMap();
			java.util.Iterator i = params.keySet().iterator();
			List<Data> sessionData= new ArrayList<Data>();
			while ( i.hasNext() )
			{
			String key = (String) i.next();
			String value = ((String[]) params.get( key ))[ 0 ];
			//Data d= new Data();
			sessionInputVariables.put(key, value);
			}
			request.getSession().setAttribute("sessionInputVariables", sessionInputVariables);
		}
		String forwardUrl = null;
		if (eventLabel != null) {
			Event e = eventDao.getEventByLabel(eventLabel);
			if (e != null) {
				Node nodeTgt = e.getNodeTarget();
				request.getSession().setAttribute("currentNode",nodeTgt);
				if (nodeTgt.getFlow() != null) {
					forwardUrl = nodeTgt.getName() + ".jsp";
					formUrl(nodeTgt.getFlow(), forwardUrl);
				request.getSession().setAttribute("currentNode",nodeTgt);
				}
			}
		}
		if (forwardUrl != null) {
			request.getRequestDispatcher(forwardUrl).forward(request, response);
		} else {
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		}

	}

	public String formUrl(Flow nodeFlow, String fwdUrl) {
		if (nodeFlow != null) {
			String prePendUrl = nodeFlow.getName() + "/" + fwdUrl;
			formUrl(nodeFlow.getParentFlow(), prePendUrl);
		}
		return fwdUrl;
	}
}
