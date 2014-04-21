public interface IErrorSimulator {

    public boolean shouldDrop(Message message, long numberOfTick);

    public boolean shouldDrop(Message message, long numberOfTick, NodeId receiver);

    public void isolateNode(NodeId nodeId);

    public void reconnectNode(NodeId nodeId);

}
