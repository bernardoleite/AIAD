package firefighting.firestation.messages;

import java.util.Date;

import firefighting.firestation.FireStationAgent;
import firefighting.world.WorldAgent;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class HelloAircraftMessage extends ACLMessage {
	private static final long serialVersionUID = 1L;
	
	private ACLMessage cfpMsg;

	private WorldAgent worldAgent;

	public HelloAircraftMessage(WorldAgent worldAgent) {
		super(ACLMessage.CFP);
		this.worldAgent = worldAgent;
		setACLMessage();
	}
	
	public ACLMessage getACLMessage() {
		return this.cfpMsg;
	}
	
	private void setACLMessage() {
		int numAircraftAgents = this.worldAgent.getNumAircraftsAgents();
		Object[] args = FireStationAgent.getAircraftAgentsNames(numAircraftAgents);
				 
		cfpMsg = null;
				
		if (args != null && args.length > 0) {
		      
			// Fill the CFP message
			cfpMsg = new ACLMessage(ACLMessage.CFP);
			      
			// Add all the pretended receivers a
			for (int i = 0; i < args.length; ++i)  
				cfpMsg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
					      
			cfpMsg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);	 
			
			// We want to receive a reply in 10 seconds
			cfpMsg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			
			cfpMsg.setContent("dummy-action");
		}
		else {
			System.out.println("No responder(s) specified!");  
		}	
	}
}
