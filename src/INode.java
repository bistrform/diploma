import java.util.Collection;
import java.util.List;

public interface INode {

    public String getNodeId();

    public void setSocket(ISocket socket);

    public void process(Message message);

}
