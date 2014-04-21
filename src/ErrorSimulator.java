import java.util.ArrayList;
import java.util.List;

public class ErrorSimulator implements IErrorSimulator {

    private List<NodeId> isolatedNodes;

    public ErrorSimulator() {
        isolatedNodes = new ArrayList<NodeId>();
    }

    @Override
    public boolean shouldDrop(Message message, long numberOfTick) {
        return false;
    }

    @Override
    public boolean shouldDrop(Message message, long numberOfTick, NodeId receiver) {
        return false;
    }

    @Override
    public void isolateNode(NodeId nodeId) {
        isolatedNodes.add(nodeId);
    }

    @Override
    public void reconnectNode(NodeId nodeId) {
        if (isolatedNodes.contains(nodeId))
            isolatedNodes.remove(nodeId);
    }
}
