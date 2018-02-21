
import java.util.ArrayList;
import java.util.List;
import material.tree.*;
import material.tree.binarytree.*;
import material.tree.exceptions.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LCRSTreeTest {
    
    public LCRSTreeTest() {
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
    public void testMoveSubtreeLinked(){  //Bien
        LinkedTree<Integer> tree = new LinkedTree<>();
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.add(2, p);
        Position<Integer> p2 = tree.add(3, p);
        Position<Integer> p3 = tree.add(4, p1);
        Position<Integer> p4 = tree.add(5, p2);
        Position<Integer> p5 = tree.add(6, p2);
        Position<Integer> p6 = tree.add(7, p3);      
        Position<Integer> mover = tree.moveSubtree(p1, p5);
        Position<Integer> padre_mover = tree.parent(mover); //padre de p1
        
        assertEquals(padre_mover,p5); //El nuevo padre tiene que ser el nodo destino
    } 
    
    @Test
    public void testMoveSubtreeLCRS(){  //Bien
        LCRSTree<Integer> tree = new LCRSTree<>();
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.add(2, p);
        Position<Integer> p2 = tree.add(3, p);
        Position<Integer> p3 = tree.add(4, p1);
        Position<Integer> p4 = tree.add(5, p2);
        Position<Integer> p5 = tree.add(6, p2);
        Position<Integer> p6 = tree.add(7, p3); 
        
        Position<Integer> mover = tree.moveSubtree(p1,p2);
        //Position<Integer> padre_mover = tree.parent(mover);  //padre de p1
        //assertEquals(padre_mover,p2);  //nuevo padre tiene que ser el nodo destino
        List<Position<Integer>> listaHijos = new ArrayList<>();
        listaHijos.add(p4);
        listaHijos.add(p5);
        listaHijos.add(p1);
        assertEquals(tree.children(p2),listaHijos);  //Compruebo que la lista de hijos esta bien
        
    }  
    
   @Test
    public void testLCRSRemove(){
        LCRSTree<Integer> tree = new LCRSTree<>();
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.add(2, p);
        Position<Integer> p2 = tree.add(3, p);
        Position<Integer> p3 = tree.add(4, p1);
        Position<Integer> p4 = tree.add(5, p2);
        Position<Integer> p5 = tree.add(6, p2);
        Position<Integer> p6 = tree.add(7, p3);  
        
        tree.remove(p1);  //El nodo p1 se borra
        Position<Integer> resultado = tree.parent(p3);
        assertEquals(resultado,p);  //Si el padre de p3 es la raiz, hemos eliminado bien
        
        //Si elimino la raiz, el arbol se tiene que quedar vacio
        tree.remove(p);
        assertEquals(tree.isEmpty(), true);
    }  
   
    @Test
    public void testLCRSChildren(){
        LCRSTree<Integer> tree = new LCRSTree<>();
        Position<Integer> p = tree.addRoot(1);
        Position<Integer> p1 = tree.add(2, p);
        Position<Integer> p2 = tree.add(3, p);
        Position<Integer> p3 = tree.add(4, p1);
        Position<Integer> p4 = tree.add(5, p2);
        Position<Integer> p5 = tree.add(6, p2);
        Position<Integer> p6 = tree.add(7, p3);
        List<Position<Integer>> listaHijos = new ArrayList<>();
        listaHijos.add(p4);
        listaHijos.add(p5);
        assertEquals(tree.children(p2), listaHijos);     
    }  
    
    @Test
    public void testLCRSFront(){
        LCRSTree<Integer> tree = new LCRSTree<>();
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
    public void testLCRSDepth(){
        LCRSTree<Integer> tree = new LCRSTree<>();
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
    public void testLCRSDegree(){
        LCRSTree<Integer> tree = new LCRSTree<>();
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
