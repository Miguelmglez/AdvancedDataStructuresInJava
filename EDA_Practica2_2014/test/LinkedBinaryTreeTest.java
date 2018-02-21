import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import material.tree.Position;
import material.tree.binarytree.*;
import material.tree.binarytree.BinaryTreeUtils;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkedBinaryTreeTest {  //Pasaaa

	@Test
	public void testSize() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		t.insertLeft(p, "2");
		Position <String> h = t.insertRight(p, "*");
		t.insertLeft(h,"3");
		t.insertRight(h,"5");
		assertEquals(t.size(), 5);
	}

	@Test
	public void testIsEmpty() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		assertEquals(t.isEmpty(), true);
	}

	@Test
	public void testPositions() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		t.insertLeft(p, "2");
		t.insertRight(p, "3");
		String salida = "";
		Iterator <Position <String>> it = t.iterator();
		while(it.hasNext()) {
			salida += it.next().getElement().toString();
                }
		assertEquals(salida, "+23");
	}

	@Test
	public void testRemove() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> q = t.insertLeft(p, "2");
		Position <String> h = t.insertRight(p, "*");
		t.insertLeft(h,"3");
		t.insertRight(h,"5");
		t.remove(q);
		assertEquals(t.size(), 4);
		
	}

	@Test
	public void testSwapElements() {
		LinkedBinaryTree <String> t = new LinkedBinaryTree <String>();
		Position <String> p = t.addRoot("+");
		Position <String> p1 = t.insertLeft(p, "2");
		Position <String> p2 = t.insertRight(p, "3");
		t.swapElements(p1, p2);
		String salida = "";
		Iterator <Position <String>> it = t.iterator();
		while(it.hasNext()) {
			salida += it.next().getElement().toString();
                }
		assertEquals(salida, "+32");
	}
        
          @Test
    public void testRemoveSubtree() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> b = t.insertLeft(h, "-");
        t.insertRight(b, "3");
        t.insertRight(h, "5");
        t.removeSubtree(p);
        String salida = "";
        if (!t.isEmpty()) {//Controlamos excepcion en caso de ser vacio
            List<Position<String>> l = new ArrayList<>();
            
            Iterator <Position <String>> it = t.iterator();
		while(it.hasNext()) {
			salida += it.next().getElement().toString();
                }
        }
        assertEquals(salida, "");
    }
    
    @Test
	public void testMirrorLinkedBT() {
            LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
            Position <Integer> p = t.addRoot(5);
            Position <Integer> q = t.insertLeft(p, 4);
            Position <Integer> h = t.insertRight(p, 6); 
            List<Integer> list = new ArrayList<>();
            BinaryTreeUtils btu = new BinaryTreeUtils();
            Iterator<Position<Integer>> it = btu.mirror(t).iterator();
            while(it.hasNext()){
                list.add(it.next().getElement());
            }
            List<Integer> lista = new ArrayList<>();
            lista.add(0, 5);
            lista.add(1, 6);
            lista.add(2, 4);
            assertEquals(list, lista);
        }   
        @Test
	public void testContainsLinkedBT() {
		LinkedBinaryTree <Integer> t = new LinkedBinaryTree <Integer>();
                BinaryTreeUtils<Integer> but = new BinaryTreeUtils<>();
		Position <Integer> p = t.addRoot(5);
		Position <Integer> q = t.insertLeft(p, 4);
		Position <Integer> h = t.insertRight(p, 6);
		
                
		assertEquals(but.contains(t, q.getElement()), true);	
	}
        
        @Test
	public void testLevelLinkedBT() {
            LinkedBinaryTree <Integer> t = new LinkedBinaryTree <Integer>();	
            BinaryTreeUtils<Integer> but = new BinaryTreeUtils<>();
		Position <Integer> p = t.addRoot(5);
		Position <Integer> q = t.insertLeft(p, 4);
		Position <Integer> h = t.insertRight(p, 6);
		
                
		assertEquals(but.level(t, q) , 2);
		
	}
       
}
