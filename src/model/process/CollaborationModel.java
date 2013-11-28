package model.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollaborationModel {
	String name;
	ArrayList<MessageFlow> mfs = new ArrayList<MessageFlow>();
	ArrayList<Process> participants = new ArrayList<Process>();
	
	PatternSearcher finder = new PatternSearcher();
	public ArrayList<MessageFlow> getMfs() {
		return mfs;
	}
	public ArrayList<Process> getParticipants() {
		return participants;
	}
	
	public boolean orderMismatch(MessageFlow mf1,MessageFlow mf2){
		SendTask s1=mf1.getSource();
		SendTask s2=mf2.getSource();
		ReceiveTask r1=mf1.getTarget();
		ReceiveTask r2=mf2.getTarget();
		if (r1.getProcess()!=r2.getProcess()){
			return false;
		}
		if(s1.getProcess()!=s2.getProcess()){
			return false;
		}
		Process pr = r1.getProcess();
		Process ps=s1.getProcess();
		if(pr.seq(r1, r2)&&ps.seq(s2, s1)){
			return true;
		}
		if(pr.seq(r2, r1)&&ps.seq(s1, s2)){
			return true;
		}
		return false;
	}
	
	public Map<MessageFlow,ArrayList<MessageFlow>> findOrderMismatch(){
		Map<MessageFlow,ArrayList<MessageFlow>> result = new HashMap<MessageFlow,ArrayList<MessageFlow>>();
		
		for(MessageFlow mf:this.getMfs()){
			ArrayList<MessageFlow> mismatch_set = new ArrayList<MessageFlow>();
			result.put(mf, mismatch_set);
			for (MessageFlow mf2:this.getMfs()){
				if(mf==mf2) {continue;}
				if (orderMismatch(mf,mf2)){
					mismatch_set = result.get(mf);
					if(result.get(mf2)==null||!result.get(mf2).contains(mf)){
						//if this relation has not been discovered yet
						mismatch_set.add(mf2);
					}
					
				}
			}
		}
		
		return result;
	}
//	public ArrayList<SendTask> findExtraMessageSenders(){
//		ArrayList<SendTask> result = new ArrayList<SendTask>();
//		for (Process p : this.participants){
//			for (Task t : p.getTasks()){
//				if (t instanceof SendTask){
//					SendTask st = (SendTask)t;
//					if (st.getOutflows().size()==0){
//						result.add(st);
//					}
//				}
//			}
//		}
//		return result;
//	}
	//find all receivers that connect to one certain send task, grouped by process
	public Map<Process,ArrayList<ReceiveTask>> findReceivers(SendTask st){
		Map<Process,ArrayList<ReceiveTask>> result = new HashMap<Process,ArrayList<ReceiveTask>>();
		for (MessageFlow mf:st.getOutflows()){
			ArrayList<ReceiveTask> receivers;
			if (!result.containsKey(mf.target.getProcess())){
				receivers = new ArrayList<ReceiveTask>();
				result.put(mf.target.getProcess(), receivers);
			}
			receivers = result.get(mf.target.getProcess());
			if (!receivers.contains(mf.target)){
				receivers.add(mf.target);
			}
		}
		
		return result;
	} 
	public Map<Process,ArrayList<SendTask>> findSenders(ReceiveTask rt){
		Map<Process,ArrayList<SendTask>> result = new HashMap<Process,ArrayList<SendTask>>();
		for (MessageFlow mf:rt.getInflows()){
			ArrayList<SendTask> receivers;
			if (!result.containsKey(mf.source.getProcess())){
				receivers = new ArrayList<SendTask>();
				result.put(mf.source.getProcess(), receivers);
			}
			receivers = result.get(mf.source.getProcess());
			if (!receivers.contains(mf.source)){
				receivers.add(mf.source);
			}
		}
		
		return result;
	}
	

	
	public void findMismatches(){
		finder.find(this);
//		System.out.println(finder);
	}

	
	public MessageFlow findMessageFlow(SendTask st, ReceiveTask rt){
		for(MessageFlow mf:this.mfs){
			if(mf.getSource()==st&&mf.getTarget()==rt){
				return mf;
			}
		}
		return null;
	}
	
	public void connect(Task t1, Task t2){
		if(t1.getProcess()==t2.getProcess()){
			System.out.println("cannot connect tasks from the same process");
			return;
		}
		MessageFlow mf=new MessageFlow("MessageFlow From "+t1.getName()+" to "+t2.getName(),(SendTask)t1,(ReceiveTask)t2);
		this.mfs.add(mf);
		if (!this.participants.contains(t1.getProcess())){
			this.participants.add(t1.getProcess());
		}
		if (!this.participants.contains(t2.getProcess())){
			this.participants.add(t2.getProcess());
		}
		
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CollaborationModel [name=");
		builder.append(name);
		builder.append(", mfs=");
		builder.append(mfs);
		builder.append("]");
		return builder.toString();
	}
	
	public ArrayList<SendTask> getAllSendTasks(){
		ArrayList<SendTask> sendTasks = new ArrayList<SendTask>();
		for (MessageFlow mf:this.getMfs()){
			if (!sendTasks.contains(mf.getSource())){
				sendTasks.add(mf.getSource());
			}
		}
		return sendTasks;
	}
	public ArrayList<ReceiveTask> getAllReceiveTasks(){
		ArrayList<ReceiveTask> receiveTasks = new ArrayList<ReceiveTask>();
		for (MessageFlow mf:this.getMfs()){
			if (!receiveTasks.contains(mf.getTarget())){
				receiveTasks.add(mf.getTarget());
			}
		}
		return receiveTasks;
	}
}
