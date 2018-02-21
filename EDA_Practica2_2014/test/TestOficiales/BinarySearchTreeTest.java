package TestOficiales;

import java.util.Iterator;
import material.tree.Position;
import material.tree.bst.BinarySearchTree;
import material.tree.bst.LinkedBinarySearchTree;
import static org.junit.Assert.*;

import org.junit.Test;

public class BinarySearchTreeTest {

	@Test
	public void testSize() {
		LinkedBinarySearchTree <Integer> b = new LinkedBinarySearchTree <Integer>();
		assertEquals(b.size(), 0);
		b.insert(5);
		assertEquals(b.size(), 1);		
		b.insert(10);
		assertEquals(b.size(), 2);
		
		for (int cont = 0; cont < 25; cont++)
			b.insert(cont);
	
		assertEquals(b.size(), 27);		
	}

	@Test
	public void testFind() {
		BinarySearchTree <Integer> b = new LinkedBinarySearchTree <Integer>();
		
		for (int cont = 0; cont < 25; cont+=2)
			b.insert(cont);

		b.insert(17);

		Position <Integer> p = b.find(17);
		assertEquals(p.getElement().intValue(),17);
		p = b.find(2);
		assertEquals(p.getElement().intValue(),2);
		p = b.find(13);
		assertEquals(p,null);
	}

	@Test
	public void testInsert() {
		BinarySearchTree <Integer> b = new LinkedBinarySearchTree <Integer>();
		b.insert(5);
		b.insert(3);
		b.insert(6);
		b.insert(7);
		b.insert(1);
		b.insert(6);

		Iterator <Position <Integer>> i = b.iterator();
		
		String salida = "";
		while(i.hasNext()) {
			salida += i.next().getElement().toString();
                }
		assertEquals(salida, "531676");
	}

	@Test
	public void testRemove() {
		BinarySearchTree <Integer> b = new LinkedBinarySearchTree <Integer>();
		b.insert(5);
		Position <Integer> p = b.insert(3);
		b.insert(7);
		b.insert(1);
		b.insert(6);
		b.remove(p);
		
		
		Iterator <Position <Integer>> i = b.iterator();
		
		String salida = "";
		while(i.hasNext()) {
			salida += i.next().getElement().toString();
                }
		assertEquals(salida, "5176");

	}

}
