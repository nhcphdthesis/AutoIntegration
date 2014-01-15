package model.integration;

import java.util.ArrayList;

import model.process.interfacemodel.Operation;

public class IntegrationSolution {
	ArrayList<Channel> flows = new ArrayList<Channel>();
	ArrayList<Channel> channels = new ArrayList<Channel>();
	ArrayList<Mediator> mediators = new ArrayList<Mediator>();
	
	public ArrayList<Channel> getChannels() {
		return channels;
	}

	public void setChannels(ArrayList<Channel> channels) {
		this.channels = channels;
	}

	public ArrayList<Mediator> getMediators() {
		return mediators;
	}

	public void setMediators(ArrayList<Mediator> mediators) {
		this.mediators = mediators;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Solution [channels=\n");
		builder.append(channels);
		builder.append(",\n");
		builder.append("mediators=\n");
		builder.append(mediators);
		builder.append("]");
		return builder.toString();
	}

	public ArrayList<Channel> getFlows() {
		return flows;
	}
	
	ArrayList<EndpointConnector> inboundConnectors = new ArrayList<EndpointConnector>();
	ArrayList<EndpointConnector> outboundConnectors = new ArrayList<EndpointConnector>();
	
	public void addInboundConnector(EndpointConnector ec){
		if(!inboundConnectors.contains(ec)){
			inboundConnectors.add(ec);
		}
	}
	public void addOutboundConnector(EndpointConnector ec){
		if(!outboundConnectors.contains(ec)){
			outboundConnectors.add(ec);
		}
	}
	//find the connector for a specified operation object
	public EndpointConnector getConnector(Operation o){
		for(EndpointConnector ec:inboundConnectors){
			if(ec.getOperation()==o){
				return ec;
			}
		}
		for(EndpointConnector ec:outboundConnectors){
			if(ec.getOperation()==o){
				return ec;
			}
		}
		return null;
	}
	public boolean addChannel(Channel c){
		if(c.getSrcFilter()==c.getTgtFilter()){
			System.out.println("invalid channel:"+c.getSrcFilter().getName()+" to "+c.getTgtFilter().getName());
			return false;
		}
		for (Channel exist : this.channels){
			if (exist.getSrcFilter()==c.getSrcFilter()&&exist.getTgtFilter()==c.getTgtFilter()){
				System.out.println("channel between "+exist +" already exist");
				return false;
			}
		}
		this.channels.add(c);
		return true;
	}
}
