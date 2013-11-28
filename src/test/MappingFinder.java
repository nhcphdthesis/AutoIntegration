package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.process.interfacemodel.Message;
import model.process.interfacemodel.SemanticAnnotation;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class MappingFinder {
	private static final String INSTANCE_URI = "http://nhc.org/ontologies/ReportInstance.owl";
	private static final String MAPPING_URI = "http://nhc.org/ontologies/ReportMapping.owl";
	private static final String HL7_URI = "http://nhc.org/ontologies/HL7Ontology.owl";
	private static final String HL7_NS = HL7_URI +"#";
	private static final String CIS_URI = "http://nhc.org/ontologies/LocalOntology.owl";
	private static final String CIS_NS = CIS_URI +"#";
	static final String MAPPING_FILE_NAME = "C:\\Users\\nhc\\Dropbox\\我的论文\\PhD论文\\集成语义\\ReportMappingRDF.owl";
	static final String HL7_FILE_NAME = "C:\\Users\\nhc\\Dropbox\\我的论文\\PhD论文\\集成语义\\HL7OntologyRDF.owl";
	private static final String MODEL_FILE_NAME = "C:\\Users\\nhc\\Dropbox\\我的论文\\PhD论文\\集成语义\\ReportInstanceRDF.owl";
	private static final String CIS_FILE_NAME = "C:\\Users\\nhc\\Dropbox\\我的论文\\PhD论文\\集成语义\\LocalOntologyRDF.owl";
	private static final String FOLDER = "C:\\Users\\nhc\\Dropbox\\我的论文\\PhD论文\\集成语义\\";
	
	OntModel model;
	
	protected static MappingFinder instance;
	public static MappingFinder getInstance(){
		if (instance==null){
			instance = new MappingFinder();
		}
		return instance;
	}
	
	public MappingFinder(){
		InputStream in, in2, in3;
		try {
			in = new FileInputStream(new File(HL7_FILE_NAME));
//			model.read(in,null); // null base URI, since model URIs are absolute
			in2 = new FileInputStream(new File(MAPPING_FILE_NAME));
			in3 = new FileInputStream(new File(CIS_FILE_NAME));
			model.read(in, null);

			model.add(ModelFactory.createDefaultModel().read(in2,null));
			model.add(ModelFactory.createDefaultModel().read(in3,null));

			try {
				in.close();
				in2.close();
				in3.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<OntResource> findMappingResource(String input_URI){
		ArrayList<OntResource> results = new ArrayList<OntResource>();
		OntResource resource1 = model.getOntResource(input_URI);
		if (resource1!=null){
			if(resource1.isClass()){
				OntClass inputClass=resource1.asClass();
				for (Iterator<OntClass> i = inputClass.listEquivalentClasses(); i.hasNext(); ) {
					  OntClass c = i.next();
					  results.add(c);
					  if(c.getURI().startsWith(CIS_URI)){
						  System.out.println("found mapping in CIS: "+ c.getURI() );
					  }
					  
					}
				//if subclass is included, then find subclasses and add
				//if superclass is included, then find superclasses and add
			}
			else if(resource1.isProperty()){
				OntProperty inputProperty = resource1.asProperty();
				for(Iterator<OntProperty> i = (Iterator<OntProperty>) inputProperty.listEquivalentProperties();i.hasNext();){
					OntProperty p = i.next();
					results.add(p);
					prl("found mapping property: "+p.getURI());
				}
			}
			else {prl("not class or property");}
		}
		return results;
	}
	public static void prl(String st){
    	System.out.println(st);
    }
	
	public ArrayList<String> findMappingDef(Message source, Message target){
		ArrayList<String> mappingDefs = new ArrayList<String>();
		for (SemanticAnnotation sa : target.getAnnotations()){
			String conceptURI=sa.getConceptRef();
			boolean found=false;
			for (SemanticAnnotation sa_s:source.getAnnotations()){
				if (sa_s.getConceptRef().equals(conceptURI)){
					found = true;
					break;
				}
			}
			if(!found){
				List<OntResource> correspondings = this.findMappingResource(conceptURI);
				for (OntResource resource:correspondings){
					for (SemanticAnnotation sa_s:source.getAnnotations()){
						if (sa_s.getConceptRef().equals(resource.getURI())){
							found = true;
							break;
						}
					}
				}
				if(found){
					mappingDefs.add(MAPPING_URI);
				}
			}

		}
		return mappingDefs;
	}
}
