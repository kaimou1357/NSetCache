/**
 * Created by Kai Mou on 12/31/2016.
 */
public class ClientCacheSet extends CacheSet{
    /**
     * Default constructor for each CacheSet. Default replacement algorithm should be LRU.
     * @param nWay
     */
    public ClientCacheSet(int nWay){
        super(nWay, "LRU");
    }

    /**
     * Custom constructor for CacheSet. Client can pass in either "MRU", "LRU", or any other value that they want.
     * @param nWay
     * @param replacementAlgo
     */
    public ClientCacheSet(int nWay, String replacementAlgo){
        super(nWay, replacementAlgo.toUpperCase());
    }

    /**
     * Client can override CustomReplacement Algorithm here for determining which entries to evict from the set.
     */
    @Override
    public void CustomReplacement(){
        //client can implement their own CustomReplacement algorithm here.
    }
}
