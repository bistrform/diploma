public interface IErrorSimulator {

    public boolean shouldDrop(Message message, long numberOfTick);

    public boolean shouldDrop(Message message, long numberOfTick, int receiver);

    public void isolateNode(int nodeId);
    public boolean isIsolatedNode(int nodeId);

    public void reconnectNode(int nodeId);

}
