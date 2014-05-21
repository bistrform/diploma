import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface INode {

    public String getNodeId();

    public void registerOtherNodes(Set<String> nodeIds);

    public void setSocket(ISocket socket);

    public void executePut(String key, String value);

    public void executeGet(String key);

    public void process(Message message);

}
