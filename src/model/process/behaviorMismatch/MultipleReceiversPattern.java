package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public class MultipleReceiversPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		Router list = new Router();
		list.setType("RecipientList");
		list.setPriority(1);
		list.setName("auto-recipientlist");
		for (MessageFlow mf : flows){
			list.addInFlow(mf);
			list.addOutFlow(mf);
		}
		routers.add(list);
		return routers;
	}

}
