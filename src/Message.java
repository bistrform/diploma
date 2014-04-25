import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private final MessageData messageData;
    private final int sender;
    private final List<Integer> receivers;

    public Message(Integer sender, List<Integer> receivers, MessageData messageData) {
        this.sender = sender;
        this.receivers = receivers;
        this.messageData = messageData;
    }

    public Message(Integer sender, Integer receiver, MessageData messageData) {
        this.sender = sender;
        this.receivers = new ArrayList<Integer>(Arrays.asList(receiver));
        this.messageData = messageData;
    }

    public int getSender() {
        return this.sender;
    }

    public List<Integer> getReceiverIds() {
        return this.receivers;
    }

    public MessageData getMessageData() {
        return this.messageData;
    }

}
