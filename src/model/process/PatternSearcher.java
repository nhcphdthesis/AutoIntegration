package model.process;

import java.util.ArrayList;
import java.util.HashMap;

import model.integration.Router;
import model.process.behaviorMismatch.BehaviorMismatchPattern;
import model.process.behaviorMismatch.ExclusiveReceiversPattern;
import model.process.behaviorMismatch.LoopReceiverPattern;
import model.process.behaviorMismatch.LoopSenderPattern;
import model.process.behaviorMismatch.MultipleReceiversPattern;
import model.process.behaviorMismatch.MultipleSendersPattern;
import model.process.behaviorMismatch.OrderMismatchPattern;
import model.process.behaviorMismatch.SequenceReceiversPattern;

public class PatternSearcher {
	CollaborationModel cm;
	HashMap<String, ArrayList<BehaviorMismatchPattern>> findings = new HashMap<String, ArrayList<BehaviorMismatchPattern>>();
	public void setCollaborationModel(CollaborationModel model){
		this.cm = model;
	}
	
	public void find(CollaborationModel cm){
		this.cm= cm;
		this.findLoopReceiverOccurences();
		this.findExclusiveReceivers();
		this.findLoopSenderOccurences();
		this.findMultipleReceivers();
		this.findSeqenceReceivers();
		this.findOrderMismatchOccurences();
		this.findMultipleSenders();
		
		for (String type : findings.keySet()){
			
			ArrayList<BehaviorMismatchPattern> occurs = findings.get(type);
			if (occurs.size()==0) continue;
			System.out.println("generating for "+type);
			for (BehaviorMismatchPattern o : occurs){
				ArrayList<Router> mediators = o.getMediator();
				System.out.println(mediators);
			}
		}
	}
	
	private void findOrderMismatchOccurences() {
		ArrayList<BehaviorMismatchPattern> occurs = new ArrayList<BehaviorMismatchPattern>();

		for (MessageFlow mf:cm.getMfs()){

				SendTask st = mf.getSource();
				ReceiveTask rt = mf.getTarget();
				OrderMismatchPattern occur = new OrderMismatchPattern();
				occur.getFlows().add(mf);
				for (MessageFlow f:cm.getMfs()){
					if (f==mf) continue;
					if (this.cm.orderMismatch(mf, f)){
						System.out.println("OrderMismatch: "+mf.getName()+", "+f.getName());
						occur.getFlows().add(f);
					}
				}
				if(occur.getFlows().size()>1){
					occurs.add(occur);
				}
		}
		
		occurs = this.removeDuplicates(occurs);
		findings.put("OrderMismatch", occurs);
	}

	public void findLoopReceiverOccurences() {
		ArrayList<BehaviorMismatchPattern> occurs = new ArrayList<BehaviorMismatchPattern>();

		for (MessageFlow mf:cm.getMfs()){
			//loop receiver occurs when the source is not loop, the target is loop, and there is only one incoming flow for target task.
			//and maybe more criteria on message content
			if (mf.getTarget().isLoop()&&mf.getTarget().getInflows().size()==1&&!mf.getSource().isLoop()){
				System.out.println("find loopReceiver: "+mf.getName());
				LoopReceiverPattern occur = new LoopReceiverPattern();
				occur.getFlows().add(mf);
				occurs.add(occur);
			}
		}
		findings.put("LoopReceiver", occurs);
	}
	
	public void findLoopSenderOccurences() {
		ArrayList<BehaviorMismatchPattern> occurs = new ArrayList<BehaviorMismatchPattern>();

		for (MessageFlow mf:cm.getMfs()){
			//loop sender occurs when the target is not loop, the source is loop, and there is only one outgoing flow from source task.
			//and maybe more criteria on message content
			if (mf.getSource().isLoop()&&!mf.getTarget().isLoop()&&mf.getSource().getOutflows().size()==1){
				System.out.println("find loopSender: "+mf.getName());
				LoopSenderPattern occur = new LoopSenderPattern();
				occur.getFlows().add(mf);
				occurs.add(occur);
			}
		}
		findings.put("LoopSender", occurs);
	}
	public void findSeqenceReceivers() {
		ArrayList<BehaviorMismatchPattern> occurs = new ArrayList<BehaviorMismatchPattern>();

		for (MessageFlow mf:cm.getMfs()){
			//
			if (mf.getSource().getOutflows().size()>1){
				SendTask st = mf.getSource();
				ReceiveTask rt = mf.getTarget();
				SequenceReceiversPattern occur = new SequenceReceiversPattern();
				occur.getFlows().add(mf);
				for (MessageFlow f:st.getOutflows()){
					if (f==mf) continue;
					if (rt.getProcess().seq(rt,f.getTarget())){
						occur.getFlows().add(f);
					}
					else if (rt.getProcess().seq(f.getTarget(),rt)){
						occur.getFlows().add(f);
					}
				}
				if(occur.getFlows().size()>1){
					System.out.println("SequenceReceivers: "+occur.getFlows().toString());
					occurs.add(occur);
				}
				
			}
		}
		
		occurs = this.removeDuplicates(occurs);
		
		findings.put("SequenceReceivers", occurs);
	}
	
	public void findMultipleReceivers() {
		ArrayList<BehaviorMismatchPattern> occurs = new ArrayList<BehaviorMismatchPattern>();

		for (SendTask st: this.cm.getAllSendTasks()){
			if(st.getOutflows().size()>1){

					MultipleReceiversPattern occur = new MultipleReceiversPattern();
					ArrayList<Process> targetProcesses = new ArrayList<Process>();
					for (MessageFlow f:st.getOutflows()){
						if(targetProcesses.contains(f.getTarget().getProcess())){
							
						}
						else{
							occur.getFlows().add(f);
							targetProcesses.add(f.getTarget().getProcess());
						}

					}
					if (occur.getFlows().size()>1){
						System.out.println("MultipleReceiver: "+occur.getFlows().toString());
						occurs.add(occur);
					}


			}
		}

		findings.put("MultipleReceivers", occurs);
	}
	public void findMultipleSenders() {
		ArrayList<BehaviorMismatchPattern> occurs = new ArrayList<BehaviorMismatchPattern>();

		for (ReceiveTask rt: cm.getAllReceiveTasks()){
			if(rt.getInflows().size()>1){
				MultipleSendersPattern occur = new MultipleSendersPattern();
				
				for(MessageFlow f:rt.getInflows()){
					occur.getFlows().add(f);
				}
				System.out.println("MultipleSenders: "+occur.getFlows().toString());
				occurs.add(occur);
			}
		}
		findings.put("MultipleSenders", occurs);
	}
	public void findExclusiveReceivers() {
		ArrayList<BehaviorMismatchPattern> occurs = new ArrayList<BehaviorMismatchPattern>();

		for (SendTask st:cm.getAllSendTasks()){
			//
			if (st.getOutflows().size()>1){
				for(MessageFlow mf:st.getOutflows()){
				ExclusiveReceiversPattern occur = new ExclusiveReceiversPattern();
				occur.getFlows().add(mf);
				for (MessageFlow f:st.getOutflows()){
					if (f==mf) continue;
					if (mf.getTarget().getProcess().exc(mf.getTarget(),f.getTarget())){
						occur.getFlows().add(f);
					}
				}
				if(occur.getFlows().size()>1){
					System.out.println("ExclusiveReceivers: "+occur.getFlows().toString());
					occurs.add(occur);
				}
				
			}
		}

		}
		occurs = removeDuplicates(occurs);
		findings.put("ExclusiveReceivers", occurs);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PatternSearcher [findings=");
		builder.append(findings);
		builder.append("]");
		return builder.toString();
	}
	public ArrayList<BehaviorMismatchPattern> removeDuplicates(ArrayList<BehaviorMismatchPattern> list){
		for(int i=list.size()-1;i>0;i--){
			BehaviorMismatchPattern p = list.get(i);
			for(int j=0;j<i;j++){
				BehaviorMismatchPattern p1=list.get(j);
				if(same(p,p1)) list.remove(p);
			}
		}
		return list;
	}

	private boolean same(BehaviorMismatchPattern p, BehaviorMismatchPattern p1) {
		
		return p.getFlows().containsAll(p1.getFlows())&&p1.getFlows().containsAll(p.getFlows());
	}
}
