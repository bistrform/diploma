import javax.naming.ldap.Control;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //System.out.println("hello");
        Net net = new Net();
        net.createControlNode();
        net.createControlNode();
        net.createDataNode();
        net.run();
    }

}
