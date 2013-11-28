package test;

import java.util.ArrayList;
import java.util.Map;

import model.integration.*;
import model.process.*;
import model.process.Process;
import model.process.interfacemodel.*;

public class SolutionGenerator {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CollaborationModel cm = Scenario.createChoreography_case2();
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
	
}
