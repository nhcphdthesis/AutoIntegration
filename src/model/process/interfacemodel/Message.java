package model.process.interfacemodel;

import java.util.ArrayList;

public class Message {
    ArrayList<SemanticAnnotation> annotations=new ArrayList<SemanticAnnotation>();
    String schemaRef = "";
    String semanticRef = "";
    public String getSemanticRef() {
		return semanticRef;
	}

	public void setSemanticRef(String semanticRef) {
		this.semanticRef = semanticRef;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	String name = "";

	public ArrayList<SemanticAnnotation> getAnnotations() {
		return annotations;
	}
	
	public void addAnnotation(SemanticAnnotation sa){
		for (SemanticAnnotation anno : annotations){
			if (anno.getDataElementRef().equals(sa.getDataElementRef())&&anno.getConceptRef().equals(sa.getConceptRef())){
				return;
			}
		}
		System.out.println("adding annotation from "+sa.getDataElementRef() +" to "+sa.getConceptRef());
		annotations.add(sa);
	}
}
