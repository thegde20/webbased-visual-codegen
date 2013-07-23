package edu.neu.webapp.graphiccodegen.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.neu.webapp.graphiccodegen.dao.EventDao;
import edu.neu.webapp.graphiccodegen.dao.RunAppDao;
import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;
import edu.neu.webapp.graphiccodegen.services.ApplicationService;
import edu.neu.webapp.graphiccodegen.services.FlowService;

@Controller
@Path("/publish")
public class PublishAppController {

	@Autowired
	private RunAppDao runAppDao;
	@Autowired
	private ApplicationService appService;
	@Autowired
	FlowService flowService;

	@Autowired
    private ServletContext servletContext;
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private EventDao eventDao;

	// @RequestMapping(value = "/publishApp")
	@GET
	@Path("/{appId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void publishApp(@PathParam("appId") String appID) throws IOException {
		System.out.println("----"+servletContext.getContextPath());
		Application a = appService.detailsApplicationService(Integer
				.parseInt(appID));
		if (a != null) {
			File dir = new File(System.getProperty("wtp.deploy")+"/"+request.getContextPath() , a.getName());
			File startFlowDir = new File(dir.getAbsolutePath(), "startFlow");
			// folders have to replaced at each publish
			startFlowDir.mkdirs();
			if (a.getFlows() != null) {
				flowService.getSubFlows(a.getFlows());
				flowService.getNodesForFlow(a.getFlows());
				for (Flow f : a.getFlows()) {

					if (f.getParentFlow() == null) {
						makeEachFlowDir(startFlowDir, f);
						// makeSubFlowDirs(dir,f);
					}

				}
			}

		}
	}

	public void makeSubFlowDirs(List<Flow> flows, File dir) {
		if (flows != null && flows.size() != 0) {
			flowService.getSubFlows(flows);
			for (Flow f : flows) {
				makeEachFlowDir(dir, f);
			}

		}

	}

	public void makeEachFlowDir(File d, Flow f) {

		File flowdir = new File(d.getAbsolutePath(), f.getName());
		if (!flowdir.exists()) {
			flowdir.mkdir();
		}
		createModulesInDir(f, flowdir);
		makeSubFlowDirs(f.getChildrenFlows(), flowdir);

	}

	public void createModulesInDir(Flow f, File dir) {
		System.out.println("in create modules");
		if (f.getNodes() != null) {
			for (Node nd : f.getNodes()) {
				System.out.println("nodes");
				if ("IO".equalsIgnoreCase(nd.getType())) {
					System.out.println("IO Node");
					// ajax call to prof
					URL url;
					String reqURL = "http://"+request.getServerName()+":"+request.getServerPort();
					try {
					url = new URL(reqURL+servletContext.getContextPath()+"/rest/publish/writeFile");
					System.out.println("URL--"+url);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
			 
					String input = "path="+dir.getPath();
			 
					OutputStream os = conn.getOutputStream();
					os.write(input.getBytes());
					os.flush();
			 
					if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
						throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
					}
			 
					BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));
			 
					String output;
					System.out.println("Output from Server .... \n");
					while ((output = br.readLine()) != null) {
						System.out.println(output);
					}
			 
					conn.disconnect();} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("Script".equalsIgnoreCase(nd.getType())) {
					// ajax call to tej

				}
			}
		}
	}
	
	@POST
	@Path("/writeFile")
	@Consumes(MediaType.APPLICATION_JSON)
	public void writeFileTest(String path) {
		System.out.println("path="+path);
	
	}
	
}
