/**
* @file Utils.java
* @Author Jorge Hdez. Batista
* @date 13/12/21
* @brief Class of assistant methods that will keep our code clean.
*/
package agents;

import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

public class Utils 
{
	
	/**
     * It allows to search the agents which offer a concrete type service.
     * @param agent Agent where it starts the research
     * @param tipo Type of searched service
     * @return First found agent
     */
    protected static DFAgentDescription buscarAgente(Agent agent, String tipo)
    {
        //We indicate the type of the agent that we are looking for
        DFAgentDescription template=new DFAgentDescription();
        ServiceDescription templateSd=new ServiceDescription();
        templateSd.setType(tipo);
        template.addServices(templateSd);
        
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(new Long(1)); //Since java version 9 Long version is deprecated
        
        try
        {
            DFAgentDescription [] results = DFService.search(agent, template, sc);
            for (int i = 0; i < results.length; ++i) 
            {
                DFAgentDescription dfd = results[i];
                AID provider = dfd.getName();
                    
                //An agent can offer different services, we are only interested in the type we are looking for
                Iterator it = dfd.getAllServices();
                while (it.hasNext()) {
                    ServiceDescription sd = (ServiceDescription) it.next();
                    if (sd.getType().equals(tipo)) {
                        System.out.println("- Servicio \"" + sd.getName() + "\" proporcionado por el agente "+ provider.getName());
                        return dfd;
                    }
                }
            }
        }
        catch(FIPAException e)
        {
        	e.printStackTrace();
        }        
        return null;
    }
}