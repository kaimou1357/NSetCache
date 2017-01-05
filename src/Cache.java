import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Kai Mou on 12/27/2016.
 */
public class Cache {
    private int nWay;
    private int totalNumSets;
    private ArrayList<LinkedCacheSet> cacheStorage;

    /**
     * Most basic constructor for a NSet Cache
     * @param nWay N-Way ( 2-way, 4-way)
     * @param numEntries Capacity of the cache.
     */
    public Cache(int nWay, int numEntries){
        if (nWay %2 != 0){
            System.out.println("Please enter a N that is a power of 2!");
            System.exit(1);
        }
        int totalSetCount = 0;
        this.nWay = nWay;
        if ((numEntries % nWay)!=0){
            this.totalNumSets = numEntries/nWay + 1;
            System.out.println(totalSetCount);
        }
        else{
            this.totalNumSets = numEntries/nWay;
        }
        //If user does not pass in replacement algorithm, default to LRU
        cacheStorage = new ArrayList<>();
        for (int i = 0; i<totalNumSets; i++){
            cacheStorage.add(new LinkedCacheSet(this.nWay));
        }
    }

    /**
     * A custom constructor for the cache class. Allows user to specify what kind of replacement algorithm the Cache should use.
     * @param nWay
     * @param numEntries
     * @param customSet a CacheSet where the client can specify/override replacement algorithms
     */
    public Cache(int nWay, int numEntries, LinkedCacheSet customSet){
        if (nWay %2 != 0){
            System.out.println("Please enter a N that is a power of 2!");
            System.exit(1);
        }
        this.nWay = nWay;
        if ((numEntries % nWay)!=0){
            this.totalNumSets = numEntries/nWay + 1;
        }
        else{
            this.totalNumSets = numEntries/nWay;
        }
        cacheStorage = new ArrayList<>();
        for (int i = 0; i< totalNumSets; i++){
            cacheStorage.add(customSet.makeCopy());
        }
    }

    /**
     * Retrieves an item from the Cache. Uses linear probing to determine the next place for the HashMap.
     * @param key Key can be any object
     * @param <T>
     * @return Returns the object that is mapped to the key
     */
    public <T> T get(T key){
        Object returnedValue = null;
        int keySet = getHashMapIndex(key.hashCode());
        int startingPoint = keySet;
        if (cacheStorage.get(startingPoint).containsKey(key)){
            LinkedCacheSet tempCache = cacheStorage.get(keySet);
            returnedValue = tempCache.get(key);
        }
        else{
            for (int i = (startingPoint+1) % totalNumSets; !cacheStorage.get(i).containsKey(key) && i != startingPoint; i= (i+1) % (totalNumSets)){
                keySet = i;
            }
            LinkedCacheSet tempCache = cacheStorage.get(keySet);
            returnedValue = tempCache.get(key);
            if (returnedValue == null){
                //make a dummy function call to fetch the object from memory.
                return null;
            }
        }

        return (T)returnedValue;

    }

    /**
     * Puts an item in the Cache. Uses linear probing to place the item in the proper array index.
     * @param key Key can be any Java object
     * @param value value can be any Java object
     * @param <T>
     */
    public <T> void put(T key, T value){

        int keySet = getHashMapIndex(key.hashCode());
        int startingPoint = keySet;
        if (cacheStorage.get(startingPoint).hasSpace()){
            LinkedCacheSet tempCache = cacheStorage.get(keySet);
            tempCache.put(key, value);
            return;
        }
        else{
            for (int i = (startingPoint + 1) % totalNumSets; !cacheStorage.get(i).hasSpace() && i != startingPoint; i = (i+1) % (totalNumSets)){
                keySet = i;
            }
            LinkedCacheSet tempCache = cacheStorage.get(keySet);
            tempCache.put(key, value);
        }



    }

    /**
     * Given any arbitrary number from an Object's key, return the place in the array that the object's key matches.
     * @param intKey intKey is returned from the key's byte representation of it's memory address
     * @return an index of the ArrayList where we can find each individual Cache Set.
     */
    private int getHashMapIndex(int intKey){
        //use bitmask to ignore leading bit for positive/negative sign
        return ((intKey & 0x7fffffff) % (totalNumSets));
    }
}
