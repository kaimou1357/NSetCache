/**
 * Created by Kai Mou on 12/27/2016.
 */
public class CacheTestCases {
    public static void main(String[] args){
        ClientCacheSet test = new ClientCacheSet(2, "MRU");
        ClientCache c = new ClientCache(2, 4, test);
        c.put("HelenHu", "Hi");
        c.put("Leeroy", "Dog");
        System.out.println(c.get("HelenHu"));
        System.out.println(c.get("Leeroy"));
        c.put("Kai", "Hi should be evicted");
        System.out.println(c.get("HelenHu"));
        System.out.println(c.get("Leeroy"));
    }
}
