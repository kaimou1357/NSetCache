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
    private int size;
    private int totalNumSets;
    private String replacementAlgo;
    private ArrayList<CacheSet> cacheStorage;

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
        this.size = numEntries;
        if ((numEntries % nWay)!=0){
            this.totalNumSets = numEntries/nWay + 1;
            System.out.println(totalSetCount);
        }
        else{
            this.totalNumSets = numEntries/nWay;
        }
        //If user does not pass in replacement algorithm, default to LRU
        this.replacementAlgo = "LRU";
        cacheStorage = new ArrayList<>();
        for (int i = 0; i<totalNumSets; i++){
            cacheStorage.add(new CacheSet(this.nWay, replacementAlgo));
        }
    }

    /**
     * A custom constructor for the cache class. Allows user to specify what kind of replacement algorithm the Cache should use.
     * @param nWay
     * @param numEntries
     * @param replacementAlgo
     */
    public Cache(int nWay, int numEntries, String replacementAlgo){
        if (nWay %2 != 0){
            System.out.println("Please enter a N that is a power of 2!");
            System.exit(1);
        }
        int totalSetCount = 0;
        this.nWay = nWay;
        this.size = numEntries;
        if ((numEntries % nWay)!=0){
            this.totalNumSets = numEntries/nWay + 1;
        }

        else{
            this.totalNumSets = numEntries/nWay;
        }

        this.replacementAlgo = replacementAlgo;
        cacheStorage = new ArrayList<>();
        for (int i = 0; i< totalNumSets; i++){
            cacheStorage.add(new CacheSet(this.nWay, replacementAlgo));
        }
    }

    /**
     * Retrieves an item from the Cache
     * @param key Key can be any object
     * @param <T>
     * @return Returns the object that is mapped to the key
     */
    public <T> T get(T key){

        int keySet = getHashMapIndex(getHash(key));

        CacheSet tempCache = cacheStorage.get(keySet);
        Object returnedValue = tempCache.get(key);
        if (returnedValue == null){
            //make a dummy function call to fetch the object from memory.
        }
        return (T)returnedValue;
    }

    /**
     * Puts an item in the Cache
     * @param key Key can be any Java object
     * @param value value can be any Java object
     * @param <T>
     */
    public <T> void put(T key, T value){

        int keySet = getHashMapIndex(getHash(key));
        //Grab the LinkedHashMap where the key should belong
        CacheSet tempCache = cacheStorage.get(keySet);
        tempCache.put(key, value);

    }

    /**
     * Method generates an integer of an object using an object's memory address.
     * @param key
     * @param <T>
     * @return Integer representation of an object's memory address
     */
    private <T> int getHash(T key){
        byte[] keyBytes = null;
        try{
            keyBytes = key.toString().getBytes("UTF-8");
        } catch(UnsupportedEncodingException e){
            System.out.println("GetBytes() could not decode the string properly");
        }

        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        byte[] hashBytes = md.digest(keyBytes);
        return byteArrayToInt(hashBytes);

    }

    /**
     *Generates the integer from a bytearray of the key's memory address following the Little Endian byte convention
     * @param arr
     * @return
     */
    private int byteArrayToInt(byte[] arr){
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return Math.abs(buffer.getInt());
    }

    /**
     *
     * @param intKey Given any arbitrary number from an Object's key, return the place in the array that the object's key matches.
     * @return an index of the ArrayList where we can find each individual Cache Set.
     */
    private int getHashMapIndex(int intKey){
        return (intKey % (size/nWay));
    }
}
