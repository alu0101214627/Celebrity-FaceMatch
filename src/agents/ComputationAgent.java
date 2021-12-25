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
	
	/** A long type
	 *  The identifier of the agent
	 */
	private static final long serialVersionUID = 1L;
	
	/** A string type
	 *  It will contain the name of the agent
	 */
	public static final String NICKNAME = "ComputationAgent";
	
	/**
	 * The initializer, here it is showed to the rest of agents the the computation agent
	 */
    public void setup(){
    	//We create the Directory Facilitator (where an agent can see other agents)
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        //We prepare the description of the service that we will add.
        ServiceDescription sd = new ServiceDescription();
        sd.setName("ComputationAgent");
        sd.setType("Computo");

      //We add the agent with it service to the Directory Facilitator (where other agents can see its services)
        dfd.addServices(sd);

        try {
            DFService.register(this,dfd);
            
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        //We run the behaviour that the agent will have
        ComputationBehaviour computationBehaviour = new ComputationBehaviour(this);
        addBehaviour(computationBehaviour);
    }
}