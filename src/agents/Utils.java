/**
* @file Utils.java
* @Author Jorge Hdez. Batista
* @date 13/12/21
* @brief Class of assistant methods that will keep our code clean.
*/
package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

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
        //We create the template that we will need to search an agent based on its type
        DFAgentDescription template=new DFAgentDescription();
        ServiceDescription templateSd=new ServiceDescription();
        templateSd.setType(tipo);
        template.addServices(templateSd);
        
        //Here there are specified the max number of results (agents) we want to return 
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(new Long(1));
        
        /**
         * Based on the search constraints, the type, and the template,
         * we will find the agent we are looking for
         */        
        try
        {
            DFAgentDescription [] results = DFService.search(agent, template, sc);
            DFAgentDescription dfd;
            for (int i = 0; i < results.length; ++i) 
            {
                //An agent can offer different services, we are only interested in the type we are looking for
            	dfd = results[i];
            	Iterator it = dfd.getAllServices();
                while (it.hasNext()) {
                    ServiceDescription sd = (ServiceDescription) it.next();
                    if (sd.getType().equals(tipo)) {
                    	//AID provider = dfd.getName();
                        //System.out.println("- Servicio \"" + sd.getName() + "\" proporcionado por el agente "+ provider.getName());
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