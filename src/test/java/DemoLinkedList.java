import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.LinkedList;

public class DemoLinkedList {

    @Test
    public void methodOne(){
        LinkedList<String> llist = new LinkedList<String>();
        llist.add("google");
        llist.add(0,"apple");
        llist.add("amazon");
        Iterator<String> iterator = llist.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }



    }
}
