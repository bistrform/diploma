import javax.naming.ldap.Control;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //System.out.println("hello");
        int[] ids = {0,1,2,3,4};
        List<Integer> nodeIds = new ArrayList<Integer>();
        for (int id : ids) {
            nodeIds.add(id);
        }
        Net net = new Net(nodeIds);
        net.run(createControlElements());
    }


    //ugly ugly useless junk, no declarative controls!
    public static Queue<ControlElement> createControlElements() {
        Queue<ControlElement> ce = new LinkedList<ControlElement>();
        List<Integer> receivers = new ArrayList<Integer>(Arrays.asList(1, 2));
        ce.add(new ControlElement(0, false, 0, receivers, "hello"));

        receivers = new ArrayList<Integer>(Arrays.asList(3));
        ce.add(new ControlElement(1, false, 2, receivers, "should drop this one"));

        receivers = new ArrayList<Integer>(Arrays.asList(2));
        ce.add(new ControlElement(2, false, 4, receivers, "should drop this also"));

        receivers = new ArrayList<Integer>(Arrays.asList(4));
        ce.add(new ControlElement(5, false, 3, receivers, "nextmessage"));
        ce.add(new ControlElement(9, true, -1, null, null));
        return ce;
    }

}
