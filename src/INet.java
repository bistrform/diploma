
public interface INet {

    public void registerNode(NodeId id);
    public boolean isRegisteredNode(NodeId id);

    public void run();

}
