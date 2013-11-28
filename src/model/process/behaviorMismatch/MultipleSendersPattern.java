package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public class MultipleSendersPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		Router filter = new Router();
		filter.setPriority(4);
		filter.setType("MessageFilter");
		filter.setName("auto-filter");
		for (MessageFlow mf : flows){
			filter.addInFlow(mf);
			filter.addOutFlow(mf);
		}
		routers.add(filter);
		Router aggregator = new Router();
		aggregator.setType("Aggregator");
		aggregator.setPriority(4);
		aggregator.setName("auto-agg-multisender");
		for (MessageFlow mf : flows){
			aggregator.addInFlow(mf);
			aggregator.addOutFlow(mf);
		}
		routers.add(aggregator);
		return routers;
	}

}
