package material.maps;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import material.tree.maps.HashTableMap;

public class HashTableMapTestQP11 extends TestCase {
	
	private HashTableMapQP<String, Integer> listin;
	
	
	
	public void defaultMap(){
		
	}

	public void testSize() {
		listin = new HashTableMapQP<String, Integer>();	
		assertEquals(listin.size(), 0);
		
		
		listin.put("Jose", new Integer(912127659));
		listin.put("Angel", new Integer(912127658));
		listin.put("Abraham", new Integer(912127657));
		listin.put("Mayte", new Integer(912127656));
		listin.put("Raul", new Integer(912127655));	
		listin.put("Jose", new Integer(1));
		listin.put("Angel", new Integer(2));
		listin.put("Abraham", new Integer(3));
		
		this.defaultMap();
		assertEquals(listin.size(), 5);

		listin.remove("Angel");
		listin.remove("Mayte");
		assertEquals(listin.size(), 3);
	}

	
	public void testIsEmpty() {
		HashTableMap<String, Integer> listin = new HashTableMapQP<String, Integer>();
		assertEquals(listin.isEmpty(), true);
		listin.put("Jose", new Integer(912127654));
		assertEquals(listin.isEmpty(), false);
		listin.remove("Jose");
		assertEquals(listin.isEmpty(), true);
	}

	
	public void testPutAndGet() {
                HashTableMap<String, Integer> listin = new HashTableMapQP<String, Integer>();

		try {
			listin.get(null);
			fail("Deberia lanzar: InvalidKeyException");

		} catch (InvalidKeyException e) {
			// OK
		}

		try {
			listin.put(null, new Integer(1213123));
			fail("Deberia lanzar: InvalidKeyException");

		} catch (InvalidKeyException e) {
			// OK
		}
		
		assertEquals(listin.get("Jose"), null);
				
		listin.put("Jose", new Integer(912127654));
		listin.put("Jose", new Integer(912127651));
		listin.put("Andres", new Integer(912127624));
		assertEquals(listin.size(), 2);
		assertEquals(listin.get("Jose"), new Integer(912127651));
		assertEquals(listin.get("Andres"), new Integer(912127624));
	}

	
	public void testRemove() {
		AbstractHashTableMap<String, Integer> listin = new HashTableMapQP<String, Integer>();
		listin.put("Jose", new Integer(912127651));
		listin.put("Andres", new Integer(912127624));
		listin.remove("Andres");
		assertEquals(listin.size(), 1);
		assertEquals(listin.get("Jose"), new Integer(912127651));
		assertEquals(listin.get("Andres"), null);
		
		try {
			listin.remove(null);
			fail("Deberia lanzar: InvalidKeyException");

		} catch (InvalidKeyException e) {
			// OK
		}
	}

	
	public void testValues() {
		AbstractHashTableMap<String, Integer> listin = new HashTableMapQP<String, Integer>();
		listin.put("Angel", new Integer(912127654));
		listin.put("Jose", new Integer(912127651));
		listin.put("Andres", new Integer(912127624));
		Iterable<Integer> values = listin.values();
		Iterator<Integer> it = values.iterator();
		
		ArrayList<Integer> l = new ArrayList<Integer>();

		
		while(it.hasNext()){
			l.add(it.next());		
		}
		
		
		assertEquals(l.size(), 3);
		assertEquals(l.contains(new Integer(912127654)), true);
		assertEquals(l.contains(new Integer(912127651)), true);
		assertEquals(l.contains(new Integer(912127624)), true);
	}

	
	public void testEntries() {
		AbstractHashTableMap<String, Integer> listin = new HashTableMapQP<String, Integer>();
		listin.put("Angel", new Integer(912127654));
		listin.put("Jose", new Integer(912127651));
		listin.put("Andres", new Integer(912127624));
		Iterable<Entry<String,Integer>> entries = listin.entries();
		ArrayList<Entry<String,Integer>> l = new ArrayList<Entry<String,Integer>> ();
		
		
		for (Entry<String,Integer> i : entries) {
			l.add(i);
		}
		
		assertEquals(l.size(), 3);
		
		Iterator<Entry<String,Integer>> it = listin.iterator();
		
		while(it.hasNext()){
			Entry<String,Integer> e = it.next();
			assertEquals(l.contains(e), true);
		}
	}
	
	
	public void testKeys() {
		AbstractHashTableMap<String, Integer> listin = new HashTableMapQP<String, Integer>();
		listin.put("Angel", new Integer(912127654));
		listin.put("Jose", new Integer(912127651));
		listin.put("Andres", new Integer(912127624));
		Iterable<String> keys = listin.keys();		
		
		
		ArrayList<String> l = new ArrayList<String> ();

		for (String k : keys) {
			l.add(k);
		}
		
		assertEquals(l.size(), 3);
		
		Iterator<Entry<String,Integer>> it = listin.iterator();
		
		while(it.hasNext()){
			String s = it.next().getKey();
			assertEquals(l.contains(s), true);
		}
	}


	
	public void testRehash() {
		HashTableMapQP<Integer, Integer> listin1 = new HashTableMapQP<Integer, Integer>(10);

		final int NUM_ENTRIES = 1000;

		// Testing size
		for (int cont = 0; cont < NUM_ENTRIES; cont++) {
			listin1.put(cont, cont);
		}
		assertEquals(listin1.size(), NUM_ENTRIES);

		// Testing elements
		listin1 = new HashTableMapQP<Integer, Integer>(10);
		int cont2, cont;
		for (cont = 1; cont <= NUM_ENTRIES; cont++) {
			listin1.put(cont, cont);
			cont2 = 1;
			while ((listin1.get(cont2) != null) && (cont2 <= cont)) {
				cont2++;
			}
			assertEquals(cont2, cont + 1);
		}
	}
}
