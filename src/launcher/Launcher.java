/**
* @file Launcher.java
* @Author Jorge Hdez. Batista
* @date 4/12/21
* @brief The controller of the rest of programs.
*/

package launcher;


import jade.core.Profile;
import jade.core.ProfileImpl;
import gui.MainGui;
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
		
		jade.core.Runtime rt = jade.core.Runtime.instance();
        rt.setCloseVM(true);
        Profile profile = new ProfileImpl(null, 1200, null);
        cc = rt.createMainContainer(profile);
        
        try {
            ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
            rt.createAgentContainer(pContainer);
            
			// Iniciación de Agentes
            cc.createNewAgent(PerceptionAgent.NICKNAME, PerceptionAgent.class.getName(), new Object[]{"0"}).start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
	}
	
	public static void main(String[] args) throws IOException {
        loadBoot();
    }

}
