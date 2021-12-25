/**
* @file ComputationBehaviour.java
* @Author Jorge Hdez. Batista
* @date 24/12/21
* @brief Class of the behaviour of the agent that connects with the external API
*/
package agents;

import com.clarifai.channel.ClarifaiChannel;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;
import com.clarifai.grpc.api.status.StatusCode;
import com.google.protobuf.ByteString;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ComputationBehaviour extends Behaviour {
	
	/** A long type
	 *  The identifier of the agent
	 */
	private static final long serialVersionUID = 1L;
	/** An agent type
	 *  It will contain the agent that have created the GUI
	 */
	ComputationAgent agent;
	
    public ComputationBehaviour(ComputationAgent a) {
        this.agent = a;
    }
    
    /**
	 * The method that runs the behaviour of the ComputationAgent
	 */
    public void action() {

    	//We prepare the message that will be received by the behaviour agent with the information from the perception agent
        ACLMessage message = agent.blockingReceive();
        File imagen = null;
        
        //We convert the message received into a image to send it to the external API
        try {
            imagen = (File) message.getContentObject();    
        } catch (UnreadableException e) {
            e.printStackTrace();
        }

        /** We connect with the external application, the code starting by "bed95...4a6"
         * is the key of the API we will use to have our answer 
         */
        V2Grpc.V2BlockingStub stub = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials("bed953637af7480e861b7800ff1074a6"));

        /** We prepare the answer creating it and connecting with the model (the code starting by "cfbb1...fe20")
         * and giving it the input we alredy have (the image uploaded by the user) in the format it is needed
         */
        MultiOutputResponse response = null;
        try {
            response = stub.postModelOutputs(PostModelOutputsRequest.newBuilder().setModelId("cfbb105cb8f54907bb8d553d68d9fe20").addInputs(
            	Input.newBuilder().setData(Data.newBuilder().setImage(Image.newBuilder().setBase64(ByteString.copyFrom(Files.readAllBytes(imagen.toPath())))))).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // We make sure the model have gone correct, and the answer is now ready to be send
        if (response.getStatus().getCode() != StatusCode.SUCCESS) {
            throw new RuntimeException("Post model outputs failed, status: " + response.getStatus());
        }

        Output output = response.getOutputs(0);

        //We prepare the message to inform the PerceptionAgent we alredy have the answer of its request
        ACLMessage resultado = new ACLMessage(ACLMessage.INFORM_REF);
        try {
            resultado.setContentObject(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AID receiver = getAgentReceiver();
        if (receiver == null){
            System.out.println("Receiver Null");
        }
        resultado.addReceiver(receiver);
        agent.send(resultado);

    }
    
    public boolean done() {
        return false;
    }

    private AID getAgentReceiver(){
    	DFAgentDescription dfd = Utils.buscarAgente(agent, "percepcion");
    	return dfd.getName();
    }
	
}