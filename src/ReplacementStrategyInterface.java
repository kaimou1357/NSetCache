import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Kai Mou on 1/5/2017.
 */
public interface ReplacementStrategyInterface {
    public void replace(HashMap<Object, LinkedCacheSet.Node> map, LinkedList<LinkedCacheSet.Node> linkedList, int capacity);
}
