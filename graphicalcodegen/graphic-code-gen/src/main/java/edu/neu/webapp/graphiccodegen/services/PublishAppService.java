package edu.neu.webapp.graphiccodegen.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import edu.neu.webapp.graphiccodegen.entities.Application;
import edu.neu.webapp.graphiccodegen.entities.Flow;
import edu.neu.webapp.graphiccodegen.entities.Node;

public class PublishAppService {

	@Autowired
	FlowService flowService;
	@Autowired
	private ApplicationService appService;

	@Resource(name = "myProperties")
	private Properties myProperties;

	public void createDirs(ServletContext servletContext, String appID,
			HttpServletRequest request) throws IOException {

		System.out.println("----" + servletContext.getContextPath());
		Application a = appService.detailsApplicationService(Integer
				.parseInt(appID));
		System.out.println("servlet real path---"
				+ servletContext.getRealPath("/"));
		if (a != null) {
			File dir = new File(servletContext.getRealPath("/"), a.getName());
			File startFlowDir = new File(dir, "startFlow");
			// folders have to replaced at each publish
			startFlowDir.mkdirs();
			if (a.getFlows() != null) {
				flowService.getSubFlows(a.getFlows());
				flowService.getNodesForFlow(a.getFlows());
				for (Flow f : a.getFlows()) {

					if (f.getParentFlow() == null) {
						makeEachFlowDir(startFlowDir, f, servletContext,
								request);
						// makeSubFlowDirs(dir,f);
						// startFlowDir.mkdirs();
					}

				}
			}

		}

	}

	public void makeSubFlowDirs(List<Flow> flows, File dir,
			ServletContext context, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		if (flows != null && flows.size() != 0) {
			flowService.getSubFlows(flows);
			for (Flow f : flows) {
				makeEachFlowDir(dir, f, context, request);
			}

		}

	}

	public void makeEachFlowDir(File d, Flow f, ServletContext context,
			HttpServletRequest request) throws FileNotFoundException,
			IOException {

		File flowdir = new File(d.getAbsolutePath(), f.getName());
		if (!flowdir.exists()) {
			flowdir.mkdirs();
		}
		createModulesInDir(f, flowdir, context, request);
		makeSubFlowDirs(f.getChildrenFlows(), flowdir, context, request);

	}

	public void createModulesInDir(Flow f, File dir,
			ServletContext servletContext, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		System.out.println("in create modules");
		if (f.getNodes() != null) {
			for (Node nd : f.getNodes()) {
				System.out.println("nodes" + nd.getType());
				if ("IO".equalsIgnoreCase(nd.getType())) {
					System.out.println("IO Node");
					// load a properties file
					System.out.println("-----"
							+ myProperties.getProperty("host"));
					// ajax call to prof

					URL url;
					String reqURL = "http://" + request.getServerName() + ":"
							+ request.getServerPort();
					try {
						url = new URL(reqURL + servletContext.getContextPath()
								+ "/rest/publish/writeFile");
						System.out.println("URL--" + url);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setDoOutput(true);
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type",
								"application/json");

						String input = "scriptName=" + nd.getName()
								+ "filePath=" + dir.getPath();

						java.io.OutputStream os = conn.getOutputStream();
						os.write(input.getBytes());
						os.flush();

						if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
							throw new RuntimeException(
									"Failed : HTTP error code : "
											+ conn.getResponseCode());
						}

						BufferedReader br = new BufferedReader(
								new InputStreamReader((conn.getInputStream())));

						String output;
						System.out.println("Output from Server .... \n");
						while ((output = br.readLine()) != null) {
							System.out.println(output);
						}

						conn.disconnect();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if ("Script".equalsIgnoreCase(nd.getType())) {
					System.out.println("script nodes----");
					try {

						String reqURL = "http://" + request.getServerName()
								+ ":" + request.getServerPort();
						String input = "scriptName=" + nd.getName()
								+ "&filePath=" + dir.getPath();
						URL url = new URL(reqURL
								+ servletContext.getContextPath()
								+ "/rest/data/post?" + input);
						System.out.println("URL--" + url);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setDoOutput(true);
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type",
								"application/json");

						if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
							throw new RuntimeException(
									"Failed : HTTP error code : "
											+ conn.getResponseCode());
						}

						BufferedReader br = new BufferedReader(
								new InputStreamReader((conn.getInputStream())));

						String output;
						System.out.println("Output from Server .... \n");
						while ((output = br.readLine()) != null) {
							System.out.println(output);
						}

						conn.disconnect();

					} catch (MalformedURLException e) {

						e.printStackTrace();

					} catch (IOException e) {

						e.printStackTrace();

					}
				}
			}
		}
	}

}
