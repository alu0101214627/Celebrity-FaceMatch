/**
* @file PerceptionAgent.java
* @Author Jorge Hdez. Batista
* @date 13/12/21
* @brief Class of the agent that interacts with the GUI
*/
package agents;

import java.io.File;
import java.io.IOException;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.*;

import gui.*;

/*! The class of the Agent that will interact with the GUI*/
public class PerceptionAgent extends Agent {
	
	/** A long type
	 *  The identifier of the agent
	 */
	private static final long serialVersionUID = 1L;
	/** A string type
	 *  It will contain the name of the agent
	 */
	public static final String NICKNAME = "PerceptionAgent";
	/** A MainGui type
	 *  It will store our GUI so we can acces to it from here
	 */
	private MainGui gui;
	
	
	/**
	 * The initializer, here it is showed to the rest of agents the the perception agent
	 */
	public void setup() {
		//We create the Directory Facilitator (where an agent can see other agents)
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        //We prepare the description of the service that we will add.
        ServiceDescription sd = new ServiceDescription();
        sd.setName("PerceptionAgent");
        sd.setType("percepcion");

        //We add the agent with it service to the Directory Facilitator (where other agents can see its services)
        dfd.addServices(sd);
        
        try {
            DFService.register(this,dfd);
            
        } catch (FIPAException e) {
            e.printStackTrace();
        }
		
        //When we have alredy done the setup of the service of the perception agent, we can start running the Grapphic Interface
		gui = new MainGui("Celebrity FaceMatch", this);
		gui.run();
		
		//We run the behaviour that the agent will have
		addBehaviour(new PerceptionBehaviour(this, gui));
	}
	/** AUN POR COMENTAR
	 * The method that allow us to create a message and send it anywhere
	 * @param 
	 */
	public void sendMessage(File file) throws IOException {
		DFAgentDescription dfd = Utils.buscarAgente(this, "Computo");
		
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(dfd.getName());
		msg.setContentObject(file);
		
		this.send(msg);
	}
}