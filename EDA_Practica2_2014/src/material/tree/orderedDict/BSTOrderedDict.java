package material.tree.orderedDict;

import java.util.ArrayList;
import java.util.Comparator;
import material.tree.Position;
import material.tree.bst.LinkedBinarySearchTree;


/**
 * Realization of a dictionary by means of a binary search tree.
 * 
 * @author Michael Goodrich, Eric Zamore
 */

// Realization of a dictionary by means of a binary search tree
public class BSTOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {
	
	public BSTOrderedDict() {
		super();
	}
	
	public BSTOrderedDict(Comparator<K> keyComparator) {
		super(keyComparator);
	}
	
	protected LinkedBinarySearchTree<Entry<K,V>> createTree (){
		return new LinkedBinarySearchTree<Entry<K,V>>();		
	}
	
        @Override
        public Iterable<Entry<K, V>> findRange(K minkey, K maxkey) throws InvalidKeyException {  //anyadido
        Entry <K,V> entry1 = new DictEntry <K,V> (minkey, null,null);
        Entry <K,V> entry2 = new DictEntry <K,V> (maxkey, null,null);
        Iterable<Position<Entry<K, V>>> aux =  this.bsTree.findRange(entry1, entry2);
        
        ArrayList<Entry<K, V>> aux2 = new ArrayList<Entry<K, V>> ();
        
        for (Position<Entry<K, V>> e: aux) {
            aux2.add(e.getElement());
        }
        
        return aux2;
    }
}

