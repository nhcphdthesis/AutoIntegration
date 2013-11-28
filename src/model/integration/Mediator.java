package model.integration;

import java.util.ArrayList;

import model.process.MessageFlow;

public class Mediator extends EIPComponent {
	String name = "";
	String type;
	ArrayList<MessageFlow> messageflows = new ArrayList<MessageFlow>();
	public ArrayList<MessageFlow> getMessageflows() {
		return messageflows;
	}
	public void setMessageflows(ArrayList<MessageFlow> messageflows) {
		this.messageflows = messageflows;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mediator [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	
	
}
