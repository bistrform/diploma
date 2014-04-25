import java.util.*;


public class Net implements INet {

    private Logger logger;
    private ErrorSimulator errorSimulator;
    private Map<Integer, Node> registeredNodes;
    private long tickNumber;

    public Map<Integer, Node> getRegisteredNodes() {
        return registeredNodes;
    }

    public Net(List<Integer> nodeIds) {
        this.registeredNodes = new LinkedHashMap<Integer, Node>();
        logger = new Logger();
        errorSimulator = new ErrorSimulator();
        tickNumber = 0;
        createNodes(nodeIds);

        errorSimulator.isolateNode(2);
        logger.nodeIsolated(2);
    }

    private void createNodes(List<Integer> nodeIds) {
        for (Integer id : nodeIds) {
            Node node = new Node(id);
            //registerOtherNodes takes care of excluding the node itself
            node.registerOtherNodes(nodeIds);
            registerNode(id, node);
            System.out.println("Registered node with id " + id);
        }
        System.out.println();
    }

    @Override
    public void registerNode(int id, Node node) {
        if (!registeredNodes.containsKey(id)) {
            registeredNodes.put(id, node);
            logger.nodeCreated(node);
        }
    }

    @Override
    public boolean isRegisteredNode(int id) {
        return registeredNodes.containsKey(id);
    }

    @Override
    public void run() {

    }

    @Override
    public void run(Queue<ControlElement> controls) {
        ControlElement nextControl = controls.poll();
        List ids = new ArrayList(registeredNodes.keySet());
        for (int tick = 0; tick < 10; tick++) {
            tickNumber = tick;
            logger.setTick(tick);
            int nodeIndex = tick % registeredNodes.size();
            int id = nodeIndex;
            System.out.format("Tick %d. Giving control to node with id %d \n", tick, id);
            Node nextNode = registeredNodes.get(id);
            //if there's a control element to process
            if (nextControl != null) {
                while (nextControl.tickNumber == tick) {
                    if (nextControl.getWorldReport) {
                        logger.printWorldReport();
                    }
                    if (nextControl.senderId == -1)
                        break;
                    Node sender = registeredNodes.get(nextControl.senderId);
                    MessageData data = new MessageData(nextControl.message);
                    Message message = new Message(sender.getNodeId(), nextControl.receiverIds, data);
                    sender.sendMessage(nextControl.receiverIds, data);
                    logger.messageSent(message);
                    nextControl = controls.poll();
                    if (nextControl == null) break;
                }
            }
            nextNode.process();
            Message nextMessage = nextNode.getNextMessageToSend();
            if (nextMessage != null) {
                processMessage(nextMessage);
            }
            System.out.println();
        }
    }

    private void processMessage(Message message) {
        List<Integer> receiverIds = message.getReceiverIds();
        for (int receiverId: receiverIds) {
            if (errorSimulator.shouldDrop(message, tickNumber, receiverId))
                continue;
            Node receiverNode = registeredNodes.get(receiverId);
            receiverNode.receiveMessage(message);
            System.out.format("Node %d sent message to node %d\n", message.getSender(), receiverId);
            logger.messageProcessed();
        }
    }
}
