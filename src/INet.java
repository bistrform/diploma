import java.util.List;
import java.util.Queue;

public interface INet {

    public void registerNode(int id, Node node);
    public boolean isRegisteredNode(int id);

    public void run();
    public void run(Queue<ControlElement> controls);

}
