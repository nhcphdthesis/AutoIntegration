package model.process;

import java.util.ArrayList;

public class ReceiveTask extends Task {
	ArrayList<MessageFlow> inflows=new ArrayList<MessageFlow>();
	public ReceiveTask(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<MessageFlow> getInflows() {
		return inflows;
	}
	
}
