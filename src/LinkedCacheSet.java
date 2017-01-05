import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Kai Mou on 1/4/2017.
 */
public class LinkedCacheSet {
    private final int capacity;
    private final String replacementAlgo;
    private HashMap<Object, Node> map = new HashMap<Object, Node>();
    private LinkedList<Node> linkedList = new LinkedList<Node>();

    class Node{
        Object key;
        Object value;
        public Node(Object key, Object value){
            this.key = key;
            this.value = value;
        }
    }

    public LinkedCacheSet(int capacity){
        this.capacity = capacity;
        this.replacementAlgo = "LRU";
    }
    public LinkedCacheSet(int capacity, String replacementAlgo){
        this.capacity = capacity;
        this.replacementAlgo = replacementAlgo;
    }

    public LinkedCacheSet makeCopy(){
        return new LinkedCacheSet(this.capacity, this.replacementAlgo);
    }

    /**
     * This method can be overriden to have a client create their own replacement algorithm. Retrieves an item from the cache and moves it to the front of the LinkedList.
     * @param key
     * @return
     */
    public Object get(Object key){
        Node returnValue = null;
        if (map.containsKey(key)){
            returnValue = map.get(key);
            linkedList.add(0,returnValue);
            linkedList.remove(returnValue);
            //move the object from wherever it is to the front of the LinkedList.
        }

        return returnValue == null ? null : returnValue.value;
    }

    /**
     * This method puts a key/value and evicts depending on the replacement algorithm selected.
     * @param key
     * @param value
     */
    public void put(Object key, Object value){
        if (map.containsKey(key)){
            //if the key already exists, modify the value and bring it back to the front of the list.
            Node old = map.get(key);
            old.value = value;
            linkedList.remove(old);
            linkedList.add(0, old);
        }
        else{
            Node newNode = new Node(key, value);
            if(map.size() >= capacity){
                if (replacementAlgo.equals("LRU")){
                    Node end = linkedList.get(capacity-1);
                    map.remove(end.key);
                    linkedList.remove(end);
                }
                else if (replacementAlgo.equals("MRU")){
                    Node beginning = linkedList.get(0);
                    map.remove(beginning.key);
                    linkedList.remove(beginning);
                }


            }
            map.put(key, newNode);
            linkedList.add(0,newNode);
        }
    }

    public boolean containsKey(Object key){
        return map.containsKey(key);
    }

    public boolean hasSpace(){
        return map.size() < capacity;
    }

}
