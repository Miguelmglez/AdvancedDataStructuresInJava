import java.util.LinkedList;
import material.tree.maps.HashTableMapSC;
import material.tree.maps.InvalidKeyException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HashTableMapSCTest {   //Funcionan todos!!
    
    public HashTableMapSCTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
	public void testSize() {
		HashTableMapSC<String, Integer> listin = new HashTableMapSC<String, Integer>();
		assertEquals(listin.size(), 0);

		listin.put("Jose", new Integer(912127659));
		listin.put("Angel", new Integer(912127658));
		listin.put("Abraham", new Integer(912127657));
		listin.put("Mayte", new Integer(912127656));
		listin.put("Raul", new Integer(912127655));
		assertEquals(listin.size(), 5);

		listin.remove("Angel");
		listin.remove("Mayte");
		assertEquals(listin.size(), 3);
	}

	@Test
	public void testIsEmpty() {
		HashTableMapSC<String, Integer> listin = new HashTableMapSC<String, Integer>();
		assertEquals(listin.isEmpty(), true);
		listin.put("Jose", new Integer(912127654));
		assertEquals(listin.isEmpty(), false);
		listin.remove("Jose");
		assertEquals(listin.isEmpty(), true);
	}

	@Test
	public void testPutAndGet() {
		HashTableMapSC<String, Integer> listin = new HashTableMapSC<String, Integer>();

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

	@Test
	public void testRemove() {
		HashTableMapSC<String, Integer> listin = new HashTableMapSC<String, Integer>();
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

	@Test
	public void testValues() {
		HashTableMapSC<String, Integer> listin = new HashTableMapSC<String, Integer>();
		listin.put("Angel", new Integer(912127654));
		listin.put("Jose", new Integer(912127651));
		listin.put("Andres", new Integer(912127624));
		Iterable<Integer> v = listin.values();
		LinkedList<Integer> l = new LinkedList<Integer>();
		for (Integer i : v) {
			l.add(i);
		}
		assertEquals(l.size(), 3);
		assertEquals(l.contains(new Integer(912127654)), true);
		assertEquals(l.contains(new Integer(912127651)), true);
		assertEquals(l.contains(new Integer(912127624)), true);
	}

	@Test
	public void testRehash() {
		HashTableMapSC<Integer, Integer> listin1 = new HashTableMapSC<Integer, Integer>(10);

		final int NUM_ENTRIES = 1000;

		// Testing size
		for (int cont = 0; cont < NUM_ENTRIES; cont++) {
			listin1.put(cont, cont);
		}
		assertEquals(listin1.size(), NUM_ENTRIES);

		// Testing elements
		listin1 = new HashTableMapSC<Integer, Integer>(10);
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
