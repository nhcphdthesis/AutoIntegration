package model.process;

import java.util.ArrayList;
import java.util.Collections;

import model.integration.Channel;
import model.integration.Router;

public class MessageFlow {
	String name;
	Channel inbound_channel;
	ArrayList<Router> routers=new ArrayList<Router>();
	public ArrayList<Router> getRouters() {
		return routers;
	}
	public void addRouter(Router r){
		if (r!=null&&!routers.contains(r)){
			routers.add(r);
		}
	}
	public Channel getInbound_channel() {
		return inbound_channel;
	}

	public void setInbound_channel(Channel inbound_channel) {
		this.inbound_channel = inbound_channel;
	}

	public Channel getOutbound_channel() {
		return outbound_channel;
	}

	public void setOutbound_channel(Channel outbound_channel) {
		this.outbound_channel = outbound_channel;
	}
	Channel outbound_channel;
	ArrayList<Channel> intermediate_channels = new ArrayList<Channel>();
	public ArrayList<Channel> getIntermediateChannels() {
		return intermediate_channels;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	SendTask source;
	ReceiveTask target;
	public boolean checked = false;
	public MessageFlow(String name, SendTask source, ReceiveTask target) {
		super();
		this.name = name;
		setSource(source);
		setTarget(target);
	}
	public SendTask getSource() {
		return source;
	}
	public void setSource(SendTask source) {
		this.source = source;
		source.getOutflows().add(this);
	}
	public ReceiveTask getTarget() {
		return target;
	}
	public void setTarget(ReceiveTask target) {
		this.target = target;
		target.getInflows().add(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageFlow [name=");
		builder.append(name);
		builder.append(", channels=\n");
		builder.append(intermediate_channels);
		builder.append("\n, source=");
		builder.append(source);
		builder.append(", target=");
		builder.append(target);
		builder.append(", checked=");
		builder.append(checked);
		builder.append("]");
		return builder.toString();
	}
	
	public void sortRouters(){
		Collections.sort(this.routers, new RouterComparator());
	}
}
