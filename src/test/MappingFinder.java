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
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class MappingFinder {
	private static final String INSTANCE_URI = "http://nhc.org/ontologies/ReportInstance.owl";
	private static final String MAPPING_URI = "http://nhc.org/ontologies/ReportMapping.owl";
	private static final String HL7_URI = "http://nhc.org/ontologies/HL7Ontology.owl";
	public static final String HL7_NS = HL7_URI + "#";
	private static final String CIS_URI = "http://nhc.org/ontologies/LocalOntology.owl";
	public static final String CIS_NS = CIS_URI + "#";

	private static final String FOLDER = "kb/Ontologies/";
	OntModel model;
	KnowledgeBase kb;

	protected static MappingFinder instance;

	public static MappingFinder getInstance() {
		if (instance == null) {
			instance = new MappingFinder();
		}
		return instance;
	}

	public MappingFinder() {
		kb = new KnowledgeBase();
		model = kb.getOntologyModel();
	}

	public List<OntResource> findMappingResource(String input_URI) {
		
		ArrayList<OntResource> results = new ArrayList<OntResource>();
		OntResource resource1 = model.getOntResource(input_URI);
		System.out.println("finding mappings for "+input_URI);
		if (resource1 != null) {
			if (resource1.isClass()) {
				OntClass inputClass = resource1.asClass();
				for (Iterator<OntClass> i = inputClass.listEquivalentClasses(); i
						.hasNext();) {
					OntClass c = i.next();
					if (!c.getURI().equals(input_URI)){
					results.add(c);
					
					System.out.println("found mapping class: "
								+ c.getURI());
					}

				}
				// if subclass is included, then find subclasses and add
				// if superclass is included, then find superclasses and add
			} else if (resource1.isProperty()) {
				OntProperty inputProperty = resource1.asProperty();
				for (Iterator<OntProperty> i = (Iterator<OntProperty>) inputProperty
						.listEquivalentProperties(); i.hasNext();) {
					OntProperty p = i.next();
					if (!p.getURI().equals(input_URI)){
					results.add(p);
					prl("found mapping property: " + p.getURI());
					}
				}
			} else {
				prl("not class or property");
			}
		}
		return results;
	}

	public static void prl(String st) {
		System.out.println(st);
	}

	public ArrayList<String> findMappingDef(Message source, Message target) {
		ArrayList<String> mappingDefs = new ArrayList<String>();
		if (source != null && target != null) {
			for (SemanticAnnotation sa : target.getAnnotations()) {
				String conceptURI = sa.getConceptRef();
				boolean found = false;
				for (SemanticAnnotation sa_s : source.getAnnotations()) {
					if (sa_s.getConceptRef().equals(conceptURI)) {
						found = true;
						break;
					}
				}
				if (!found) {
					List<OntResource> correspondings = this
							.findMappingResource(conceptURI);
					for (OntResource resource : correspondings) {
						for (SemanticAnnotation sa_s : source.getAnnotations()) {
							if (sa_s.getConceptRef().equals(resource.getURI())) {
								found = true;
								break;
							}
						}
					}
					if (found) {
						mappingDefs.add(MAPPING_URI);
					}
				}

			}
		}
		return mappingDefs;
	}
}
