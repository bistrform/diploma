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
        String menuMessage = "Please select next option\n1 - insert data\n2 - get data\n3 - continue";
        System.out.format("Node with id %s is processing\n", nodeId);
        //if the node has nothing to process, promptt for user input
        if (message == null) {
            try {
                System.out.println(menuMessage);
                Scanner userInputScanner = new Scanner(System.in);
                String reply = userInputScanner.nextLine();
                int command = Integer.parseInt(reply);
                processUserCommand(command);
            }
            catch (Exception ex) {

            }
        }
        //else process the message appropriately
        else {
            processMessage(message);
        }
    }

    private void processMessage(Message message) {
        MessageData data = message.getMessageData();
        String key = data.getKey();
        String value = data.getValue();
        if (!value.equals("")) {
            this.data.put(key,value);
        }
        else {

        }
        updateSenderSize(message);
    }

    private void updateSenderSize(Message message) {
        String senderId = message.getSender();
        int actualSenderSize = message.getActualSenderSize();
        otherNodes.put(senderId, actualSenderSize);
    }

    private void processUserCommand(int command) {
        switch (command) {
            case 1:
                initiatePut();
                break;

            case 2:
                initiateGet();
                break;

            case 3:
                break;

            case 4:
                System.out.println(getStatus());
                break;

            default:
                break;
        }
    }

    private void initiatePut() {
        System.out.println("Please enter data in format key:value");
        try {
            Scanner userInputScanner = new Scanner(System.in);
            String input = userInputScanner.nextLine();
            String[] splittedInput = input.split(":");
            String key = splittedInput[0];
            String value = splittedInput[1];
            executePut(key, value);
            System.out.println("Data processed");
        }
        catch (Exception ex) {

        }
    }

    private void executePut(String key, String value) {
        String receiverId = getLightestNodeId();
        MessageData data = new MessageData(key, value);
        Message putMessage = new Message(getNodeId(), receiverId, data, getActualSize());
        socket.acceptMessage(putMessage);
    }

    private void initiateGet() {
        try {
            System.out.println("Please enter data key");
            Scanner userInputScanner = new Scanner(System.in);
            String key = userInputScanner.nextLine();
            String value = executeGet(key);
            System.out.println("Results for key " + key + ": " + value);
        }
        catch (Exception ex) {

        }
    }

    private String executeGet(String key) {
        String value = "No value found";

        return value;
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
