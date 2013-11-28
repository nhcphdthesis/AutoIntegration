package model.process;

import model.process.interfacemodel.Operation;



public class Task {
	String name;
	Operation operation;
	Process process;
	boolean isLoop = false;
	String loopCondition;
	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	boolean isSend(){
		return this instanceof SendTask;
	}
	boolean isReceive(){
		return this instanceof ReceiveTask;
	}

	public Task(String name) {
		super();
		this.name = name;
		this.operation = new Operation();
		this.operation.setName("DefaultOperation: "+name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	
	
}
