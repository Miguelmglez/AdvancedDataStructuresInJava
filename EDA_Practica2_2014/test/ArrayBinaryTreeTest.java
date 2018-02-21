import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import material.tree.Position;
import material.tree.binarytree.ArrayBinaryTree;
import material.tree.binarytree.BinaryTreeUtils;
import material.tree.binarytree.LinkedBinaryTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ArrayBinaryTreeTest {

    @Test
    public void testIsEmpty() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(100);
        assertEquals(t.isEmpty(), true);
    }

    @Test
    public void addRootTest() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(100);
        assertEquals(0, t.size());
        Position<String> root = t.addRoot("1");
        assertEquals(1, t.size());
    }

    @Test
    public void testSize() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(100);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        t.insertRight(h, "5");
        assertEquals(t.size(), 5);
    }

    @Test
    public void testHasLeft() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(100);
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        assertEquals(t.hasLeft(p), true);
        assertEquals(t.hasLeft(q), false);
    }

    @Test
    public void testPositions() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(100);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        t.insertRight(p, "3");
        String salida = "";
        List<Position<String>> l = new ArrayList<>();
        t.preorderPositions(p, l);
        for (Position<String> e : l) {
            salida += e.getElement();
        }
        assertEquals(salida, "+23");
    }

    @Test
    public void testRemove() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(10);
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        //t.insertRight(h,"5");
        t.remove(h);

        String salida = "";
        List<Position<String>> l = new ArrayList<>();
        t.preorderPositions(p, l);
        for (Position<String> e : l) {
            System.out.println(e.getElement());
            salida += e.getElement();
        }
        assertEquals(salida, "+23");
        assertEquals(t.size(), 3);
    }

    @Test
    public void testRemoveInternal() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(10);
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> b = t.insertLeft(h, "-");
        t.insertRight(b, "3");
        t.insertRight(h, "5");
        t.remove(b);
        String salida = "";
        List<Position<String>> l = new ArrayList<>();
        t.preorderPositions(p, l);
        for (Position<String> e : l) {
            salida += e.getElement();
        }
        assertEquals(salida, "+2*35");
        assertEquals(t.size(), 5);
    }

    @Test
    public void testSwapElements() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(10);
        Position<String> p = t.addRoot("+");
        Position<String> p1 = t.insertLeft(p, "2");
        Position<String> p2 = t.insertRight(p, "3");
        t.swapElements(p1, p2);
        String salida = "";
        List<Position<String>> l = new ArrayList<>();
        t.preorderPositions(p, l);
        for (Position<String> e : l) {
            salida += e.getElement();
        }
        assertEquals(salida, "+32");
    }
    
    @Test
    public void testAttachABT(){   //Va a saltar excepcion porque ya hay hijo izquierdo
        ArrayBinaryTree<Integer> tree = new ArrayBinaryTree<>(10000);
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.insertLeft(p, 2);
        Position<Integer> p2 = tree.insertRight(p, 3);
        Position<Integer> p3 = tree.insertLeft(p1, 4);
        Position<Integer> p4 = tree.insertRight(p1, 5);
        Position<Integer> p5 = tree.insertLeft(p2, 6);
        Position<Integer> p6 = tree.insertRight(p2, 7); 
        
        ArrayBinaryTree<Integer> tree2 = new ArrayBinaryTree<>(1000);
        Position<Integer> q = tree2.addRoot(1);
        Position<Integer> q1 = tree2.insertLeft(q, 2);
        Position<Integer> q2 = tree2.insertRight(q, 3);
        
        ArrayBinaryTree<Integer> tree3 = new ArrayBinaryTree<>(1000);
        Position<Integer> r = tree3.addRoot(11);
        Position<Integer> r1 = tree2.insertLeft(r, 12);
        Position<Integer> r2 = tree2.insertRight(r, 13);
        
        tree.attach(p3, tree2, tree3);
        assertEquals(tree.hasLeft(p3), true);
        assertEquals(tree.hasRight(p3), true);  
    }

    @Test
    public void testRemoveSubtree() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<>(10);
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> b = t.insertLeft(h, "-");
        t.insertRight(b, "3");
        t.insertRight(h, "5");
        t.removeSubtree(p);
        String salida = "";
        if (!t.isEmpty()) {
            List<Position<String>> l = new ArrayList<>();
            t.preorderPositions(p, l);
            for (Position<String> e : l) {
                salida += e.getElement();
            }
            assertEquals(salida, "");
            assertEquals(t.size(), 0);
        }
    }
    
    
	public void testMirrorArrayBT() {
		ArrayBinaryTree <Integer> t = new ArrayBinaryTree <Integer>(10000);
                BinaryTreeUtils<Integer> but = new BinaryTreeUtils<>();
		Position <Integer> p = t.addRoot(5);
		Position <Integer> q = t.insertLeft(p, 4);
		Position <Integer> h = t.insertRight(p, 6); 
		List<Integer> lista = new ArrayList<>();
                lista.add(0, 5);
                lista.add(1, 6);
                lista.add(2, 4);
                List<Integer> lista2 = new ArrayList<>();
                ArrayBinaryTree<Integer> espejo = new ArrayBinaryTree<>(10000);
                espejo = (ArrayBinaryTree<Integer>) but.mirror(t);
                Iterator<Position<Integer>> it = espejo.iterator();
		while(it.hasNext()) {
                    lista2.add(it.next().getElement());
                }
		assertEquals(lista2, lista);	
	}
        
        @Test
	public void testContainsArrayBT() {
		ArrayBinaryTree <Integer> t = new ArrayBinaryTree <>(10);
                BinaryTreeUtils<Integer> but = new BinaryTreeUtils<>();
		Position <Integer> p = t.addRoot(4);
		t.insertLeft(p, 1);
		Position <Integer> h = t.insertRight(p, 6);
                Position <Integer> h1 = t.insertLeft(h, 5);
		assertEquals(but.contains(t, 5), true);	
	}
        
        @Test
	public void testLevelArrayBT() {
            ArrayBinaryTree <Integer> t = new ArrayBinaryTree <>(10);	
            BinaryTreeUtils<Integer> but = new BinaryTreeUtils<>();
		Position <Integer> p = t.addRoot(5);
		Position <Integer> q = t.insertLeft(p, 4);
		Position <Integer> h = t.insertRight(p, 6);
		
                
		assertEquals(but.level(t, q) , 2);
		
	}
}

