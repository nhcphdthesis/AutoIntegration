package test;

import java.util.ArrayList;
import java.util.Map;

import model.integration.*;
import model.process.*;
import model.process.Process;
import model.process.interfacemodel.*;
import model.semantics.MappingFinder;

public class SolutionGenerator {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CollaborationModel cm = ScenarioFactory.createOne2ManySendVariation2();
		SolutionGenerator gen = new SolutionGenerator();

		prl(gen.generateSolution(cm));
		
		
		
	}

	
	
	public static void prl(Object o){
		System.out.println(o);
		
		
	}

	
	IntegrationSolution generateSolution(CollaborationModel cm){
		IntegrationSolution solution = new IntegrationSolution();
		cm.findMismatches();
		for(ReceiveTask rt:cm.getAllReceiveTasks()){
			Operation o = rt.getOperation();
			EndpointConnector ec_outbound = new EndpointConnector(o);
			solution.addOutboundConnector(ec_outbound);
		}
		for (SendTask st : cm.getAllSendTasks()){
			Operation o = st.getOperation();
			EndpointConnector ec_inbound = new EndpointConnector(o);
			solution.addInboundConnector(ec_inbound);
			
			for(MessageFlow mf:st.getOutflows()){
				mf.sortRouters();
				System.out.println(mf.getName()+":"+mf.getRouters().toString());
				Mediator last = ec_inbound;
				// if mapping is found, generate translator
				Translator t = generateTranslator(mf);
				if(t!=null){
					Channel c = new Channel("from endpoint to translator");
					c.setSrcFilter(last);
					c.setTgtFilter(t);
					solution.addChannel(c);
					last=t;
				}
				
				for (int i=0;i<mf.getRouters().size();i++){
					Router router = mf.getRouters().get(i);
						Channel c = new Channel("channel");
						c.setSrcFilter(last);
						c.setTgtFilter(router);
						solution.addChannel(c);
						last = router;
				}
				Channel c = new Channel("channel");
				c.setSrcFilter(last);
				c.setTgtFilter(solution.getConnector(mf.getTarget().getOperation()));
				solution.addChannel(c);
			}
		}

		return solution;
	}
	public Translator generateTranslator(MessageFlow mf){
		long begin = System.currentTimeMillis();

		ArrayList<String> defs = MappingFinder.getInstance().findMappingDef(mf.getSource().getOperation().getOutput(), mf.getTarget().getOperation().getInput());
		if (defs.size()==0){
			return null;
		}
		long end = System.currentTimeMillis();
		long cost = end - begin;
		System.out.println("find time: "+ cost);
		Translator t = new Translator();
		t.setName("translator for "+mf.getName());
		t.setType("SemanticTranslator");
		for (String def : defs){
			prl("adding: "+def);
			t.addMappingDef(def);
		}
		return t;
	}
}
