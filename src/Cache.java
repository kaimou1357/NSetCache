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
    private static Cache singleCache = null;
    private int nWay;
    private int size;
    private String replacementAlgo;
    private ArrayList<CacheSet> cacheStorage;

    private Cache(int association, int numEntries){
        if ((numEntries % association)!=0){
            System.out.println("Number of elements chosen will not fit properly with N sized set blocks");
        }

        this.nWay = association;
        this.size = numEntries;
        this.replacementAlgo = "LRU";
        cacheStorage = new ArrayList<>();
        for (int i = 0; i<size/nWay; i++){
            cacheStorage.add(new CacheSet(nWay, replacementAlgo));
        }
    }

    private Cache(int association, int numEntries, String replacementAlgo){
        if ((numEntries % association)!=0){
            System.out.println("Number of elements chosen will not fit properly with N sized set blocks");
        }
        this.nWay = association;
        this.size = numEntries;
        this.replacementAlgo = replacementAlgo;
        cacheStorage = new ArrayList<>();
        for (int i = 0; i<size/nWay; i++){
            cacheStorage.add(new CacheSet(nWay, replacementAlgo));
        }
    }

    public static Cache getStandardInstance(int association, int numEntries){
        if (singleCache == null){
            singleCache = new Cache(association, numEntries);
        }
        return singleCache;
    }

    public static Cache getInstanceWithCustomAlgo(int association, int numEntries, String replacementAlgo){
        if (singleCache == null){
            singleCache = new Cache(association, numEntries, replacementAlgo);
        }
        return singleCache;
    }

    public <T> T get(T key){
        int keySet = getHashMapIndex(getHash(key));
        System.out.println(keySet);
//        CacheSet tempCache = cacheStorage.get(keySet);
//        Object returnedValue = tempCache.get(key);
//        return (T)returnedValue;
        return null;
    }

    public <T> void put(T key, T value){
//        int keySet = getHashMapIndex(getHash(key));
//        //Grab the LinkedHashMap where the key should belong
//        CacheSet tempCache = cacheStorage.get(keySet);
//        tempCache.put(key, value);

    }

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

    private int byteArrayToInt(byte[] arr){
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }
    private void clear(){
        for(int i = 0; i < cacheStorage.size(); i++){
            cacheStorage.set(i, new CacheSet(nWay, replacementAlgo));
        }
    }
    private int getHashMapIndex(int intKey){
        return (intKey % nWay) + size;
    }
}
