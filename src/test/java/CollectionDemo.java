import org.testng.annotations.Test;

import java.util.*;

public class CollectionDemo {

    //arraylist contains
    @Test
    public void arraylistDemo(){
         List<String> listone  = new ArrayList<String>();
         listone.add("google");
         System.out.println(listone.isEmpty());
         listone.add("tata");
         listone.add(1,"flipcart");
         System.out.println(listone);
/*
         for(int i=0;i<listone.size();i++){
             System.out.println("listone  " +listone.get(i));


         }
         */


         Iterator<String> it = listone.iterator();
         while(it.hasNext()){
             String names = it.next();
             System.out.println(names);

         }
    }

}
