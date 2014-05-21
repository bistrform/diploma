import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Node implements INode, IObservable {

    private String nodeId;
    private ISocket socket;
    //this map holds other nodes' ids and the occupied space in it
    private Map<String, Integer> otherNodes;
    private Map<String, String> data;

    public String getNodeId() {
        return nodeId;
    }

    public Node(String nodeId) {
        this.nodeId = nodeId;
        socket = new Socket();
        otherNodes = new LinkedHashMap<String, Integer>();
        data = new HashMap<String, String>();
    }

    @Override
    public void registerOtherNodes(Set<String> nodeIds) {
        List<String> otherIds = new ArrayList<String>(nodeIds);
        otherIds.remove(this.nodeId);
        for (String id: otherIds) {
            //in the beginning all nodes are empty (for now)
            this.otherNodes.put(id, 0);
        }
    }

    @Override
    public void setSocket(ISocket socket) {
        this.socket = socket;
    }

    @Override
    public void process(Message message) {
        if (message != null)
            processMessage(message);
    }

    private void processMessage(Message message) {
        MessageData data = message.getMessageData();
        String messageKey = data.getKey();
        String messageValue = data.getValue();
        //if the message is a "get" request
        if (messageValue.equals("")) {
            String value = this.data.get(messageKey);
            if (value != null) {
                MessageData valueMessageData = new MessageData(messageKey, value);
                Message valueMessage = new Message(getNodeId(), message.getSender(), valueMessageData, getActualSize());
                valueMessage.setIsAnswer();
                socket.acceptMessage(valueMessage);
            }
        }
        //if the message is either a "put" request or a "get" answer
        else {
            if (message.isAnswer()) {
                System.out.format("The value for the key \"%s\" is \"%s\"\n", messageKey, messageValue);
            }
            else {
                System.out.println("Data inserted to node " + nodeId);
                this.data.put(messageKey, messageValue);
            }
        }
        updateSenderSize(message);
    }

    private void updateSenderSize(Message message) {
        String senderId = message.getSender();
        int actualSenderSize = message.getActualSenderSize();
        otherNodes.put(senderId, actualSenderSize);
    }

    @Override
    public void executePut(String key, String value) {
        String receiverId = getLightestNodeId();
        MessageData data = new MessageData(key, value);
        Message putMessage = new Message(getNodeId(), receiverId, data, getActualSize());
        socket.acceptMessage(putMessage);
    }

    //this only sends the request to other nodes. the value is returned when the answer arrives
    @Override
    public void executeGet(String key) {
        List<String> receivers = new ArrayList<String>(otherNodes.keySet());
        MessageData data = new MessageData(key);
        Message getMessage = new Message(getNodeId(), receivers, data, getActualSize());
        socket.acceptMessage(getMessage);
    }

    private String getLightestNodeId() {
        //ugly current implementation. think about other data structures

        Iterator<Map.Entry<String, Integer>> nodesIterator = otherNodes.entrySet().iterator();
        String lightestNodeId = nodesIterator.next().getKey();
        while (nodesIterator.hasNext()) {
            Map.Entry<String, Integer> nextEntry = nodesIterator.next();
            if (nextEntry.getValue() < otherNodes.get(lightestNodeId)) {
                lightestNodeId = nextEntry.getKey();
            }
        }
        return lightestNodeId;
    }

    @Override
    public String getStatus() {
        StringBuilder nodeStatus = new StringBuilder();
        nodeStatus.append("Node: " + nodeId);
        nodeStatus.append(System.getProperty("line.separator"));
        nodeStatus.append(data.toString());
        nodeStatus.append(System.getProperty("line.separator"));
        return nodeStatus.toString();
    }

    private int getActualSize() {
        return this.data.size();
    }

}
