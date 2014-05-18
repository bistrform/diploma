import java.util.*;


public class Net implements INet, IObservable {

    // this map holds queues of messages that have to be delivered to each node
    private Map<String, Queue<Message>> messagesToProcess;
    // this map holds all of the messages each node has sent
    private Map<String, ISocket> nodeSockets;
    private Map<String, INode> registeredNodes;
    private long tickNumber;


    public Net() {
        this.registeredNodes = new LinkedHashMap<String, INode>();
        messagesToProcess = new HashMap<String, Queue<Message>>();
        nodeSockets = new HashMap<String, ISocket>();
        tickNumber = 0;
    }

    public Map<String, INode> getRegisteredNodes() {
        return registeredNodes;
    }

    public void createNode() {
        String id = UUID.randomUUID().toString();
        INode node = new Node(id);
        registerNode(id, node);
    }

    @Override
    public void registerNode(String id, INode node) {
        if (!registeredNodes.containsKey(id)) {
            Queue<Message> nodeQueue = new LinkedList<Message>();
            registeredNodes.put(id, node);
            ISocket nodeSocket = new Socket();
            nodeSockets.put(id, nodeSocket);
            messagesToProcess.put(id, nodeQueue);
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

            //first let the node process the next available message
            Queue<Message> currentNodeQueue = messagesToProcess.get(currentNode.getNodeId());
            if (currentNodeQueue != null) {
                Message nextMessageForNode = currentNodeQueue.poll();
                //the node is responsible for checking for null message
                currentNode.process(nextMessageForNode);
            }

            //then get all of its formed messages from it's socket
            ISocket currentNodeSocket = nodeSockets.get(currentNode.getNodeId());
            distributeResponseMessages(currentNodeSocket.getAllMessages());
        }
    }

    private void distributeResponseMessages(List<Message> responseMessages) {
        for (Message message : responseMessages) {
            List<String> receiverIds = message.getReceiverIds();
            for (String receiverId : receiverIds) {
                Queue<Message> receiverQueue = messagesToProcess.get(receiverId);
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
        for (String id : messagesToProcess.keySet()) {
            netStatus.append("Queue for node " + id + " " + messagesToProcess.get(id).toString());
        }

        return netStatus.toString();
    }

}
