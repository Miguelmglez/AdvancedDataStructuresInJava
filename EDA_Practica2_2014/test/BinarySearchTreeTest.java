import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import material.tree.Position;
import material.tree.bst.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class BinarySearchTreeTest {  //funcionan todos

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
		LinkedBinarySearchTree <Integer> b = new LinkedBinarySearchTree <Integer>();
		b.insert(5);
		b.insert(3);
		b.insert(6);
		b.insert(7);
		b.insert(1);
		b.insert(2);

		Iterator <Position <Integer>> i = b.iterator();
		
		String salida = "";
		while(i.hasNext()) {
			salida += i.next().getElement().toString();
                }
		assertEquals(salida, "536172");
	}

	@Test
	public void testRemove() {
		LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree <Integer>();
		b.insert(5);
		Position <Integer> p = b.insert(3);
		b.insert(7);
		b.insert(1);
		b.insert(6);
		b.remove(p);
		
		
		Iterator<Position<Integer>> i =  b.iterator();
		
		String salida = "";
                while(i.hasNext()){
                    salida += i.next().getElement().toString();
                }
		assertEquals(salida, "5176");

	}
        
            @Test
    public void testFindRange() {
        LinkedBinarySearchTree<Integer> b = new LinkedBinarySearchTree<>();
        b.insert(5);
        b.insert(3);
        b.insert(6);
        b.insert(7);
        b.insert(1);
        b.insert(4);
        String salida = "";
        Iterable<Position<Integer>> i = b.findRange(2, 5);
        for (Position<Integer> e : i) {
            salida += e.getElement().toString();            
        }
        //recorrido en anchura 536147
        assertEquals(salida, "534");            
    }
    
    @Test
        public void testAVLTreeAutoComplete(){
            AVLTree <String> b = new AVLTree <String>();
            b.insert("cocina");
            b.insert("comida");
            b.insert("comision");
            Ejercicio2_BinarySearch autoc = new Ejercicio2_BinarySearch();
            ArrayList<String> lista = new ArrayList<>();
            lista.add("comida");
            lista.add("comision");
            List<String> lista2 = new ArrayList<>();
            lista2 = (List<String>) autoc.autoComplete(b, "com");
            
            assertEquals(lista2, lista);
        }
        
    
}
