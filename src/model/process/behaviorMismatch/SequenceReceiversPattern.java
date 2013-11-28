package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public class SequenceReceiversPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		Router routingslip = new Router();
		routingslip.setPriority(3);
		routingslip.setType("RoutingSlip");
		routingslip.setName("auto-routingslip");
		for (MessageFlow mf : flows){
			routingslip.addInFlow(mf);
			routingslip.addOutFlow(mf);
		}
		routers.add(routingslip);
		return routers;
	}

}
