import java.util.List;
import java.util.Queue;

public interface INet {

    public void registerNode(int id, Node node);
    public boolean isRegisteredNode(int id);


    //each node has a queue of incoming messages

    public void run();
    public void run(Queue<ControlElement> controls);

}
