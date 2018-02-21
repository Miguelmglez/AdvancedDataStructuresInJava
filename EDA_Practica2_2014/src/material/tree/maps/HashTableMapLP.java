
package material.tree.maps;

/* @param <K> Key type
 * @param <V> Value type
 */

public class HashTableMapLP<K, V> extends HashTableMap<K, V> {

    /**
     * Collision solved with linear probe - returns index of found key or -(a +
     * 1), where a is * the index of the first empty or available slot found.
     * The index value is negative because it is needed to distinguish when the
     * key is in the table (positive) and when is not (negative)
     */
    public HashEntryIndex findEntry(K key) throws InvalidKeyException {
        int avail = -1;//la utilizaremos para comprobar que ya hemos encontrado
        checkKey(key);
        int i = hashValue(key);//posicion que ocupa en la tabla
        final int j = i;
        do {
            Entry<K, V> e = bucket[i];
            if (e == null) {
                if (avail < 0) {
                    avail = i; // key is not in table
                }
                break;
            } else if (key.equals(e.getKey())) // we have found our key
            {
                return new HashEntryIndex(i, true); // key found
            } else if (e == AVAILABLE) { // bucket is deactivated
                if (avail < 0) {
                    avail = i; // remember that this slot is available
                }
            }
            i = (i + 1) % capacity; // PRUEBA LINEAL. //Esto es lo que cambia
        } while (i != j);//paramos cuando hemos mirado todas las posiciones
        return new HashEntryIndex(avail, false); // first empty or available slot
    }
}
