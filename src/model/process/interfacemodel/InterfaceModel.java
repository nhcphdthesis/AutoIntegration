package model.process.interfacemodel;

import java.util.ArrayList;

public class InterfaceModel {
	Endpoint endpoint;
	ArrayList<Operation> operations = new ArrayList<Operation>();
	public Endpoint getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}
	public ArrayList<Operation> getOperations() {
		return operations;
	}
	public void setOperations(ArrayList<Operation> operations) {
		this.operations = operations;
	}
	
	
}
