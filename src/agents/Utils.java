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
     * Permite buscar los agents que dan un servicio de un determinado tipo. Devuelve el primero de ellos.
     * @param agent Agentes desde el que se realiza la búsqueda
     * @param tipo Tipo de servicio buscado
     * @return Primer agente que proporciona el servicio
     */
    protected static DFAgentDescription buscarAgente(Agent agent, String tipo)
    {
        //indico las caracteristicas el tipo de servicio que quiero encontrar
        DFAgentDescription template=new DFAgentDescription();
        ServiceDescription templateSd=new ServiceDescription();
        templateSd.setType(tipo); //como define el tipo el agente coordinador tamiben podriamos buscar por nombre
        template.addServices(templateSd);
        
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(new Long(1));
        
        try
        {
            DFAgentDescription [] results = DFService.search(agent, template, sc);
            for (int i = 0; i < results.length; ++i) 
            {
                DFAgentDescription dfd = results[i];
                AID provider = dfd.getName();
                    
                //un mismo agente puede proporcionar varios servicios, solo estamos interasados en "tipo"
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