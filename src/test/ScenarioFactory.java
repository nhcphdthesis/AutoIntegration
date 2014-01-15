package test;

import model.process.CollaborationModel;
import model.process.MessageFlow;
import model.process.Process;
import model.process.ReceiveTask;
import model.process.Relation;
import model.process.SendTask;
import model.process.interfacemodel.Endpoint;
import model.process.interfacemodel.InterfaceModel;
import model.process.interfacemodel.Operation;
import model.process.interfacemodel.SemanticAnnotation;
import model.semantics.KnowledgeBase;
import model.semantics.MappingFinder;

public class ScenarioFactory {
	public static CollaborationModel createChoreographyComplex1() {
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		Process p3 = new Process("p3");
		SendTask p1_s1 = new SendTask("send1 in p1");
		p1.addTask(p1_s1);
		SendTask p1_s2 = new SendTask("send2 in p1");
		p1.addTask(p1_s2);
		ReceiveTask p2_r1 = new ReceiveTask("receive1 in p2");
		p2.addTask(p2_r1);
		ReceiveTask p2_r2 = new ReceiveTask("receive2 in p2");
		p2.addTask(p2_r2);
		p2.assertSeq(p2_r1, p2_r2);
		ReceiveTask p3_r1 = new ReceiveTask("receive in P3");
		p3.addTask(p3_r1);
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);
		c1.getParticipants().add(p3);
		MessageFlow mf1 = new MessageFlow("mf1",p1_s1,p2_r1);
		c1.getMfs().add(mf1);
		MessageFlow mf2 = new MessageFlow("mf2",p1_s2,p2_r1);
		c1.getMfs().add(mf2);
		MessageFlow mf3 = new MessageFlow("mf3",p1_s1,p3_r1);
		c1.getMfs().add(mf3);
		MessageFlow mf4 = new MessageFlow("mf4",p1_s2,p2_r2);
		c1.getMfs().add(mf4);
		
		Endpoint ep1 = new Endpoint();
		ep1.setURI("hl7://127.0.0.1");
		InterfaceModel inf1 = new InterfaceModel();
		inf1.setEndpoint(ep1);
		Operation ep1_op1 = new Operation();
		ep1_op1.setName("Send1");
		ep1_op1.setInterfce(inf1);
		p1_s1.setOperation(ep1_op1);
		Operation ep1_op2 = new Operation();
		ep1_op2.setName("Send2");
		ep1_op2.setInterfce(inf1);
		p1_s2.setOperation(ep1_op2);
		
		Endpoint ep2 = new Endpoint();
		InterfaceModel inf2 = new InterfaceModel();
		inf2.setEndpoint(ep2);
		Operation ep2_op2 = new Operation();
		ep2_op2.setName("Receive21");
		ep2_op2.setInterfce(inf2);
		Operation ep2_op3 = new Operation();
		ep2_op3.setName("Receive22");
		ep2_op3.setInterfce(inf2);
		p2_r1.setOperation(ep2_op2);
		p2_r2.setOperation(ep2_op3);
		ep2.setURI("http://localhost");
		
		Endpoint ep3 = new Endpoint();
		InterfaceModel inf3 = new InterfaceModel();
		ep3.setURI("db:1234");
		inf3.setEndpoint(ep3);
		Operation ep3_op1 = new Operation();
		ep3_op1.setName("receiveE3");
		ep3_op1.setInterfce(inf3);
		p3_r1.setOperation(ep3_op1);
		return c1;
	}
	
	public static CollaborationModel createChoreography_OrderMismatch() {
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		Process p3 = new Process("p3");
		SendTask p1_s1 = new SendTask("send1 in p1");
		p1.addTask(p1_s1);
		SendTask p1_s2 = new SendTask("send2 in p2");
		p1.addTask(p1_s2);
		ReceiveTask p2_r1 = new ReceiveTask("receive1 in p2");
		p2.addTask(p2_r1);
		ReceiveTask p2_r2 = new ReceiveTask("receive2 in p2");
		p2.addTask(p2_r2);
		ReceiveTask p3_r1 = new ReceiveTask("receive in P3");
		p3.addTask(p3_r1);
		
		Relation r1=new Relation();
		r1.setType("seq");
		r1.getTasks().add(p1_s1);
		r1.getTasks().add(p1_s2);
		p1.getRelations().add(r1);
		
		Relation r2=new Relation();
		r2.setType("seq");
		r2.getTasks().add(p2_r1);
		r2.getTasks().add(p2_r2);
		p2.getRelations().add(r2);
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);
		c1.getParticipants().add(p3);

		c1.connect(p1_s1, p2_r2);
		c1.connect(p1_s2, p2_r1);


//		MessageFlow mf3 = new MessageFlow("mf3",p1_s1,p3_r1);
//		c1.getMfs().add(mf3);
//		MessageFlow mf4 = new MessageFlow("mf4",p1_s2,p2_r2);
//		c1.getMfs().add(mf4);
		
		Endpoint ep1 = new Endpoint();
		ep1.setURI("hl7://127.0.0.1");
		InterfaceModel inf1 = new InterfaceModel();
		inf1.setEndpoint(ep1);
		Operation ep1_op1 = new Operation();
		ep1_op1.setName("Send1");
		ep1_op1.setInterfce(inf1);
		p1_s1.setOperation(ep1_op1);
		Operation ep1_op2 = new Operation();
		ep1_op2.setName("Send2");
		ep1_op2.setInterfce(inf1);
		p1_s2.setOperation(ep1_op2);
		
		Endpoint ep2 = new Endpoint();
		InterfaceModel inf2 = new InterfaceModel();
		inf2.setEndpoint(ep2);
		Operation ep2_op2 = new Operation();
		ep2_op2.setName("Receive21");
		ep2_op2.setInterfce(inf2);
		Operation ep2_op3 = new Operation();
		ep2_op3.setName("Receive22");
		ep2_op3.setInterfce(inf2);
		p2_r1.setOperation(ep2_op2);
		p2_r2.setOperation(ep2_op3);
		ep2.setURI("http://localhost");
		
		Endpoint ep3 = new Endpoint();
		InterfaceModel inf3 = new InterfaceModel();
		ep3.setURI("db:1234");
		inf3.setEndpoint(ep3);
		Operation ep3_op1 = new Operation();
		ep3_op1.setName("receiveE3");
		ep3_op1.setInterfce(inf3);
		p3_r1.setOperation(ep3_op1);
		return c1;
	}
	
	public static CollaborationModel createChoreography_complex2() {
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		Process p3 = new Process("p3");
		SendTask p1_s1 = new SendTask("send1 in p1");
		p1.addTask(p1_s1);
		SendTask p1_s2 = new SendTask("send2 in p1");
		p1.addTask(p1_s2);
		SendTask p1_s3 = new SendTask("send3 in p1");
		p1.addTask(p1_s3);
		ReceiveTask p2_r1 = new ReceiveTask("receive1 in p2");
		p2.addTask(p2_r1);
		ReceiveTask p2_r2 = new ReceiveTask("receive2 in p2");
		p2.addTask(p2_r2);
		ReceiveTask p3_r1 = new ReceiveTask("receive in P3");
		p3.addTask(p3_r1);
		
		Relation r1=new Relation();
		r1.setType("seq");
		r1.getTasks().add(p1_s1);
		r1.getTasks().add(p1_s2);
		r1.getTasks().add(p1_s3);
		p1.getRelations().add(r1);
		
		Relation r2=new Relation();
		r2.setType("seq");
		r2.getTasks().add(p2_r1);
		r2.getTasks().add(p2_r2);
		p2.getRelations().add(r2);
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);
		c1.getParticipants().add(p3);
		MessageFlow mf1 = new MessageFlow("mf1",p1_s1,p2_r2);
		c1.getMfs().add(mf1);
		MessageFlow mf2 = new MessageFlow("mf2",p1_s2,p2_r1);
		c1.getMfs().add(mf2);
		MessageFlow mf3 = new MessageFlow("mf3",p1_s1,p3_r1);
		c1.getMfs().add(mf3);
		MessageFlow mf4 = new MessageFlow("mf4",p1_s3,p2_r2);
		c1.getMfs().add(mf4);
		
		Endpoint ep1 = new Endpoint();
		ep1.setURI("hl7://127.0.0.1");
		InterfaceModel inf1 = new InterfaceModel();
		inf1.setEndpoint(ep1);
		Operation ep1_op1 = new Operation();
		ep1_op1.setName("Send1");
		ep1_op1.setInterfce(inf1);
		p1_s1.setOperation(ep1_op1);
		Operation ep1_op2 = new Operation();
		ep1_op2.setName("Send2");
		ep1_op2.setInterfce(inf1);
		p1_s2.setOperation(ep1_op2);
		
		Endpoint ep2 = new Endpoint();
		InterfaceModel inf2 = new InterfaceModel();
		inf2.setEndpoint(ep2);
		Operation ep2_op2 = new Operation();
		ep2_op2.setName("Receive21");
		ep2_op2.setInterfce(inf2);
		Operation ep2_op3 = new Operation();
		ep2_op3.setName("Receive22");
		ep2_op3.setInterfce(inf2);
		p2_r1.setOperation(ep2_op2);
		p2_r2.setOperation(ep2_op3);
		ep2.setURI("http://localhost");
		
		Endpoint ep3 = new Endpoint();
		InterfaceModel inf3 = new InterfaceModel();
		ep3.setURI("db:1234");
		inf3.setEndpoint(ep3);
		Operation ep3_op1 = new Operation();
		ep3_op1.setName("receiveE3");
		ep3_op1.setInterfce(inf3);
		p3_r1.setOperation(ep3_op1);
		return c1;
	}
	
	
	
	public static CollaborationModel createChoreography_case() {
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");

		SendTask p1_s_order = new SendTask("send order");
		p1.addTask(p1_s_order);
		ReceiveTask p1_r_reject = new ReceiveTask("Receive Reject");
		p1.addTask(p1_r_reject);
		ReceiveTask p1_r_accept = new ReceiveTask("receive accept");
		p1.addTask(p1_r_accept);
		ReceiveTask p1_r_report = new ReceiveTask("receive report");
		p1.addTask(p1_r_report);
		
		p1.assertExc(p1_r_accept, p1_r_reject);
		
		
		ReceiveTask p2_r_order = new ReceiveTask("receive order request");
		p2.addTask(p2_r_order);
		ReceiveTask p2_r_item = new ReceiveTask("receive order items");
		p2_r_item.setLoop(true);
		p2.addTask(p2_r_item);
		
		p2.assertSeq(p2_r_order, p2_r_item);
		
		SendTask p2_s_response = new SendTask("send order response");
		p2.addTask(p2_s_response);
		SendTask p2_s_complete = new SendTask("send complete");
		p2.addTask(p2_s_complete);
		SendTask p2_s_pre_report = new SendTask("send preliminary report");
		p2.addTask(p2_s_pre_report);
		SendTask p2_s_sup_report = new SendTask("send suppliment report");
		p2.addTask(p2_s_sup_report);
		
		
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);

		MessageFlow order_requset = new MessageFlow("order_requset",p1_s_order,p2_r_order);
		c1.getMfs().add(order_requset);
		MessageFlow order_item = new MessageFlow("order_item",p1_s_order,p2_r_item);
		c1.getMfs().add(order_item);
		MessageFlow response_reject = new MessageFlow("response_reject",p2_s_response,p1_r_reject);
		c1.getMfs().add(response_reject);
		MessageFlow response_accept = new MessageFlow("response_accept",p2_s_response,p1_r_accept);
		c1.getMfs().add(response_accept);
		MessageFlow pre_report = new MessageFlow("pre_report",p2_s_pre_report,p1_r_report);
		c1.getMfs().add(pre_report);
		MessageFlow sup_report = new MessageFlow("sup_report",p2_s_sup_report,p1_r_report);
		c1.getMfs().add(sup_report);
		
		Endpoint ep1 = new Endpoint();
		ep1.setURI("hl7://127.0.0.1");
		InterfaceModel inf1 = new InterfaceModel();
		inf1.setEndpoint(ep1);
		Operation ep1_op1 = new Operation();
		ep1_op1.setName("Send Order Operation");
		ep1_op1.setInterfce(inf1);
		ep1_op1.getOutput().addAnnotation(new SemanticAnnotation("zhenduan",KnowledgeBase.CIS_NS+"hasObservation"));
		ep1_op1.getOutput().addAnnotation(new SemanticAnnotation("zhenduanContent",KnowledgeBase.CIS_NS+"StudyObservation"));
		ep1_op1.getOutput().setSemanticRef(KnowledgeBase.CIS_NS+"ReportMessage");
		p1_s_order.setOperation(ep1_op1);
		
		Operation ep1_op2 = new Operation();
		ep1_op2.setName("Receive Accept Operation");
		ep1_op2.setInterfce(inf1);
		p1_r_accept.setOperation(ep1_op2);
		Operation ep1_op_reject = new Operation();
		ep1_op_reject.setName("Receive Reject Operation");
		ep1_op_reject.setInterfce(inf1);
		p1_r_reject.setOperation(ep1_op_reject);
		Operation ep1_op_report = new Operation();
		ep1_op_report.setName("Receive Report Operation");
		ep1_op_report.setInterfce(inf1);
		p1_r_report.setOperation(ep1_op_report);
		
		
		Endpoint ep2 = new Endpoint();
		InterfaceModel inf2 = new InterfaceModel();
		inf2.setEndpoint(ep2);
		Operation ep2_op_order = new Operation();
		ep2_op_order.setName("Receive Order");
		ep2_op_order.setInterfce(inf2);
		p2_r_order.setOperation(ep2_op_order);
		ep2_op_order.getInput().addAnnotation(new SemanticAnnotation("obx",KnowledgeBase.HL7_NS+"OBX"));
		ep2_op_order.getInput().addAnnotation(new SemanticAnnotation("obx.content",KnowledgeBase.HL7_NS+"OBX.CONTENT"));
		
		Operation ep2_op_item = new Operation();
		ep2_op_item.setName("Receive Order Item");
		ep2_op_item.setInterfce(inf2);
		ep2_op_item.getInput().setSemanticRef(KnowledgeBase.HL7_NS+"OBX.CONTENT");
		
		p2_r_item.setOperation(ep2_op_item);
		Operation ep2_op_response = new Operation();
		ep2_op_response.setName("Send Response");
		ep2_op_response.setInterfce(inf2);
		p2_s_response.setOperation(ep2_op_response);
		Operation ep2_op_complete = new Operation();
		ep2_op_complete.setName("Send Complete");
		ep2_op_complete.setInterfce(inf2);
		p2_s_complete.setOperation(ep2_op_complete);
		
		
		Operation ep2_op_send_pre_report = new Operation();
		ep2_op_send_pre_report.setName("Send Preliminary Report");
		ep2_op_send_pre_report.setInterfce(inf2);
		p2_s_pre_report.setOperation(ep2_op_send_pre_report);
		
		Operation ep2_op_send_sup_report = new Operation();
		ep2_op_send_sup_report.setName("Send Supplement Report");
		ep2_op_send_sup_report.setInterfce(inf2);
		p2_s_sup_report.setOperation(ep2_op_send_sup_report);
		
		ep2.setURI("http://localhost");
		
		return c1;
	}
	
	public static CollaborationModel createChoreography_case2() {
		//change the order of complete and report in CIS, this requires resequencer
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");

		SendTask p1_s_order = new SendTask("send order");
		p1.addTask(p1_s_order);
		ReceiveTask p1_r_reject = new ReceiveTask("Receive Reject");
		p1.addTask(p1_r_reject);
		ReceiveTask p1_r_accept = new ReceiveTask("receive accept");
		p1.addTask(p1_r_accept);
		ReceiveTask p1_r_report = new ReceiveTask("receive report");
		p1.addTask(p1_r_report);
		ReceiveTask p1_r_complete = new ReceiveTask("receive complete");
		p1.addTask(p1_r_complete);
		
		p1.assertSeq(p1_r_report, p1_r_complete);
		p1.assertExc(p1_r_accept, p1_r_reject);
		
		ReceiveTask p2_r_order = new ReceiveTask("receive order request");
		p2.addTask(p2_r_order);
		ReceiveTask p2_r_item = new ReceiveTask("receive order items");
		p2_r_item.setLoop(true);
		p2.assertSeq(p2_r_order, p2_r_item);
		p2.addTask(p2_r_item);
		SendTask p2_s_response = new SendTask("send order response");
		p2.addTask(p2_s_response);
		SendTask p2_s_complete = new SendTask("send complete");
		p2.addTask(p2_s_complete);
		SendTask p2_s_pre_report = new SendTask("send preliminary report");
		p2.addTask(p2_s_pre_report);
		SendTask p2_s_sup_report = new SendTask("send suppliment report");
		p2.addTask(p2_s_sup_report);
		
		Relation r2=new Relation();
		r2.setType("seq");
		r2.getTasks().add(p2_s_complete);
		r2.getTasks().add(p2_s_pre_report);
		r2.getTasks().add(p2_s_sup_report);
		p2.getRelations().add(r2);
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);

		MessageFlow order_requset = new MessageFlow("order_requset",p1_s_order,p2_r_order);
		c1.getMfs().add(order_requset);
		MessageFlow order_item = new MessageFlow("order_item",p1_s_order,p2_r_item);
		c1.getMfs().add(order_item);
		MessageFlow response_reject = new MessageFlow("response_reject",p2_s_response,p1_r_reject);
		c1.getMfs().add(response_reject);
		MessageFlow response_accept = new MessageFlow("response_accept",p2_s_response,p1_r_accept);
		c1.getMfs().add(response_accept);
		MessageFlow complete = new MessageFlow("complete",p2_s_complete,p1_r_complete);
		c1.getMfs().add(complete);
		MessageFlow pre_report = new MessageFlow("pre_report",p2_s_pre_report,p1_r_report);
		c1.getMfs().add(pre_report);
		MessageFlow sup_report = new MessageFlow("sup_report",p2_s_sup_report,p1_r_report);
		c1.getMfs().add(sup_report);
		
		Endpoint ep1 = new Endpoint();
		ep1.setURI("hl7://127.0.0.1");
		InterfaceModel inf1 = new InterfaceModel();
		inf1.setEndpoint(ep1);
		Operation ep1_op1 = new Operation();
		ep1_op1.setName("Send Order Operation");
		ep1_op1.setInterfce(inf1);
		p1_s_order.setOperation(ep1_op1);
		Operation ep1_op2 = new Operation();
		ep1_op2.setName("Receive Accept Operation");
		ep1_op2.setInterfce(inf1);
		Operation ep1_op_complete = new Operation();
		ep1_op_complete.setName("Receive Complete Operation");
		ep1_op_complete.setInterfce(inf1);
		p1_r_complete.setOperation(ep1_op_complete);
		Operation ep1_op_reject = new Operation();
		ep1_op_reject.setName("Receive Reject Operation");
		ep1_op_reject.setInterfce(inf1);
		p1_r_reject.setOperation(ep1_op_reject);
		Operation ep1_op_report = new Operation();
		ep1_op_report.setName("Receive Report Operation");
		ep1_op_report.setInterfce(inf1);
		p1_r_report.setOperation(ep1_op_report);
		
		
		Endpoint ep2 = new Endpoint();
		InterfaceModel inf2 = new InterfaceModel();
		inf2.setEndpoint(ep2);
		Operation ep2_op_order = new Operation();
		ep2_op_order.setName("Receive Order");
		ep2_op_order.setInterfce(inf2);
		p2_r_order.setOperation(ep2_op_order);
		Operation ep2_op_item = new Operation();
		ep2_op_item.setName("Receive Order Item");
		ep2_op_item.setInterfce(inf2);
		p2_r_item.setOperation(ep2_op_item);
		Operation ep2_op_response = new Operation();
		ep2_op_response.setName("Send Response");
		ep2_op_response.setInterfce(inf2);
		p2_s_response.setOperation(ep2_op_response);
		Operation ep2_op_complete = new Operation();
		ep2_op_complete.setName("Send Complete");
		ep2_op_complete.setInterfce(inf2);
		p2_s_complete.setOperation(ep2_op_complete);
		
		
		Operation ep2_op_send_pre_report = new Operation();
		ep2_op_send_pre_report.setName("Send Preliminary Report");
		ep2_op_send_pre_report.setInterfce(inf2);
		p2_s_pre_report.setOperation(ep2_op_send_pre_report);
		
		Operation ep2_op_send_sup_report = new Operation();
		ep2_op_send_sup_report.setName("Send Supplement Report");
		ep2_op_send_sup_report.setInterfce(inf2);
		p2_s_sup_report.setOperation(ep2_op_send_sup_report);
		
		ep2.setURI("http://localhost");
		
		return c1;
	}
	
	public static CollaborationModel createOneFromManyReceiveVariation3() {
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		Process p3 = new Process("p3");
		SendTask p1_s1 = new SendTask("send1");
		p1.addTask(p1_s1);
		SendTask p1_s2 = new SendTask("send2");
		p1.addTask(p1_s2);
		SendTask p2_s1 = new SendTask("send 1 in p2");
		p2.addTask(p2_s1);

		ReceiveTask p3_r1 = new ReceiveTask("receive in P3");
		p3.addTask(p3_r1);
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);
		c1.getParticipants().add(p3);
		MessageFlow mf1 = new MessageFlow("mf1",p1_s1,p3_r1);
		c1.getMfs().add(mf1);
		MessageFlow mf2 = new MessageFlow("mf2",p1_s2,p3_r1);
		c1.getMfs().add(mf2);
		MessageFlow mf3 = new MessageFlow("mf3",p2_s1,p3_r1);
		c1.getMfs().add(mf3);
		
		Endpoint ep1 = new Endpoint();
		ep1.setURI("hl7://127.0.0.1");
		InterfaceModel inf1 = new InterfaceModel();
		inf1.setEndpoint(ep1);
		Operation ep1_op1 = new Operation();
		ep1_op1.setName("Send1");
		ep1_op1.setInterfce(inf1);
		p1_s1.setOperation(ep1_op1);
		Operation ep1_op2 = new Operation();
		ep1_op2.setName("Send2");
		ep1_op2.setInterfce(inf1);
		p1_s2.setOperation(ep1_op2);
		
		Endpoint ep2 = new Endpoint();
		InterfaceModel inf2 = new InterfaceModel();
		inf2.setEndpoint(ep2);
		Operation ep2_op2 = new Operation();
		ep2_op2.setName("Receive21");
		ep2_op2.setInterfce(inf2);
		Operation ep2_op3 = new Operation();
		ep2_op3.setName("Receive22");
		ep2_op3.setInterfce(inf2);
		p2_s1.setOperation(ep2_op2);
		ep2.setURI("http://localhost");
		
		Endpoint ep3 = new Endpoint();
		InterfaceModel inf3 = new InterfaceModel();
		ep3.setURI("db:1234");
		inf3.setEndpoint(ep3);
		Operation ep3_op1 = new Operation();
		ep3_op1.setName("receiveE3");
		ep3_op1.setInterfce(inf3);
		p3_r1.setOperation(ep3_op1);
		return c1;
	}
	


	
	public static CollaborationModel createSingleSendOrReceiveVariation3(){
		//simple sequenced receivers
		CollaborationModel cm=new CollaborationModel();
		Process client = new Process("process1");
		Process server = new Process("process2");

		SendTask client_request = new SendTask("send");
		client.addTask(client_request);

		
		ReceiveTask server_receive1 = new ReceiveTask("receive1");
		server.addTask(server_receive1);
		ReceiveTask server_receive2 = new ReceiveTask("receive2");
		server.addTask(server_receive2);
		server.assertSeq(server_receive1, server_receive2);
		
		cm.connect(client_request, server_receive1);
		cm.connect(client_request, server_receive2);

		return cm;
	}

	public static CollaborationModel createSingleSendOrReceiveVaration1(){
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		
		SendTask p1_s1 = new SendTask("send");
		p1.addTask(p1_s1);
		p1_s1.setLoop(true);
		
		ReceiveTask p2_r1 = new ReceiveTask("receive");
		p2.addTask(p2_r1);
		
		CollaborationModel cm = new CollaborationModel();
		cm.addParticipant(p1);
		cm.addParticipant(p2);

		cm.connect(p1_s1, p2_r1);
		
		return cm;
		
	}
	
	public static CollaborationModel createSingleSendOrReceiveVaration2(){
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		
		SendTask p1_s1 = new SendTask("send");
		p1.addTask(p1_s1);
		
		
		ReceiveTask p2_r1 = new ReceiveTask("receive");
		p2.addTask(p2_r1);
		p2_r1.setLoop(true);
		
		CollaborationModel cm = new CollaborationModel();
		cm.addParticipant(p1);
		cm.addParticipant(p2);

		cm.connect(p1_s1, p2_r1);
		
		return cm;
		
	}
	
	public static CollaborationModel createSingleSendReceiveVaration1(){
		CollaborationModel cm=new CollaborationModel();
		Process client = new Process("client process");
		Process server = new Process("server process");

		SendTask client_request = new SendTask("client request");
		client.addTask(client_request);
		client_request.setLoop(true);
		ReceiveTask client_receive = new ReceiveTask("client receive");
		client_receive.setLoop(true);
		client.addTask(client_request);
		
		client.assertSeq(client_request, client_receive);
		
		ReceiveTask server_receive = new ReceiveTask("server receive");
		server.addTask(server_receive);
		SendTask server_response = new SendTask("server response");
		server.addTask(server_response);
		server.assertSeq(server_receive, server_response);
		
		cm.connect(client_request, server_receive);
		cm.connect(server_response, client_receive);

		return cm;
	}
	
	public static CollaborationModel createSingleSendReceiveVaration2(){
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		
		SendTask p1_s1 = new SendTask("send request");
		p1.addTask(p1_s1);
		ReceiveTask p1_r1 = new ReceiveTask("Receive response");
		p1.addTask(p1_r1);
		
		
		ReceiveTask p2_r1 = new ReceiveTask("receive request in server");
		p2.addTask(p2_r1);
		p2_r1.setLoop(true);
		SendTask p2_s1=new SendTask("response from server");
		p2.addTask(p2_s1);
		p2_s1.setLoop(true);
		
		CollaborationModel cm = new CollaborationModel();
		cm.addParticipant(p1);
		cm.addParticipant(p2);

		cm.connect(p1_s1, p2_r1);
		cm.connect(p2_s1, p1_r1);
		
		return cm;
		
	}
	
	public static CollaborationModel createRacingIncomingMessagesVariation1(){
		//simple exclusive
		CollaborationModel cm=new CollaborationModel();
		Process client = new Process("process1");
		Process server = new Process("process2");

		SendTask client_request = new SendTask("send");
		client.addTask(client_request);

		
		ReceiveTask server_receive1 = new ReceiveTask("receive1");
		server.addTask(server_receive1);
		ReceiveTask server_receive2 = new ReceiveTask("receive2");
		server.addTask(server_receive2);
		server.assertExc(server_receive1, server_receive2);
		
		cm.connect(client_request, server_receive1);
		cm.connect(client_request, server_receive2);

		return cm;
	}
	
	public static CollaborationModel createRacingIncomingMessagesVariation2(){
		//simple exclusive
		CollaborationModel cm=new CollaborationModel();
		Process client = new Process("process1");
		Process server = new Process("process2");

		SendTask exe1 = new SendTask("send1");
		client.addTask(exe1);
		SendTask exe2 = new SendTask("send2");
		client.addTask(exe2);
		client.assertExc(exe1, exe2);

		
		ReceiveTask server_receive1 = new ReceiveTask("receive");
		server.addTask(server_receive1);
		
		cm.connect(exe1, server_receive1);
		cm.connect(exe2, server_receive1);

		return cm;
	}
	
	public static CollaborationModel createOne2ManySendVariation1() {
		//multiple receivers
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		Process p3 = new Process("p3");
		SendTask p1_s1 = new SendTask("send1");
		p1.addTask(p1_s1);

		ReceiveTask p2_r1 = new ReceiveTask("receive1");
		p2.addTask(p2_r1);

		ReceiveTask p3_r1 = new ReceiveTask("receive in P3");
		p3.addTask(p3_r1);
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);
		c1.getParticipants().add(p3);
		
		MessageFlow mf1 = new MessageFlow("mf1",p1_s1,p2_r1);
		c1.getMfs().add(mf1);
		MessageFlow mf3 = new MessageFlow("mf3",p1_s1,p3_r1);
		c1.getMfs().add(mf3);
		
		return c1;
	}
	
	public static CollaborationModel createOne2ManySendVariation2() {
		//multiple receivers
		Process p1 = new Process("p1");
		Process p2 = new Process("p2");
		Process p3 = new Process("p3");
		SendTask p1_s1 = new SendTask("send1");
		p1.addTask(p1_s1);
		SendTask p1_s2 = new SendTask("send2");
		p1.addTask(p1_s2);
		ReceiveTask p2_r1 = new ReceiveTask("receive1");
		p2.addTask(p2_r1);
		ReceiveTask p2_r2 = new ReceiveTask("receive2");
		p2.addTask(p2_r2);
		ReceiveTask p3_r1 = new ReceiveTask("receive in P3");
		p3.addTask(p3_r1);
		
		CollaborationModel c1 = new CollaborationModel();
		c1.getParticipants().add(p1);
		c1.getParticipants().add(p2);
		c1.getParticipants().add(p3);
		MessageFlow mf1 = new MessageFlow("mf1",p1_s1,p2_r1);
		c1.getMfs().add(mf1);
		MessageFlow mf2 = new MessageFlow("mf2",p1_s1,p2_r2);
		c1.getMfs().add(mf2);
		MessageFlow mf3 = new MessageFlow("mf3",p1_s1,p3_r1);
		c1.getMfs().add(mf3);
		
		Endpoint ep1 = new Endpoint();
		ep1.setURI("hl7://127.0.0.1");
		InterfaceModel inf1 = new InterfaceModel();
		inf1.setEndpoint(ep1);
		Operation ep1_op1 = new Operation();
		ep1_op1.setName("Send1");
		ep1_op1.setInterfce(inf1);
		p1_s1.setOperation(ep1_op1);
		Operation ep1_op2 = new Operation();
		ep1_op2.setName("Send2");
		ep1_op2.setInterfce(inf1);
		p1_s2.setOperation(ep1_op2);
		
		Endpoint ep2 = new Endpoint();
		InterfaceModel inf2 = new InterfaceModel();
		inf2.setEndpoint(ep2);
		Operation ep2_op2 = new Operation();
		ep2_op2.setName("Receive21");
		ep2_op2.setInterfce(inf2);
		Operation ep2_op3 = new Operation();
		ep2_op3.setName("Receive22");
		ep2_op3.setInterfce(inf2);
		p2_r1.setOperation(ep2_op2);
		p2_r2.setOperation(ep2_op3);
		ep2.setURI("http://localhost");
		
		Endpoint ep3 = new Endpoint();
		InterfaceModel inf3 = new InterfaceModel();
		ep3.setURI("db:1234");
		inf3.setEndpoint(ep3);
		Operation ep3_op1 = new Operation();
		ep3_op1.setName("receiveE3");
		ep3_op1.setInterfce(inf3);
		p3_r1.setOperation(ep3_op1);
		
		return c1;
	}
	
	
}
