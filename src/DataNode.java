import java.util.*;

public class DataNode implements INode, IObservable {

    private String nodeId;
    private List<String> otherNodes;
    private Map<String, String> data;

    public String getNodeId() {
        return nodeId;
    }

    public DataNode(String nodeId) {
        this.nodeId = nodeId;
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Message> process(Message message) {
        System.out.format("Node with id %s is processing\n", nodeId);
        List<Message> responses = new ArrayList<Message>();

        return responses;
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
