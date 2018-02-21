package TestOficiales;

import java.util.Iterator;
import material.tree.Position;
import material.tree.bst.RBTree;
import static org.junit.Assert.*;

import org.junit.Test;

public class RBTreeTest {

	@Test
	public void testSize() {
		RBTree <Integer> b = new RBTree <Integer>();
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
		RBTree <Integer> b = new RBTree <Integer>();
		
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
		RBTree <Integer> b = new RBTree <Integer>();
		b.insert(5);
		b.insert(3);
		b.insert(1);
		b.insert(7);
		b.insert(6);

		
		
		String salida = "";
		Iterator <Position <Integer>> it = b.iterator();
		while(it.hasNext()) {
			salida += it.next().getElement().toString();
                }
		assertEquals(salida, "31657");
		

		b = new RBTree <Integer>();
		b.insert(4);
		b.insert(7);
		b.insert(12);
		b.insert(15);
		b.insert(3);
		b.insert(5);
		b.insert(14);
		b.insert(18);
		b.insert(16);
		b.insert(17);

		it = b.iterator();
		
		 salida = "";
		
		while(it.hasNext()) {
			salida += it.next().getElement().toString();
                }
		assertEquals(salida, "1474351216151817");

	}

	@Test
	public void testRemove() {
		RBTree <Integer> b = new RBTree <Integer>();
		b.insert(5);
		b.insert(3);
		Position <Integer> p = b.insert(1);
		b.insert(7);
		b.insert(6);
		
		b.remove(p);
				
		Iterator <Position <Integer>> i = b.iterator();
		
		String salida = "";
                while(i.hasNext()) {  //Recorro el árbol en anchura
			salida += i.next().getElement().toString();
                }
		assertEquals(salida, "5367");

		
		Position <Integer> p1, p2, p3, p4, p5, p6;
		b = new RBTree <Integer>();
		b.insert(4);
		b.insert(7);
		p2 = b.insert(12);
		p5 = b.insert(15);
		p1 = b.insert(3);
		b.insert(5);
		b.insert(14);
		p4 = b.insert(18);
		p6 = b.insert(16);
		p3 = b.insert(17);
		
		b.remove(p1);
		b.remove(p2);
		b.remove(p3);
		b.remove(p4);
		b.remove(p5);
		b.remove(p6);
		
		i = b.iterator();
		
		salida = "";
		while(i.hasNext()) {  //Recorro el árbol en anchura
			salida += i.next().getElement().toString();
                }
		assertEquals(salida, "54147");

	}

}
