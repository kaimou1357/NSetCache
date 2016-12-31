import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Kai Mou on 12/27/2016.
 */
public class CacheSet<T, U> extends LinkedHashMap<T, U> {
    private final int capacity;
    private final String replacementAlgo;

    CacheSet(final int capacity, final String replacementAlgo){
        super(capacity, 0.75f, true);
        this.replacementAlgo = replacementAlgo;
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<T, U> eldest){
        return size() > this.capacity;
    }





}
