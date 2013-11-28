package model.process;

import java.util.ArrayList;

public class SendTask extends Task {

	ArrayList<MessageFlow> outflows=new ArrayList<MessageFlow>();
	public SendTask(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<MessageFlow> getOutflows() {
		return outflows;
	}
	
}
