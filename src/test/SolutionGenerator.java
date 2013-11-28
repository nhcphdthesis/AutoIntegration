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
		
		CollaborationModel cm = Scenario.createChoreography();
		SolutionGenerator gen = new SolutionGenerator();

		prl(gen.generateSolution(cm));
		
		
		
	}

	
	
	public static void prl(Object o){
		System.out.println(o);
		
		
	}

	
	public IntegrationSolution construct(CollaborationModel chore){
		IntegrationSolution solution = new IntegrationSolution();
		//0. generate inbound channel and outbound channel for each mesage flow
		for (MessageFlow mf:chore.getMfs()){
			constructPartialChannelsForMessageFlow(mf,solution);
		}
		
		//1. order mismatch
		solveOrderMismatch(chore, solution);
		//2. multi-receiver
		for (Process p:chore.getParticipants()){
			for (SendTask st:p.getSendTasks()){
				if(st.getOutflows().size()>1){
					Map<Process,ArrayList<ReceiveTask>> receivers = chore.findReceivers(st);
					Mediator multiplixer = new Mediator();
					multiplixer.setName("multiplex for "+st.getName());
					for(ArrayList<ReceiveTask> al:receivers.values()){
						for(ReceiveTask rt:al){
							MessageFlow mf=chore.findMessageFlow(st, rt);
							addToInboundChannel(mf,solution,multiplixer);
						}
					}
					combineInboundChannels(st.getOutflows(),solution);
				}
			}
		}
		//3. multi-sender
		for (Process p: chore.getParticipants()){
			for (ReceiveTask rt:p.getReceiveTasks()){
				if (rt.getInflows().size()>1){
					Map<Process,ArrayList<SendTask>> senders = chore.findSenders(rt);
					Mediator merger = new Mediator();
					merger.setName("merger for "+rt.getName());
					for(ArrayList<SendTask> al:senders.values()){
						for(SendTask st:al){
							MessageFlow mf=chore.findMessageFlow(st, rt);
							addToOutboundChannel(mf,solution,merger);
						}
					}
					combineOutboundChannels(rt.getInflows(), solution);
				}
				
			}
		}
			
		return solution;
	}



	private void solveOrderMismatch(CollaborationModel chore,
			IntegrationSolution solution) {
		Map<MessageFlow,ArrayList<MessageFlow>> flows_resequence = chore.findOrderMismatch();
		for (MessageFlow mf:flows_resequence.keySet()){
			ArrayList<MessageFlow> mismatch=flows_resequence.get(mf);
			if(mismatch.size()>0){
				mismatch.add(mf);
				prl(mismatch);
				constructResequencer(mismatch,solution);
			}
		}
	}
	
	private void combineInboundChannels(ArrayList<MessageFlow> outflows,
			IntegrationSolution solution) {
		String namelist="combined inbound for ";
		for (MessageFlow mf:outflows){
			namelist+=mf.getName()+",";
		}
		Channel combined = new Channel(namelist);
		solution.getChannels().add(combined);
		for(MessageFlow mf:outflows){
			Channel old = mf.getInbound_channel();
			combined.setTgtFilter(old.getTgtFilter());
			combined.setSrcFilter(old.getSrcFilter());
			mf.setInbound_channel(combined);
			solution.getChannels().remove(old);
		}
	}
	
	private void combineOutboundChannels(ArrayList<MessageFlow> inflows,
			IntegrationSolution solution) {
		String namelist="combined outbound for ";
		for (MessageFlow mf:inflows){
			namelist+=mf.getName()+",";
		}
		Channel combined = new Channel(namelist);
		solution.getChannels().add(combined);
		for(MessageFlow mf:inflows){
			Channel old = mf.getOutbound_channel();
			combined.setTgtFilter(old.getTgtFilter());
			combined.setSrcFilter(old.getSrcFilter());
			mf.setOutbound_channel(combined);
			solution.getChannels().remove(old);
		}
	}

	private void constructResequencer(ArrayList<MessageFlow> mismatch,
			IntegrationSolution solution) {
		Mediator resequencer = new Mediator();
		String name = "";
		for(MessageFlow mf:mismatch){
			name+=mf.getName()+",";
		}
		resequencer.setName("resequencer for "+name);
		
		for(MessageFlow mf:mismatch){
			addToOutboundChannel(mf, solution, resequencer);
			resequencer.getMessageflows().add(mf);
		}
		
	}

	void constructPartialChannelsForMessageFlow(MessageFlow mf,IntegrationSolution s){
		//inbound
		Channel ch = new Channel("inbound for "+mf.getName());
		mf.setInbound_channel(ch);
		//allocate endpoint connector
		EndpointConnector ec_i = new EndpointConnector(mf.getSource().getOperation());
		ch.setSrcFilter(ec_i);
		
		s.getChannels().add(ch);
		//outbound
		ch = new Channel("outbound for "+mf.getName());
		mf.setOutbound_channel(ch);
		EndpointConnector ec_o = new EndpointConnector(mf.getTarget().getOperation());
		ch.setTgtFilter(ec_o);
		s.getChannels().add(ch);
		//data translator
		Mediator m = new Translator();
		m.setName("Translator for "+mf.getName());
		m.getMessageflows().add(mf);
		mf.getInbound_channel().setTgtFilter(m);
		mf.getOutbound_channel().setSrcFilter(m);
		s.getMediators().add(m);
		
		
	}
	
	void addToInboundChannel(MessageFlow mf, IntegrationSolution s, Mediator m){
		Channel inbound=mf.getInbound_channel();
		Channel intermediate = new Channel("Inbound Intermediate for "+mf.getName()+"."+m.getName());
		intermediate.setTgtFilter(inbound.getTgtFilter());
		intermediate.setSrcFilter(m);
		mf.getIntermediateChannels().add(intermediate);
		inbound.setTgtFilter(m);
		s.getChannels().add(intermediate);
		if(!s.getMediators().contains(m)){
			s.getMediators().add(m);
		}
	}
	
	void addToOutboundChannel(MessageFlow mf, IntegrationSolution s, Mediator m){
		Channel outbound=mf.getOutbound_channel();
		Channel intermediate = new Channel("Outbound Intermediate for "+mf.getName()+"."+m.getName());
		intermediate.setSrcFilter(outbound.getSrcFilter());
		intermediate.setTgtFilter(m);
		mf.getIntermediateChannels().add(intermediate);
		outbound.setSrcFilter(m);
		s.getChannels().add(intermediate);
		if(!s.getMediators().contains(m)){
			s.getMediators().add(m);
		}
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
