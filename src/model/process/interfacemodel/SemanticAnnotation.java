package model.process.interfacemodel;

public class SemanticAnnotation {
	String dataElementRef;
	String conceptRef;
	public String getDataElementRef() {
		return dataElementRef;
	}
	public void setDataElementRef(String dataElementRef) {
		this.dataElementRef = dataElementRef;
	}
	public String getConceptRef() {
		return conceptRef;
	}
	public void setConceptRef(String conceptRef) {
		this.conceptRef = conceptRef;
	}
	public SemanticAnnotation(String dataElementRef, String conceptRef) {
		super();
		this.dataElementRef = dataElementRef!=null?dataElementRef:"";
		this.conceptRef = conceptRef!=null?conceptRef:"";
	}
	
}
