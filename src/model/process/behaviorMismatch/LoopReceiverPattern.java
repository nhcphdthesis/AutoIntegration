package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public class LoopReceiverPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		MessageFlow mf=flows.get(0);
		Router splitter = new Router();
		splitter.setPriority(5);
		splitter.setType("Splitter");
		splitter.setName("auto-splitter");
		routers.add(splitter);
		splitter.addInFlow(mf);
		splitter.addOutFlow(mf);
		return routers;
	}


	
}
