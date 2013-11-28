package model.integration;

import model.process.interfacemodel.Operation;

public class EndpointConnector extends Mediator {
	String URI;
	Operation operation;
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EndpointConnector [URI=");
		builder.append(URI);
		builder.append(", operation=");
		builder.append(operation==null?"dummy":operation.getName());
		builder.append("]");
		return builder.toString();
	}
	public EndpointConnector(Operation op) {
		super();
		this.setName("connector-"+((op!=null)?op.getName():"empty-operation"));
		
		this.URI = "";// (op!=null)?op.getInterfce().getEndpoint().getURI():"empty-URI";
		this.operation = op;
	}
	public EndpointConnector(String string){
		super();
		this.setName(string);
		this.URI = string;
		this.operation = null;
	}
	
	
}
