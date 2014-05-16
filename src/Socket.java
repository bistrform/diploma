import java.util.LinkedList;
import java.util.Queue;

public class Socket implements ISocket {

    private Queue<Message> messageQueue;

    public Socket() {
        messageQueue = new LinkedList<Message>();
    }


    @Override
    public void acceptMessage(Message message) {
        messageQueue.add(message);
    }

    @Override
    public Message getMessage() {
        return messageQueue.poll();
    }
}
