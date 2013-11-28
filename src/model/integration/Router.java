package model.integration;

import java.util.ArrayList;

import model.process.MessageFlow;

public class Router extends Mediator {
	ArrayList<MessageFlow> inFlows = new ArrayList<MessageFlow>();
	ArrayList<MessageFlow> outFlows = new ArrayList<MessageFlow>();
	int priority;
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public void addInFlow(MessageFlow mf){
		if (!inFlows.contains(mf)){
			inFlows.add(mf);
			mf.addRouter(this);
		}
	}
	public void addOutFlow(MessageFlow mf){
		if (!outFlows.contains(mf)){
			outFlows.add(mf);
			mf.addRouter(this);
		}
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Router [name=");
		builder.append(this.name);
		builder.append(", type=");
		builder.append(this.type);
		builder.append(", priority=");
		builder.append(this.priority);
//		builder.append(",Router [inFlows="+inFlows.size());
//		builder.append(", outFlows="+outFlows.size());
		builder.append("]");
		return builder.toString();
	}
	
	
}
