package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the flow database table.
 * 
 */
@Entity
public class Flow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	private String name;

	//bi-directional many-to-one association to Application
	@ManyToOne
	@JoinColumn(name="appID")
	private Application application;

	//bi-directional many-to-one association to Flow
	@ManyToOne
	@JoinColumn(name="flowID")
	private Flow parentFlow;

	public Flow getParentFlow() {
		return parentFlow;
	}

	public void setParentFlow(Flow parentFlow) {
		this.parentFlow = parentFlow;
	}

	public List<Flow> getChildrenFlows() {
		return childrenFlows;
	}

	public void setChildrenFlows(List<Flow> childrenFlows) {
		this.childrenFlows = childrenFlows;
	}

	//bi-directional many-to-one association to Flow
	@OneToMany(mappedBy="parentFlow")
	private List<Flow> childrenFlows;

	//bi-directional many-to-one association to Node
	@OneToMany(mappedBy="flow")
	private List<Node> nodes;

	public Flow() {
	}

	public Flow(String name,String desc, Application app,ArrayList<Node> nodes) {
		this.name= name;
		this.description = desc;
		this.application = app;
		this.nodes = nodes;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Flow getFlow() {
		return this.parentFlow;
	}

	public void setFlow(Flow flow) {
		this.parentFlow = flow;
	}

	public List<Flow> getFlows() {
		return this.childrenFlows;
	}

	public void setFlows(List<Flow> flows) {
		this.childrenFlows = flows;
	}

	public Flow addFlow(Flow flow) {
		getFlows().add(flow);
		flow.setFlow(this);

		return flow;
	}

	public Flow removeFlow(Flow flow) {
		getFlows().remove(flow);
		flow.setFlow(null);

		return flow;
	}

	public List<Node> getNodes() {
		return this.nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Node addNode(Node node) {
		getNodes().add(node);
		node.setFlow(this);

		return node;
	}

	public Node removeNode(Node node) {
		getNodes().remove(node);
		node.setFlow(null);

		return node;
	}

}