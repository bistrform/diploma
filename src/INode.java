import java.util.Collection;
import java.util.List;

public interface INode {

    public String getNodeId();

    //set message to socket
    public void setSocket (ISocket socket);


    //INet has priority queues inside of it, latency simulation
    //node sends all of the messages after processing 1 incoming message on own tick

    public List<Message> process(Message message);

}
