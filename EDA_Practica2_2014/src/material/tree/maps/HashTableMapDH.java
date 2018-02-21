
package material.tree.maps;

/* @param <K> Key type
 * @param <V> Value type
 */

public class HashTableMapDH<K, V> extends HashTableMap<K, V> {

    protected HashEntryIndex findEntry(K key) throws InvalidKeyException {
        int q = capacity / 2;//tamaño de la tabla / 2
        int avail = -1;
        checkKey(key);
        int inicial = hashValue(key);
        int pos = inicial;
        int i = 0; //contador

        do {
            Entry<K, V> e = bucket[pos];
            if (e == null) {
                if (avail < 0) {
                    avail = pos; // key is not in table
                }
                break;
            } else if (key.equals(e.getKey())) // we have found our key
            {
                return new HashEntryIndex(pos, true); // key found
            } else if (e == AVAILABLE) { // bucket is deactivated
                if (avail < 0) {
                    avail = pos; // remember that this slot is available
                }
            }

            i++;
            int d = q - (key.hashCode() % q);
            pos = (inicial + (i * d)) % this.capacity;  //Esto es lo que cambia
        } while (i != capacity); //paramos cuando hemos mirado todas las posiciones
        return new HashEntryIndex(avail, false); // first empty or available slot
    }
}
