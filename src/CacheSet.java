import java.util.*;
import java.util.function.Function;

/**
 * Created by Kai Mou on 12/27/2016.
 */
public class CacheSet<T, U> extends LinkedHashMap<T, U> {
    private final int capacity;
    private final String replacementAlgo;

    public CacheSet(final int capacity, final String replacementAlgo){
        super(capacity, 0.75f, true);
        this.replacementAlgo = replacementAlgo;
        this.capacity = capacity;
    }

    /**
     * Make a new copy of the CacheSet. More of a helper function for Cache.
     * @return
     */
    public CacheSet makeCopy(){
        return new CacheSet(this.capacity, this.replacementAlgo);
    }

    /**
     * LRU replacement evicts the least recently used item.
     */
    private void LRUReplacement(){
        ArrayList<T> keys = new ArrayList<T>(keySet());
        if (keys.size() > 0){
            //if least recently used, remove the first item in the array
            this.remove(keys.get(0));
        }
    }

    /**
     * MRU replacement algorithm here evicts the most recently used item.
     */
    private void MRUReplacement(){
        ArrayList<T> keys = new ArrayList<T>(keySet());
        this.remove(keys.get(keys.size()-1));
    }

    /**
     * Custom replacement algorithm here. Currently set as default LRU.
     */
    public void CustomReplacement(){
        //this custom replacement can be overriden by the client.
        //for now, just use LRU.
        LRUReplacement();
    }

    /**
     * Custom method for putting a value in the set. Implemented this way to account for MRU and Custom replacement algorithms.
     * @param key
     * @param value
     */
    public void putCustom(T key, U value){

        if (size() + 1 > capacity){
            //if adding an item will exceed capacity, remove items based on replacement rule.
            if (replacementAlgo.equals("LRU")){
                LRUReplacement();
            }

            else if (replacementAlgo.equals("MRU")){
                //if most recently used, remove the last item in the array
                MRUReplacement();
            }

            else{
                //custom replacement function.
                CustomReplacement();
            }
        }
        put(key, value);

    }

    /**
     * A method that returns true or false depending on whether or not this set still has free blocks.
     * @return
     */
    public boolean hasSpace(){
        return size() < capacity;
    }






}
