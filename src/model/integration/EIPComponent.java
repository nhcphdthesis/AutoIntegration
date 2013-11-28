package model.integration;

public class EIPComponent {
	String name;
	EIPComponent next;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Component [name=");
		builder.append(name);
		builder.append(", next=");
		builder.append(next!=null?next.name:"null");
		builder.append("]");
		return builder.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EIPComponent getNext() {
		return next;
	}
	public void setNext(EIPComponent next) {
		this.next = next;
	}
	
	
}
