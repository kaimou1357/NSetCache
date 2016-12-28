/**
 * Created by Kai Mou on 12/27/2016.
 */
public class Test {
    public static void main(String[] args){
        Cache c = Cache.getStandardInstance(2, 2);
        //c.put("HelenHu", "Hi");
        c.get("HelenHu");
        c.get(4);
        c.get(5);
        c.get(7);
        c.get("obbaaaaa");
    }
}
