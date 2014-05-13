public interface INet {

    public void registerNode(String id, INode node);
    public boolean isRegisteredNode(String id);


    //each node has a queue of incoming messages

    public void run();

}
