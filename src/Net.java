import java.util.*;


public class Net implements INet, IObservable {

    private Logger logger;
    private Map<String, Queue<Message>> messageQueues;
    private Map<String, INode> registeredNodes;
    private long tickNumber;

    public Map<String, INode> getRegisteredNodes() {
        return registeredNodes;
    }

    public Net() {
        this.registeredNodes = new LinkedHashMap<String, INode>();
        messageQueues = new HashMap<String, Queue<Message>>();
        logger = new Logger();
        tickNumber = 0;
    }

    //temporary helper methods
    public void createDataNode() {
        String id = UUID.randomUUID().toString();
        INode dataNode = new DataNode(id);
        registerNode(id, dataNode);
    }

    public void createControlNode() {
        String id = UUID.randomUUID().toString();
        INode controlNode = new ControlNode(id);
        registerNode(id, controlNode);
    }

    @Override
    public void registerNode(String id, INode node) {
        if (!registeredNodes.containsKey(id)) {
            Queue<Message> nodeQueue = new LinkedList<Message>();
            registeredNodes.put(id, node);
            messageQueues.put(id, nodeQueue);
        }
    }

    public boolean isRegisteredNode(String id) {
        return registeredNodes.containsKey(id);
    }

    @Override
    public void run() {
        for (int tick = 0; tick < 10; tick++) {
            tickNumber = tick;
            int currentNodeIndex = tick % registeredNodes.size();
            INode currentNode = getNodeByIndex(currentNodeIndex);
            System.out.format("Tick %d. Giving control to node with id %s \n", tick, currentNode.getNodeId());
            Queue<Message> currentNodeQueue = messageQueues.get(currentNode.getNodeId());
            if (currentNodeQueue != null) {
                Message nextMessageForNode = currentNodeQueue.poll();
                //the node is responsible for checking for null message
                List<Message> responseMessages = currentNode.process(nextMessageForNode);
                distributeResponseMessages(responseMessages);
            }
        }
    }

    private void distributeResponseMessages(List<Message> responseMessages) {
        for (Message message : responseMessages) {
            List<String> receiverIds = message.getReceiverIds();
            for (String receiverId : receiverIds) {
                Queue<Message> receiverQueue = messageQueues.get(receiverId);
                if (receiverQueue != null)
                    receiverQueue.add(message);
            }
        }
    }

    private INode getNodeByIndex(int index) {
        List<INode> nodes = new ArrayList<INode>(registeredNodes.values());
        return nodes.get(index);
    }

    @Override
    public String getStatus() {
        StringBuilder netStatus = new StringBuilder();
        netStatus.append("Net status");
        netStatus.append(System.getProperty("line.separator"));
        netStatus.append("Tick " + tickNumber);
        netStatus.append(System.getProperty("line.separator"));
        netStatus.append("Registered nodes: " + registeredNodes.toString());
        netStatus.append(System.getProperty("line.separator"));
        for (String id : messageQueues.keySet()) {
            netStatus.append("Queue for node " + id + " " + messageQueues.get(id).toString());
        }

        return netStatus.toString();
    }

}
