package model.process.interfacemodel;

public class Operation {
 InterfaceModel interfce;
 String name;
 Message input;
 
 public Operation(){
	 name = "";
	 input = new Message();
	 output = new Message();
 }
 
 public Message getInput() {
	return input;
}
public void setInput(Message input) {
	this.input = input;
}
public Message getOutput() {
	return output;
}
public void setOutput(Message output) {
	this.output = output;
}
Message output;
public InterfaceModel getInterfce() {
	return interfce;
}
public void setInterfce(InterfaceModel interfce) {
	this.interfce = interfce;
	interfce.getOperations().add(this);
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
 
 
}
