package TestOficiales;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import material.tree.Position;
import material.tree.binarytree.ArrayBinaryTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArrayBinaryTreeTest {

    @Test
    public void testSize() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(100);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        t.insertRight(h, "5");
        assertEquals(t.size(), 5);
    }

    @Test
    public void testIsEmpty() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(100);
        assertEquals(t.isEmpty(), true);
    }

    @Test
    public void testHasLeft() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(100);
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        assertEquals(t.hasLeft(p), true);
        assertEquals(t.hasLeft(q), false);
    }

    @Test
    public void testPositions() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(100);
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        t.insertRight(p, "3");
        List<Position<String>> l = new ArrayList<>();
        t.preorderPositions(p, l);
        String salida = "";
        for (Position<String> e : l) {
            salida += e.getElement();
        }
        assertEquals(salida, "+23");
    }

    @Test
    public void testRemove() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(10);
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        t.insertRight(h, "5");
        t.remove(q);

        List<Position<String>> l = new ArrayList<>();
        t.preorderPositions(p, l);
        String salida = "";

        for (Position<String> e : l) {
            System.out.println(e.getElement());
            salida += e.getElement();
        }
        assertEquals(salida, "+*35");
        assertEquals(t.size(), 4);
    }

    @Test
    public void testRemoveInternal() {
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(100);
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
        ArrayBinaryTree<String> t = new ArrayBinaryTree<String>(100);
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

}
