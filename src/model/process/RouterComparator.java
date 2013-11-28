package model.process;

import java.util.Comparator;

import model.integration.Router;

public class RouterComparator implements Comparator<Router> {

	@Override
	public int compare(Router r1, Router r2) {
		// TODO Auto-generated method stub
		return r1.getPriority()-r2.getPriority();
	}

}
