import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private final MessageData messageData;
    private final String sender;
    private final List<String> receivers;

    public Message(String sender, List<String> receivers, MessageData messageData) {
        this.sender = sender;
        this.receivers = receivers;
        this.messageData = messageData;
    }

    public Message(String sender, String receiver, MessageData messageData) {
        this.sender = sender;
        this.receivers = new ArrayList<String>(Arrays.asList(receiver));
        this.messageData = messageData;
    }

    public String getSender() {
        return this.sender;
    }

    public List<String> getReceiverIds() {
        return this.receivers;
    }

    public MessageData getMessageData() {
        return this.messageData;
    }

}
