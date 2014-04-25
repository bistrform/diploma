import java.util.ArrayList;
import java.util.List;

public class ErrorSimulator implements IErrorSimulator {

    private List<Integer> isolatedNodes;

    public ErrorSimulator() {
        isolatedNodes = new ArrayList<Integer>();
    }

    @Override
    public boolean shouldDrop(Message message, long numberOfTick) {
        if (isolatedNodes.contains(message.getSender())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldDrop(Message message, long numberOfTick, int receiver) {
        if (isolatedNodes.contains(message.getSender())) {
            System.out.println("Dropped message from node " + message.getSender());
            return true;
        }
        if (isolatedNodes.contains(receiver)) {
            System.out.println("Dropped message to node " + receiver);
            return true;
        }
        return false;
    }

    @Override
    public void isolateNode(int nodeId) {
        isolatedNodes.add(nodeId);
    }

    @Override
    public boolean isIsolatedNode(int nodeId) {
        return isolatedNodes.contains(nodeId);
    }

    @Override
    public void reconnectNode(int nodeId) {
        if (isolatedNodes.contains(nodeId))
            isolatedNodes.remove(nodeId);
    }


}
