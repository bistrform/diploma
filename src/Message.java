import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private final MessageData messageData;
    private final NodeId sender;
    private final List<NodeId> receivers;

    public Message(NodeId sender, List<NodeId> receivers, MessageData messageData) {
        this.sender = sender;
        this.receivers = receivers;
        this.messageData = messageData;
    }

    public Message(NodeId sender, NodeId receiver, MessageData messageData) {
        this.sender = sender;
        this.receivers = new ArrayList<NodeId>(Arrays.asList(receiver));
        this.messageData = messageData;
    }

    public NodeId getSender() {
        return this.sender;
    }

    public List<NodeId> getReceivers() {
        return this.receivers;
    }

    public MessageData getMessageData() {
        return this.messageData;
    }

}
