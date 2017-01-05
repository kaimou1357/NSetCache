import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kai Mou on 12/27/2016.
 * Unit Testing class for the NSet Associative Cache for The Trade Desk
 */
public class CacheTestCases {
    public static void main(String[] args){
        testBasicReadWrite();
        testUpdateSameKey();
        testLRU();
        testMRU();
        System.out.println("All test cases passed!");
    }

    @Test
    public static void testBasicReadWrite(){
        ClientCache cache = new ClientCache(2, 8);
        cache.put("Leeroy", "Jenkins");
        cache.put("Kai", "Mou");
        Assert.assertEquals(cache.get("Leeroy"), "Jenkins");
        Assert.assertEquals(cache.get("Kai"), "Mou");

    }

    @Test
    public static void testUpdateSameKey(){
        ClientCache cache = new ClientCache(2, 8);
        cache.put(16, "Kai");
        cache.put(16, "Mou");
        Assert.assertEquals(cache.get(16), "Mou");

    }

    @Test
    public static void testLRU(){
        ClientCache cache = new ClientCache(2, 2);
        //create a 2 way cache with max of two entries. Should only be 1 set.
        cache.put("Leeroy", "Jenkins");
        cache.put("Kai", "Mou");

        cache.put("Extra", "Hi");
        //If LRU, "Jenkins" should be evicted.
        Assert.assertNull(cache.get("Leeroy"));
        Assert.assertEquals(cache.get("Kai"), "Mou");
    }

    @Test
    public static void testMRU(){
        ClientCacheSet set = new ClientCacheSet(2, "MRU");
        ClientCache cache = new ClientCache(2,2, set);

        cache.put("Leeroy", "Jenkins");
        cache.put("Kai", "Mou");

        cache.put("Extra", "Hi");
        //if MRU, "Mou" should be evicted
        Assert.assertNull(cache.get("Kai"));
        Assert.assertEquals(cache.get("Leeroy"), "Jenkins");

    }



}
