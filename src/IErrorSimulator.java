public interface IErrorSimulator {

    public boolean shouldDrop(Message message, long numberOfTick);

    public boolean shouldDrop(Message message, long numberOfTick, String receiver);

    public void isolateNode(String nodeId);
    public boolean isIsolatedNode(String nodeId);

    public void reconnectNode(String nodeId);

}
