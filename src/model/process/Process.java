package model.process;

import java.util.ArrayList;

public class Process {
	String name;
	ArrayList<Task> tasks = new ArrayList<Task>();
	ArrayList<Relation> relations = new ArrayList<Relation>();
	public ArrayList<Relation> getRelations() {
		return relations;
	}

	public Process(String name) {
		super();
		this.name = name;
	}
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	public void addTask(Task t){
		if(!this.tasks.contains(t)){
			this.tasks.add(t);
			t.setProcess(this);
		}
	}
	//retrieve a task using a given name
	public Task getTask(String taskName){
		for (Task t : this.tasks){
			if (t.getName().equalsIgnoreCase(taskName)){
				return t;
			}
		}
		return null;
	}
	public ArrayList<SendTask> getSendTasks(){
		ArrayList<SendTask> result = new ArrayList<SendTask>();
		for (Task t: this.tasks){
			if (t instanceof SendTask){
				result.add((SendTask)t);
			}
		}
		return result;
	}
	public ArrayList<ReceiveTask> getReceiveTasks(){
		ArrayList<ReceiveTask> result = new ArrayList<ReceiveTask>();
		for (Task t: this.tasks){
			if (t instanceof ReceiveTask){
				result.add((ReceiveTask)t);
			}
		}
		return result;
	}
	//determine if two tasks in a process has sequence relation
	public boolean seq(Task t1,Task t2){
		for(Relation rel:this.getRelations()){
			if(rel.getType().equalsIgnoreCase("seq")){
				if(rel.getTasks().contains(t1)&&rel.getTasks().contains(t2)){
					if(rel.getTasks().indexOf(t1)<rel.getTasks().indexOf(t2)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void assertSeq(Task t1, Task t2){
		Relation seq = new Relation();
		seq.setType("seq");
		seq.tasks.add(t1);
		seq.tasks.add(t2);
		this.relations.add(seq);
	}
	public void assertExc(Task t1, Task t2){
		Relation seq = new Relation();
		seq.setType("exc");
		seq.tasks.add(t1);
		seq.tasks.add(t2);
		this.relations.add(seq);
	}
	public boolean exc(ReceiveTask t1, ReceiveTask t2) {
		for(Relation rel:this.getRelations()){
			if(rel.getType().equalsIgnoreCase("exc")){
				if(rel.getTasks().contains(t1)&&rel.getTasks().contains(t2)){
						return true;
				}
			}
		}
		return false;
	}
}
