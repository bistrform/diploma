import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;

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
            System.out.println(menuMessage);
            Scanner userInputScanner = new Scanner(System.in);
            int command = userInputScanner.nextInt();
            processUserCommand(command);
            userInputScanner.close();
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
            default:
                break;
        }
    }

    private void initiatePut() {
        System.out.println("Please enter data in format key:value");
        Scanner userInputScanner = new Scanner(System.in);
        String input = userInputScanner.next();
        String[] splittedInput = input.split(":");
        String key = splittedInput[0];
        String value = splittedInput[1];
        executePut(key, value);
        System.out.println("Data processed");
    }

    private void executePut(String key, String value) {
        String receiverId = getLightestNodeId();
        MessageData data = new MessageData(key, value);
        Message putMessage = new Message(getNodeId(), receiverId, data);
        socket.acceptMessage(putMessage);
    }

    private void initiateGet() {
        System.out.println("Please enter data key");
        Scanner userInputScanner = new Scanner(System.in);
        String key = userInputScanner.next();
        String value = executeGet(key);
        System.out.println("Results for key " + key + ": " + value);
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

}
