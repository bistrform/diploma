import java.util.*;

public class Node implements INode {

    private Integer nodeId;
    private List<Integer> otherNodes;
    private Map<String, String> data;
    private Queue<Message> messagesToSend;
    private Queue<Message> receivedMessages;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setInteger(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Node(Integer nodeId) {
        this.nodeId = nodeId;
        otherNodes = new ArrayList<Integer>();
        data = new HashMap<String, String>();
        messagesToSend = new LinkedList<Message>();
        receivedMessages = new LinkedList<Message>();
    }

    @Override
    public void registerOtherNodes(List<Integer> nodeIds) {
        List<Integer> otherIds = new ArrayList<Integer>(nodeIds);
        otherIds.remove(this.nodeId);
        this.otherNodes.addAll(otherIds);
    }

    @Override
    public void process() {
        System.out.format("Node with id %d is processing\n", nodeId);
        if (receivedMessages.peek() != null) {
            Message nextReceivedMessage = receivedMessages.poll();
            System.out.println("Node " + nodeId + " received message with data " + nextReceivedMessage.getMessageData().getStringData());
        }
    }

    @Override
    public void sendMessage(int receiver, MessageData data) {
        Message message = new Message(getNodeId(), receiver, data);
        messagesToSend.add(message);
    }

    @Override
    public void sendMessage(List<Integer> receivers, MessageData data) {
        Message message = new Message(getNodeId(), receivers, data);
        messagesToSend.add(message);
    }

    @Override
    public Message getNextMessageToSend() {
        return messagesToSend.poll();
    }

    @Override
    public void receiveMessage(Message message) {
        receivedMessages.add(message);
    }
}
