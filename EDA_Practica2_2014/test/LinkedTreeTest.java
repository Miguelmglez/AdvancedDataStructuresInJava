
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import material.tree.LinkedTree;
import material.tree.Position;
import material.tree.TreeUtils;
import static org.junit.Assert.*;
import org.junit.Test;

public class LinkedTreeTest {

    @Test
    public void testSize() {
        LinkedTree<String> t = new LinkedTree<>();
        Position<String> p = t.addRoot("+");
        t.add("2", p);
        Position<String> h = t.add("*", p);
        t.add("3", h);
        t.add("5", h);
        assertEquals(t.size(), 5);
    }

    @Test
    public void testIsEmpty() {
        LinkedTree<String> t = new LinkedTree<>();
        assertEquals(t.isEmpty(), true);
    }

    @Test
    public void testRemove() {
        LinkedTree<String> t = new LinkedTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> q = t.add("2", p);
        Position<String> h = t.add("*", p);
        t.add("3", h);
        t.add("5", h);
        t.remove(h);
        assertEquals(t.size(), 2);

    }

    @Test
    public void testSwapElements() {
        LinkedTree<String> t = new LinkedTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> q = t.add("2", p);
        Position<String> h = t.add("*", p);
        t.add("3", q);
        t.add("5", h);
        t.swapElements(p, q);

        if (!t.isEmpty()) {
            String salida = "";
            Iterator<Position<String>> it = t.iterator();

            while (it.hasNext()) {
                salida += it.next().getElement();
            }
            assertEquals(t.size(), 5);
            assertEquals(salida, "2+*35");
        }
    }

    
    
    @Test
    public void testMoveSubTree() {
        LinkedTree<String> t = new LinkedTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> q = t.add("2", p);
        Position<String> h = t.add("*", p);
        Position<String> r = t.add("3", q);
        Position<String> s = t.add("5", h);
        t.moveSubtree(q, s);

        if (!t.isEmpty()) {
            String salida = "";
            Iterator<Position<String>> it = t.iterator();

            while (it.hasNext()) {
                salida += it.next().getElement();
            }
            assertEquals(t.size(), 5);
            assertEquals(salida, "+*523");

        }

    }

    @Test
    public void testMotrar() {
        LinkedTree<String> t = new LinkedTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> q = t.add("2", p);
        Position<String> h = t.add("*", p);
        Position<String> r = t.add("3", q);
        Position<String> s = t.add("5", h);

        Iterator<Position<String>> it = t.iterator();
        String salida = "";
        while (it.hasNext()) {
            salida += it.next().getElement();
            System.out.println(salida);
        }
        assertEquals(t.size(), 5);
        assertEquals(salida, "+2*35");
    }
    
    @Test
    public void testLinkedFront(){
        LinkedTree<Integer> tree = new LinkedTree<>();
        TreeUtils<Integer> ut = new TreeUtils<>();
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.add(2, p);
        Position<Integer> p2 = tree.add(3, p);
        Position<Integer> p3 = tree.add(4, p1);
        Position<Integer> p4 = tree.add(5, p2);
        Position<Integer> p5 = tree.add(6, p2);
        Position<Integer> p6 = tree.add(7, p3);  
        List<Position<Integer>> listaHojas = new ArrayList<>();
        listaHojas.add(p4);
        listaHojas.add(p5);
        listaHojas.add(p6);
        
        assertEquals(ut.front(tree),listaHojas);
    }
    
    @Test
    public void testLinkedDepth(){
        LinkedTree<Integer> tree = new LinkedTree<>();
        TreeUtils<Integer> ut = new TreeUtils<>();
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.add(2, p);
        Position<Integer> p2 = tree.add(3, p);
        Position<Integer> p3 = tree.add(4, p1);
        Position<Integer> p4 = tree.add(5, p2);
        Position<Integer> p5 = tree.add(6, p2);
        Position<Integer> p6 = tree.add(7, p3);  
        
        assertEquals(ut.depth(tree), 4);  
    }
    
    @Test
    public void testLinkedDegree(){
        LinkedTree<Integer> tree = new LinkedTree<>();
        TreeUtils<Integer> ut = new TreeUtils<>();
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.add(2, p);
        Position<Integer> p2 = tree.add(3, p);
        Position<Integer> p3 = tree.add(4, p1);
        Position<Integer> p4 = tree.add(5, p2);
        Position<Integer> p5 = tree.add(6, p2);
        Position<Integer> p6 = tree.add(7, p3); 
        
        assertEquals(ut.degree(tree),2);   
    }  
    
}

