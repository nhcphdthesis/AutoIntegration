package model.process;

import java.util.ArrayList;

public class Relation {
	String name;
	String type;//"seq","par","exc"
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	ArrayList<Task> tasks = new ArrayList<Task>();
	public ArrayList<Task> getTasks() {
		return tasks;
	}
}
