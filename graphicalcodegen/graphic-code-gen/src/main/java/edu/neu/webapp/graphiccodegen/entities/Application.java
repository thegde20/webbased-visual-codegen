package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the application database table.
 * 
 */
@Entity
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to Developer
	@ManyToOne
	@JoinColumn(name="devEmail")
	private Developer developer;

	//bi-directional many-to-one association to Flow
	@OneToMany(mappedBy="application")
	private List<Flow> flows =  new ArrayList<Flow>();

	public Application() {
	}

	public Application(String name,Developer d) {
		this.name=name;
		this.developer = d;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Developer getDeveloper() {
		return this.developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public List<Flow> getFlows() {
		return this.flows;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public Flow addFlow(Flow flow) {
		if(getFlows() == null){
			this.flows = new ArrayList<Flow>();
		}
		getFlows().add(flow);
		flow.setApplication(this);

		return flow;
	}

	public Flow removeFlow(Flow flow) {
		getFlows().remove(flow);
		flow.setApplication(null);

		return flow;
	}

}