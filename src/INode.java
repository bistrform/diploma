import java.util.Collection;
import java.util.List;

public interface INode {

    //new node gets connected to another node, no "register" in the API
    public void registerOtherNodes(List<Integer> nodeIds);




    //nodeid is string
    //client is a node. if the incoming meccage queue is empty, it can prompt for user actions


    //set message to socket
    public void setSocket (ISocket socket);


    //INet has priority queues inside of it, latency simulation
    //node sends all of the messages after processing 1 incoming message on own tick

    public void process(Message message);





    ///old junk
    public void sendMessage(int receiver, MessageData data);
    public void sendMessage(List<Integer> receivers, MessageData data);
    //broadcast?


    //public Message getNextMessageToSend();

    public void receiveMessage(Message message);


}
