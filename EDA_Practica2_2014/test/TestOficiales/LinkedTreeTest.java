package TestOficiales;

import java.util.Iterator;
import material.tree.LinkedTree;
import material.tree.Position;
import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedTreeTest {


	@Test
	public void testSize() {
		LinkedTree <String> t = new LinkedTree <String>();
		Position <String> p = t.addRoot("+");
		t.add("2",p);
		Position <String> h = t.add("*",p);
		t.add("3",h);
		t.add("5",h);
		assertEquals(t.size(), 5);
	}

	@Test
	public void testIsEmpty() {
		LinkedTree <String> t = new LinkedTree <String>();
		assertEquals(t.isEmpty(), true);
	}

	@Test
	public void testPositions() {
		LinkedTree <String> t = new LinkedTree <String>();
		Position <String> p = t.addRoot("+");
		t.add("2",p);
		t.add("3",p);
		String salida = "";
		Iterator <Position <String>> it = t.iterator();
		while(it.hasNext()) {
			salida += it.next().getElement().toString();
                }
		assertEquals(salida, "+23");
	}

	@Test
	public void testRemove() {
		LinkedTree <String> t = new LinkedTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> q = t.add("2",p);
		Position <String> h = t.add("*",p);
		t.add("3",h);
		t.add("5",h);
		t.remove(h);
		assertEquals(t.size(), 2);
		
	}

	@Test
	public void testSwapElements() {
		LinkedTree <String> t = new LinkedTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> p1 = t.add("2",p);
		Position <String> p2 = t.add("3",p);
		t.swapElements(p1, p2);
		String salida = "";
		Iterator <Position <String>> it = t.iterator();
		while(it.hasNext()) {
			salida += it.next().getElement().toString();
                }
		assertEquals(salida, "+32");
	}

}
