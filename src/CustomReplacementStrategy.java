import com.petebevin.markdown.Replacement;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Kai Mou on 1/5/2017.
 */
public class CustomReplacementStrategy implements ReplacementStrategyInterface {
    @Override
    public void replace(HashMap<Object, LinkedCacheSet.Node> map, LinkedList<LinkedCacheSet.Node> linkedList, int capacity){
        //clients can implement their own replacement strategy here...
        //Maybe try a random replacement strategy?... :)
    }
}
