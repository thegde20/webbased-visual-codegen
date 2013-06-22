package edu.neu.webapp.graphiccodegen.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: AppFlowStart
 *
 */
@Entity

public class AppFlowStart implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "aid")
	private Application application;
	
	
	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@OneToOne
	@JoinColumn(name="fid")
	private Flow flow;
	
	public AppFlowStart() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
   
}
