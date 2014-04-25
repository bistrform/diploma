import java.util.List;

public interface INode {

    public void registerOtherNodes(List<Integer> nodeIds);

    public void process();

    public void sendMessage(int receiver, MessageData data);
    public void sendMessage(List<Integer> receivers, MessageData data);
    public Message getNextMessageToSend();

    public void receiveMessage(Message message);


}
