/**
* @file PerceptionBehaviour.java
* @Author Jorge Hdez. Batista
* @date 13/12/21
* @brief Class of the behaviour of the agent that interacts with the GUI
*/
package agents;

import com.clarifai.grpc.api.Output;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import gui.MainGui;

public class PerceptionBehaviour extends CyclicBehaviour {
	
	/** A long type
	 *  The identifier of the agent
	 */
	private static final long serialVersionUID = 1L;
	/** An agent type
	 *  It will contain the agent that have created the GUI
	 */
    PerceptionAgent agent;
    /** A MainGui type
	 *  It will store our GUI so we can access to it from here
	 */
    MainGui gui;
    
    public PerceptionBehaviour(PerceptionAgent a, MainGui gui) {
        this.agent = a;
        this.gui = gui;
    }
    
    /**
	 * The method that runs the behaviour of the PerceptionAgent
	 */
	public void action() {
		//We prepare the message that will be received by the behaviour agent with the information from the computation agent
        ACLMessage msg = agent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF));
        
        /**
         * We decapsulate the information from the message and we send it to the Graphich Interface
         */
        try {
        	Output output = (Output) msg.getContentObject();
        	//System.out.println(output.getData().getConcepts(0).getName());
            gui.showResults(output.getData().getConcepts(0).getName(), output.getData().getConcepts(1).getName(), output.getData().getConcepts(2).getName());
            
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
	
}