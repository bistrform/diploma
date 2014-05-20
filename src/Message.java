import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private final MessageData messageData;
    private final String sender;
    private final List<String> receivers;
    private final int actualSenderSize;

    public Message(String sender, List<String> receivers, MessageData messageData, int actualSenderSize) {
        this.sender = sender;
        this.receivers = receivers;
        this.messageData = messageData;
        this.actualSenderSize = actualSenderSize;
    }

    public Message(String sender, String receiver, MessageData messageData, int actualSenderSize) {
        this.sender = sender;
        this.receivers = new ArrayList<String>(Arrays.asList(receiver));
        this.messageData = messageData;
        this.actualSenderSize = actualSenderSize;
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

    public int getActualSenderSize() {
        return this.actualSenderSize;
    }

}
