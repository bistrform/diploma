import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node implements INode {

    private List<NodeId> otherNodes;
    private Map<String, String> data;


    public Node() {
        otherNodes = new ArrayList<NodeId>();
        data = new HashMap<String, String>();
    }

    @Override
    public void process() {

    }
}
