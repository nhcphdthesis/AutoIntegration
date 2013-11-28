package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public class OrderMismatchPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		Router resequencer = new Router();
		resequencer.setType("Resequencer");
		resequencer.setPriority(2);
		resequencer.setName("auto-resequencer");
		for (MessageFlow mf : flows){
			resequencer.addInFlow(mf);
			resequencer.addOutFlow(mf);
		}
		routers.add(resequencer);
		return routers;
	}

}
