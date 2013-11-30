package model.process.interfacemodel;

import java.util.ArrayList;

public class Message {
    ArrayList<SemanticAnnotation> annotations=new ArrayList<SemanticAnnotation>();

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
