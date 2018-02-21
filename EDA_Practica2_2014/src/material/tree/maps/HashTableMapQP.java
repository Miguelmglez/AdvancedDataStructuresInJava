
package material.tree.maps;

/* @param <K> Key type
 * @param <V> Value type
 */

public class HashTableMapQP<K, V> extends HashTableMap<K, V> {

    protected HashEntryIndex findEntry(K key) throws InvalidKeyException {
        int avail = -1;
        checkKey(key);
        int posicionInicial = hashValue(key);
        int pos = posicionInicial;
        int intento = 0; //contador

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

            intento++;
            pos = (posicionInicial + (intento * intento)) % capacity;  //Esto es lo que cambia
        } while (intento != capacity); //paramos cuando hayamos mirado todas las posiciones de la tabla
        return new HashEntryIndex(avail, false); // first empty or available slot
    }
}
