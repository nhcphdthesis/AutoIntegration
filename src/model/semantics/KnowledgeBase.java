package model.semantics;

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
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
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
//		init();
		
		OntModel ontology = new KnowledgeBase().getOntologyModel();
		
		String input_URI = "http://nhc.org/ontologies/HL7Ontology.owl#OBX.CONTENT";
		//method 1: using Jena API
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
				//method 2: using SPARQL query
				// Create a new query
				String queryString = 
					"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
					"PREFIX hl7: <http://nhc.org/ontologies/HL7Ontology.owl#> " +
					"PREFIX cis: <http://nhc.org/ontologies/LocalOntology.owl#> " +
					"SELECT ?c " +
					"WHERE {" +
					"      ?c owl:equivalentClass  hl7:" + input_URI.substring(input_URI.indexOf("#")+1) + " ." +
					"      }";
				System.out.println("query = "+queryString);

				Query query = QueryFactory.create(queryString);

				// Execute the query and obtain results
				QueryExecution qe = QueryExecutionFactory.create(query, ontology);
				ResultSet result = qe.execSelect();

				// Output query results	
				ResultSetFormatter.out(System.out, result, query);

				// Important - free up resources used running the query
				qe.close();

	}
static void init(){
	Dataset dataset = TDBFactory.createDataset(KB);
//	dataset.begin(ReadWrite.WRITE) ;
	Model tdb = dataset.getNamedModel("nhcTest");
	
	tdb.read(MODEL_FILE_NAME);
	OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, tdb);
	ontology.writeAll(System.out, "N3");
	tdb.close();
//	dataset.end();
	dataset.close();
}
	
}
