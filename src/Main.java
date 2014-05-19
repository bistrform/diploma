public class Main {

    public static void main(String[] args) {
        //System.out.println("hello");
        Net net = new Net();
        net.createNode();
        net.createNode();
        net.createNode();
        net.createNode();
        for (INode node: net.getRegisteredNodes().values()) {
            node.registerOtherNodes(net.getRegisteredNodes().keySet());
        }
        net.run();
    }

}
