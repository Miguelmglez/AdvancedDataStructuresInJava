
package material.tree.maps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class HashTableMapSC<K, V> implements Map<K, V> {

    /**
     * @param <T> Key type
     * @param <U> Value type
     *
     */
    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {

            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }

        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

    private class HashTableMapSCIterator<T, U> implements Iterator<Entry<T, U>> {     

        private ArrayDeque<HashEntry<T,U>> nodeQueue = new ArrayDeque<HashEntry<T,U>>();

        public HashTableMapSCIterator(List<HashEntry<T, U>>[] b){
            for(List<HashEntry<T,U>> listEntry: b){
                if(listEntry!=null){
                    for(HashEntry<T,U> entry: listEntry)
                        nodeQueue.add(entry);
                }
            }
        }

        @Override
        public boolean hasNext(){
            return (!nodeQueue.isEmpty());
        }

        @Override
        public Entry<T, U> next(){
            return nodeQueue.remove();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
        
    }

    private class HashTableMapSCKeyIterator<T, U> implements Iterator<T> {

        public HashTableMapSCIterator<T, U> it;

        public HashTableMapSCKeyIterator(HashTableMapSCIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapSCValueIterator<T, U> implements Iterator<U> {

        public HashTableMapSCIterator<T, U> it;

        public HashTableMapSCValueIterator(HashTableMapSCIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    protected class HashEntryPosition {

        int index;
        boolean found;

        public HashEntryPosition(int index, boolean found) {
            super();
            this.index = index;
            this.found = found;
        }
    }

    protected int n = 0; // number of entries in the dictionary
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected List<HashEntry<K, V>>[] bucket;// bucket array
    protected final Entry<K,V> AVAILABLE = new HashEntry<>(null,null);
    protected long scale, shift; // the shift and scaling factors

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    public HashTableMapSC() {
        this(109345121, 1000); // reusing the constructor HashTableMap(int p, int cap)
    }

    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     *
     * @param cap initial capacity
     */
    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     */
    public HashTableMapSC(int cap) {
        this(109345121, cap); // reusing the constructor HashTableMap(int p, int cap)
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
        this.n = 0;
        this.prime = p;
        this.capacity = cap;
        this.bucket = new ArrayList [capacity]; // safe cast
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return the size
     */
    @Override
    public int size() {
        return n;
    }

    /**
     * Returns whether or not the table is empty.
     *
     * @return true if the size is 0
     */
    @Override
    public boolean isEmpty() {
        return (n == 0);
    }

    /**
     * Collision solved with linear probe
     *
     * @param key
     * @return
     */
    private HashEntryPosition findEntry(K key) throws InvalidKeyException {  //Modificar
        checkKey(key);
        int i = hashValue(key); // posicion que ocupa en la tabla
        if (bucket[i] != null) {//en esa posicion hay una lista
            for (HashEntry<K, V> e : bucket[i]) {//recorremos la lista
                if (e == null) {
                    break;
                } else if (key.equals(e.getKey())) // clave encontrada
                {
                    return new HashEntryPosition(i, true);
                }
            }
        }
        return new HashEntryPosition(i, false); // first empty or available slot

    }

    /**
     * Put a key-value pair in the map, replacing previous one if it exists.
     *
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) throws InvalidKeyException {  //Modificado
        HashEntryPosition i = findEntry(key); // find the appropriate spot for this entry
        V old = null; //valor que devolveremos

        if (i.found == true) { //actualizar el valor
            if (bucket[i.index] != null) { //la lista no está vacia
                for (HashEntry<K, V> e : bucket[i.index]) { //recorremos la lista
                    if (e.getKey().equals(key)) {//claves iguales
                        old = e.getValue();
                        e.setValue(value);
                    }
                }
            }
        } else {  //añadir nuevo
			
            if (bucket[i.index] == null) { //la lista estaba vacia
                bucket[i.index] = new ArrayList<HashEntry<K, V>>(); //creamos la lista
                bucket[i.index].add(new HashEntry<K, V>(key, value)); //insertamos
            } else {
                bucket[i.index].add(new HashEntry<K, V>(key, value)); //insertamos
            }
            n++; //aumentamos el tamaño
        }
        return old; //devolvemos el valor
    }

    /**
     * Returns the value associated with a key.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(K key) throws InvalidKeyException {  //Modificado
        HashEntryPosition i = findEntry(key); // helper method for finding a key
        V val = null;
        if (i.found == false) {
            return null; // there is no value for this key, so return null
        }
        if (bucket[i.index] != null) { //la clave esta en la lista
            for (HashEntry<K, V> h : bucket[i.index]) {//recorremos la lista
                if (h.getKey().equals(key)) {//comprobamos que la clave sea igual a la que buscamos
                    val = h.getValue();
                }
            }
        }
        return val;
    }

    /**
     * Removes the key-value pair with a specified key.
     *
     * @param key
     * @return
     */
    @Override
    public V remove(K key) throws InvalidKeyException {  //modificado
        HashEntryPosition i = findEntry(key); // find this key first
        if (i.found == false) {
            return null; // nothing to remove
        }
        if (bucket[i.index] != null) { //la clave a borrar esta en la lista
            for (HashEntry<K, V> e : bucket[i.index]) { //recorremos la lista
                if (e.getKey().equals(key)) { //clave encontrada
                    bucket[i.index].remove(e); //borramos
                    n--; //disminuimos el tamaño
                    return e.getValue(); //devolvemos el valor borrado
                }
            }
        }
        return null;
    }

    /**
     * Returns an iterable object containing all of the keys.
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
        return new Iterable<K>() {
            public Iterator<K> iterator() {
                return new HashTableMapSCKeyIterator<K,V>(new HashTableMapSCIterator<K,V>(bucket));
            }
        };
    }

    /**
     * Returns an iterable object containing all of the values.
     *
     * @return
     */
    @Override
    public Iterable<V> values() {
        return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new HashTableMapSCValueIterator<K, V>(new HashTableMapSCIterator<K, V>(bucket));
            }
        };
    }

    /**
     * Returns an iterable object containing all of the entries.
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
        return new Iterable<Entry<K, V>>() {
            public Iterator<Entry<K, V>> iterator() {
                return new HashTableMapSCIterator<K, V>(bucket);
            }
        };
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapSCIterator<K, V>(this.bucket);
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
        // We cannot check the second test (i.e., k instanceof K) since we do not know the class K
        if (k == null) {
            throw new InvalidKeyException("Invalid key: null.");
        }
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

}
