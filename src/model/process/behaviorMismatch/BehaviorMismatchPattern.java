package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public abstract class BehaviorMismatchPattern {
	ArrayList<MessageFlow> flows=new ArrayList<MessageFlow>();
	public ArrayList<MessageFlow> getFlows() {
		return flows;
	}
	public void setFlows(ArrayList<MessageFlow> flows) {
		this.flows = flows;
	}
	ArrayList<Router> routers= new ArrayList<Router>();
	public abstract ArrayList<Router> getMediator();

}
