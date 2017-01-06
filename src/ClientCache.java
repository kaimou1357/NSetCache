/**
 * Created by Kai Mou on 12/27/2016.
 */
public class ClientCache extends Cache{

    /**
     * Default ClientCache constructor where user can specify the set association and total number of entries.
     * @param nWay Number of blocks in a set. Should be a power of 2
     * @param numEntries Total number of entries user would like to the cache to hold.
     */
    public ClientCache(int nWay, int numEntries){
        super(nWay, numEntries);
    }

    /**
     * Custom ClientCache takes in a ClientCacheSet where the client can override replacement methods.
     * @param nWay Number of blocks in a set. Should be a power of 2
     * @param numEntries Total number of entries user would like to the cache to hold.
     * @param clientReplacementStrategy CacheSet should specify replacement algorithm and can override replacement methods.
     */
    public ClientCache(int nWay, int numEntries, ReplacementStrategyInterface clientReplacementStrategy){
        super(nWay, numEntries, clientReplacementStrategy);
    }
}
