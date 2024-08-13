package tests;

import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        HashMap<String, String> m = new HashMap<>();
        HashMap<String, ArrayList<String>> mm = new HashMap<>();


        m.put("mm", "11111");
        m.put("mm", "2222");
        System.out.println("m = " + m);
        
        ArrayList<String> a = new ArrayList<>();
        a.add("a1");
        mm.put("1", a);
        System.out.println("mm = " + mm);


        ArrayList<String> b = mm.get("1");
        b.add("b1");
        
        mm.put("1", b);

        System.out.println("mm = " + mm);

    }

}
