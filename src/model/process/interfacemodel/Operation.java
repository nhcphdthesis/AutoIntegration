package model.process.interfacemodel;

public class Operation {
 InterfaceModel interfce;
 String name;
 Message input;
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
