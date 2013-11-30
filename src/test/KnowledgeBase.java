package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;

public class KnowledgeBase {
	private static final String INSTANCE_URI = "http://nhc.org/ontologies/ReportInstance.owl";
	private static final String MAPPING_URI = "http://nhc.org/ontologies/ReportMapping.owl";
	private static final String HL7_URI = "http://nhc.org/ontologies/HL7Ontology.owl";
	public static final String HL7_NS = HL7_URI + "#";
	private static final String CIS_URI = "http://nhc.org/ontologies/LocalOntology.owl";
	public static final String CIS_NS = CIS_URI + "#";
	static final String MAPPING_FILE_NAME = "kb/Ontologies/ReportMappingRDF.owl";
	static final String HL7_FILE_NAME = "kb/Ontologies/HL7OntologyRDF.owl";
	private static final String MODEL_FILE_NAME = "kb/Ontologies/ReportInstanceRDF.owl";
	private static final String CIS_FILE_NAME = "kb/Ontologies/LocalOntologyRDF.owl";
	private static final String FOLDER = "kb/Ontologies/";
	private static final String KB ="kb";
	/**
	 * @param args
	 */
	
	public OntModel getOntologyModel(){
		Dataset dataset = TDBFactory.createDataset(KB);
		Model tdb = dataset.getNamedModel("nhcTest");
		OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		ontology.getDocumentManager().setProcessImports(false);
		ontology.add(tdb);
		return ontology;
	}
	public static void main(String[] args) {
		OntModel ontology = new KnowledgeBase().getOntologyModel();
		
		String input_URI = "http://nhc.org/ontologies/HL7Ontology.owl#OBX.CONTENT";
		ArrayList<OntResource> results = new ArrayList<OntResource>();

				
				OntResource resource1 = ontology.getOntResource(input_URI);
				if(resource1.isClass()){
					OntClass inputClass=resource1.asClass();
					for (Iterator<OntClass> i = inputClass.listEquivalentClasses(); i.hasNext(); ) {
						  OntClass c = i.next();
						  results.add(c);
						System.out.println("found mapping in CIS: "+ c.getURI() );
						  
						}
					//if subclass is included, then find subclasses and add
					//if superclass is included, then find superclasses and add
				}
				else if(resource1.isProperty()){
					OntProperty inputProperty = resource1.asProperty();
					for(Iterator<OntProperty> i = (Iterator<OntProperty>) inputProperty.listEquivalentProperties();i.hasNext();){
						OntProperty p = i.next();
						results.add(p);
						System.out.println("found mapping property: "+p.getURI());
					}
				}
				else {System.out.println("not class or property");}
//		init();
	}
static void init(){
	Dataset dataset = TDBFactory.createDataset(KB);
//	dataset.begin(ReadWrite.WRITE) ;
	Model tdb = dataset.getNamedModel("nhcTest");
	tdb.read(MAPPING_FILE_NAME);
	OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, tdb);
	ontology.writeAll(System.out, "N3");
	tdb.close();
//	dataset.end();
	dataset.close();
}
	
}
