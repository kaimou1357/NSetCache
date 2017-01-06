import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Kai Mou on 1/5/2017.
 */
public class MRUStrategy implements ReplacementStrategyInterface {
    @Override
    public void replace(HashMap<Object, LinkedCacheSet.Node> map, LinkedList<LinkedCacheSet.Node> linkedList, int capacity){
        LinkedCacheSet.Node beginning = linkedList.get(0);
        map.remove(beginning.key);
        linkedList.remove(beginning);
    }
}
