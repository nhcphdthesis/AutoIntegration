package model.integration;

import java.util.ArrayList;

import model.process.interfacemodel.Message;

public class Translator extends Mediator {
	ArrayList<String> mappingDefs = new ArrayList<String>();
	Message sourceMessage;
	Message targetMessage;
	
	public void addMappingDef(String mapping){
		if (!mappingDefs.contains(mapping)){
			mappingDefs.add(mapping);
		}
	}
}
