package model.process.behaviorMismatch;

import java.util.ArrayList;

import model.integration.Router;
import model.process.MessageFlow;

public class LoopSenderPattern extends BehaviorMismatchPattern {

	@Override
	public ArrayList<Router> getMediator() {
		Router agg = new Router();
		agg.setType("Aggregator");
		agg.setPriority(0);
		agg.setName("auto-agg-loopsender");
		for (MessageFlow mf : flows){
			agg.addInFlow(mf);
			agg.addOutFlow(mf);
		}
		routers.add(agg);
		return routers;
	}

}
