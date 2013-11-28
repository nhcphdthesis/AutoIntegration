package model.integration;

import java.util.ArrayList;

public class Channel extends EIPComponent{
	String name;
	Mediator src;
	public Mediator getSrcFilter() {
		return src;
	}
	public void setSrcFilter(Mediator from) {
		this.src = from;
	}
	public Mediator getTgtFilter() {
		return tgt;
	}
	public void setTgtFilter(Mediator to) {
		this.tgt = to;
	}
	Mediator tgt;
	ArrayList<Mediator> mediator = new ArrayList<Mediator>();
	ArrayList<EndpointConnector> inbounds = new ArrayList<EndpointConnector>();
	ArrayList<EndpointConnector> outbounds = new ArrayList<EndpointConnector>();
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Channel: "+name+" [from=");
		builder.append(src==null?"null":src.getName());
		builder.append(", to=");
		builder.append(tgt==null?"null":tgt.getName());
		builder.append("]\n");
		return builder.toString();
	}
	public ArrayList<Mediator> getMediator() {
		return mediator;
	}
	public ArrayList<EndpointConnector> getInbounds() {
		return inbounds;
	}
	public ArrayList<EndpointConnector> getOutbounds() {
		return outbounds;
	}
	public Channel(String name) {
		super();
		this.name = name;
	}

	
	
	
}
