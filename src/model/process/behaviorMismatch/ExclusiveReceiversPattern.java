package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public class ExclusiveReceiversPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		Router cbr = new Router();
		cbr.setType("CBR");
		cbr.setPriority(3);
		cbr.setName("auto-CBR");
		for (MessageFlow mf : flows){
			cbr.addInFlow(mf);
			cbr.addOutFlow(mf);
		}
		routers.add(cbr);
		return routers;
	}

}
