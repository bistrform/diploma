import java.util.*;

public class Node implements INode, IObservable {

    private String nodeId;
    private ISocket socket;
    private List<String> otherNodes;
    private Map<String, String> data;

    public String getNodeId() {
        return nodeId;
    }

    public Node(String nodeId) {
        this.nodeId = nodeId;
        socket = new Socket();
        otherNodes = new ArrayList<String>();
        data = new HashMap<String, String>();
    }

//    @Override
    public void registerOtherNodes(List<String> nodeIds) {
        List<String> otherIds = new ArrayList<String>(nodeIds);
        otherIds.remove(this.nodeId);
        this.otherNodes.addAll(otherIds);
    }

    @Override
    public void setSocket(ISocket socket) {
        this.socket = socket;
    }

    @Override
    public void process(Message message) {
        System.out.format("Node with id %s is processing\n", nodeId);
        //response messages are pushed to the socket
    }

    @Override
    public String getStatus() {
        StringBuilder nodeStatus = new StringBuilder();
        nodeStatus.append("Data node: " + nodeId);
        nodeStatus.append(System.getProperty("line.separator"));
        nodeStatus.append(data.toString());
        nodeStatus.append(System.getProperty("line.separator"));
        return nodeStatus.toString();
    }

}
