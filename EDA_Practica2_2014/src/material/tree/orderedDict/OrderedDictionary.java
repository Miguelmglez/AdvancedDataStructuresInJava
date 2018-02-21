package material.tree.orderedDict;


/**
 * An interface for a dictionary storing (key-value) pairs.
 * 
 * @author Michael Goodrich
 */

public interface OrderedDictionary<K, V> {

	/** Returns the number of entries in the dictionary. */
	public int size();

	/** Returns whether the dictionary is empty. */
	public boolean isEmpty();

	/**
	 * Returns an entry containing the given key, or <tt>null</tt> if no such
	 * entry exists.
	 */
	public Entry<K, V> find(K key) throws InvalidKeyException;

	/**
	 * Returns an iterator containing all the entries containing the given key,
	 * or an empty iterator if no such entries exist.
	 */
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException;

	/**
	 * Inserts an item into the dictionary. Returns the newly created entry.
	 */
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException;

	/** Removes and returns the given entry from the dictionary. */
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException;
        
        /**Find range in ordered diccionaries. */
        public Iterable<Entry<K, V>> findRange (K minkey, K maxkey) throws InvalidKeyException;

//
//	/** Returns an iterator containing all the entries in the dictionary. */
//	public Iterable<Entry<K, V>> entries();
}

