/**
* @file COmputationAgent.java
* @Author Jorge Hdez. Batista
* @date 24/12/21
* @brief Class of the agent that connects with the external API
*/
package agents;

import jade.core.*;

import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/*! The class of the Agent that will interact with the GUI*/
public class ComputationAgent extends Agent {
	
	private static final long serialVersionUID = 1L;
	public static final String NICKNAME = "ComputationAgent";
	
	@Override
    public void setup(){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName("ComputationAgent");
        sd.setType("Computo");

        dfd.addServices(sd);

        try {
            DFService.register(this,dfd);
            
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        ComputationBehaviour computationBehaviour = new ComputationBehaviour(this);
        addBehaviour(computationBehaviour);
    }
}