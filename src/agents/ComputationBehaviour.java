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
	
	private static final long serialVersionUID = 1L;
	ComputationAgent agent;
	
    public ComputationBehaviour(ComputationAgent a) {
        this.agent = a;
    }
    
    @Override
    public void action() {

        ACLMessage message = agent.blockingReceive();
        File imagen = null;
        
        try {
            imagen = (File) message.getContentObject();
            
        } catch (UnreadableException e) {
            e.printStackTrace();
        }

        V2Grpc.V2BlockingStub stub = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials("bed953637af7480e861b7800ff1074a6"));


        MultiOutputResponse response = null;
        try {
            response = stub.postModelOutputs(PostModelOutputsRequest.newBuilder().setModelId("cfbb105cb8f54907bb8d553d68d9fe20").addInputs(
            	Input.newBuilder().setData(Data.newBuilder().setImage(Image.newBuilder().setBase64(ByteString.copyFrom(Files.readAllBytes(imagen.toPath())))))).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.getStatus().getCode() != StatusCode.SUCCESS) {
            throw new RuntimeException("Post model outputs failed, status: " + response.getStatus());
        }

        Output output = response.getOutputs(0);

        //Prepara el mensaje y lo envia
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
    
    @Override
    public boolean done() {
        return false;
    }

    private AID getAgentReceiver(){
    	DFAgentDescription dfd = Utils.buscarAgente(agent, "percepcion");
    	return dfd.getName();
    }
	
}