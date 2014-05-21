import java.util.Scanner;
import java.util.Set;

public class ClientNode implements INode{

    String nodeId;
    INode attachedNode;
    INet net;

    public ClientNode(String nodeId, INode attachedNode) {
        this.nodeId = nodeId;
        this.attachedNode = attachedNode;
    }

    @Override
    public String getNodeId() {
        return nodeId;
    }

    @Override
    public void registerOtherNodes(Set<String> nodeIds) {

    }

    @Override
    public void setSocket(ISocket socket) {

    }

    private void initiatePut() {
        System.out.println("Please enter data in format key:value");
        try {
            Scanner userInputScanner = new Scanner(System.in);
            String input = userInputScanner.nextLine();
            String[] splittedInput = input.split(":");
            String key = splittedInput[0];
            String value = splittedInput[1];
            executePut(key, value);
            System.out.println("Data processed");
        }
        catch (Exception ex) {

        }
    }

    private void initiateGet() {
        try {
            System.out.println("Please enter data key");
            Scanner userInputScanner = new Scanner(System.in);
            String key = userInputScanner.nextLine();
            executeGet(key);
            System.out.println("Get command executed");
        }
        catch (Exception ex) {

        }
    }

    @Override
    public void executePut(String key, String value) {
        attachedNode.executePut(key, value);

    }

    @Override
    public void executeGet(String key) {
        attachedNode.executeGet(key);
    }

    @Override
    public void process(Message message) {
        String menuMessage = "Please select next option\n1 - insert data\n2 - get data\n3 - continue\n4 - get status";
        System.out.format("Node with id %s is processing\n", nodeId);
        try {
            System.out.println(menuMessage);
            Scanner userInputScanner = new Scanner(System.in);
            String reply = userInputScanner.nextLine();
            int command = Integer.parseInt(reply);
            processUserCommand(command);
        }
        catch (Exception ex) {

        }
    }

    private void processUserCommand(int command) {
        switch (command) {
            case 1:
                initiatePut();
                break;

            case 2:
                initiateGet();
                break;

            case 3:
                break;

            case 4:
                System.out.println(getStatus());
                break;

            default:
                break;
        }
    }

    private String getStatus() {
        StringBuilder nodeStatus = new StringBuilder();
        nodeStatus.append("Client node: " + nodeId);
        nodeStatus.append(System.getProperty("line.separator"));
        nodeStatus.append("Attached node: " + attachedNode.getNodeId());
        nodeStatus.append(System.getProperty("line.separator"));
        return nodeStatus.toString();
    }
}
