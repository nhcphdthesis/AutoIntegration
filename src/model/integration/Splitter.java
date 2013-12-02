package model.integration;

public class Splitter extends Router{
	String query = "";
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Splitter(){
		super();
		this.setType("Splitter");
		this.setName("auto-splitter");
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Splitter [query=");
		builder.append(query);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	
}
