/**
* @file Launcher.java
* @Author Jorge Hdez. Batista
* @date 4/12/21
* @brief The controller of the rest of programs.
*/

package launcher;


import jade.core.Profile;
import jade.core.ProfileImpl;
import java.io.IOException;
import jade.wrapper.StaleProxyException;

import agents.*;

/*! The main file of the application, which will run first and start all the necessary processes*/
public class Launcher {
	
	/** A AgentContainer type
	 *  The place where all the agents of the application will be created and stored
	 */
	private static jade.wrapper.AgentContainer cc;
	/**
	 * The program which initializes the JADE platform and the agents
	 *
	 */
	private static void loadBoot() {
		
		/**
		 * We create the container of the agents that will form part of our MultiAgent System
		 * so, we need to use the object from JADE library and send with it a Profile Implementation
		 * which will be useful to launch different containers and avoid the conflict between them
		 */
		jade.core.Runtime rt = jade.core.Runtime.instance();
        rt.setCloseVM(true);
        Profile profile = new ProfileImpl(null, 1200, null);
        cc = rt.createMainContainer(profile);
        
        /**
		 * We create the agents, starting from the agents container, and sending all the information to identify the agent
		 */
        try {            
            cc.createNewAgent(PerceptionAgent.NICKNAME, PerceptionAgent.class.getName(), new Object[]{"0"}).start();
            cc.createNewAgent(ComputationAgent.NICKNAME, ComputationAgent.class.getName(), new Object[]{"0"}).start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
	}
	
	public static void main(String[] args) throws IOException {
        loadBoot();
    }

}
