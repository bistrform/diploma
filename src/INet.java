public interface INet {

    public void registerNode(String id, INode node);
    public boolean isRegisteredNode(String id);

    public void run();

}
