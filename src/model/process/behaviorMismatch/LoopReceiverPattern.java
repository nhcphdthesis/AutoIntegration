package model.process.behaviorMismatch;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import model.integration.Router;
import model.integration.Splitter;
import model.process.MessageFlow;
import model.process.interfacemodel.Message;
import model.semantics.KnowledgeBase;

public class LoopReceiverPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		MessageFlow mf=flows.get(0);
		Splitter splitter = new Splitter();
		splitter.setPriority(5);
		
		String source,target;
		source = mf.getSource().getOperation().getOutput().getSemanticRef();
		target = mf.getTarget().getOperation().getInput().getSemanticRef();
		
		OntModel ontology = new KnowledgeBase().getOntologyModel();
		
		String queryString = 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX hl7: <http://nhc.org/ontologies/HL7Ontology.owl#> " +
				"PREFIX cis: <http://nhc.org/ontologies/LocalOntology.owl#> " +
				"SELECT ?item " +
				"WHERE {" +
				"      ?item rdf:Type  hl7:" + target.substring(target.indexOf("#")+1) + " ." +
				"      }";
			System.out.println("query = "+queryString);
			
			splitter.setQuery(queryString);

			Query query = QueryFactory.create(queryString);

			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.create(query, ontology);
			ResultSet result = qe.execSelect();

			// Output query results	
			ResultSetFormatter.out(System.out, result, query);

			// Important - free up resources used running the query
			qe.close();
		
		routers.add(splitter);
		splitter.addInFlow(mf);
		splitter.addOutFlow(mf);
		return routers;
	}


	
}
