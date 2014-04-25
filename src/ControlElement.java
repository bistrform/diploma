import java.util.List;

public class ControlElement {

    public long tickNumber;
    public boolean getWorldReport;
    public int senderId;
    public List<Integer> receiverIds;
    public String message;

    public ControlElement(long tickNumber, boolean getWorldReport, int senderId, List<Integer> receiverIds, String message) {
        this.tickNumber = tickNumber;
        this.getWorldReport = getWorldReport;
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.message = message;
    }

}
