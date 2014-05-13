import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControlNode implements INode {

    private String nodeId;
    private List<String> otherNodes;

    public ControlNode(String nodeId) {
        this.nodeId = nodeId;
        otherNodes = new ArrayList<String>();
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void registerOtherNodes(List<String> nodeIds) {
        List<String> otherIds = new ArrayList<String>(nodeIds);
        otherIds.remove(this.nodeId);
        this.otherNodes.addAll(otherIds);
    }

    @Override
    public void setSocket(ISocket socket) {
    }

    @Override
    public List<Message> process(Message message) {
        List<Message> responses = new ArrayList<Message>();
        //if the control node has no message to process, it prompts the user for controls
        if (message == null) {
            String help = "Choose next action:\n1 - skip action\n2 - get world state\n";
            System.out.println(help);
            Scanner userInputScanner = new Scanner(System.in);
            String reply = userInputScanner.nextLine();
            try {
                int action = Integer.parseInt(reply);
                switch (action) {
                    case 1:
                        break;

                    case 2:
                        System.out.println("Getting world status");
                        break;

                    default:
                        break;

                }
            }
            catch (NumberFormatException ex) {
                System.out.println("Wrong input");
            }

        }
        return responses;
    }
}
