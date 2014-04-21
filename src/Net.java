import java.util.ArrayList;
import java.util.List;


public class Net implements INet {

    private List<NodeId> registeredNodes;
    private long tickNumber;

    public Net() {
        this.registeredNodes = new ArrayList<NodeId>();
        tickNumber = 0;
    }

    @Override
    public void registerNode(NodeId id) {
        if (!registeredNodes.contains(id))
            registeredNodes.add(id);
    }

    @Override
    public boolean isRegisteredNode(NodeId id) {
        return registeredNodes.contains(id);
    }

    @Override
    public void run() {
        
    }
}
