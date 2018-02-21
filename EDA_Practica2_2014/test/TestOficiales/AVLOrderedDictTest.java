package TestOficiales;



import java.util.TreeSet;
import material.tree.orderedDict.AVLOrderedDict;
import material.tree.orderedDict.Entry;
import static org.junit.Assert.*;

import org.junit.Test;

public class AVLOrderedDictTest {

	@Test
	public void testSize() {
		AVLOrderedDict <String,Integer> dict = new AVLOrderedDict <String,Integer>();
		assertEquals(dict.size(),0);
		dict.insert("Angel", 9151592);
		assertEquals(dict.size(),1);
		dict.insert("Angel", 9151591);
		assertEquals(dict.size(),2);
		dict.insert("Jose",  9100000);	
		assertEquals(dict.size(),3);
	}

	@Test
	public void testIsEmpty() {
		AVLOrderedDict <String,Integer> dict = new AVLOrderedDict <String,Integer>();
		assertEquals(dict.isEmpty(),true);
		dict.insert("Angel", 9151592);
		assertEquals(dict.isEmpty(),false);
	}

	@Test
	public void testFind() {
		AVLOrderedDict <String,Integer> dict = new AVLOrderedDict <String,Integer>();
		dict.insert("Angel", 9151592);
		dict.insert("Angel", 9151591);
		dict.insert("Jose",  9100000);
		Entry <String,Integer> contacto = dict.find("Angel");
		assertEquals(contacto.getValue().intValue(),9151591);		
	}

	@Test
	public void testFindAll() {
		AVLOrderedDict <String,Integer> dict = new AVLOrderedDict <String,Integer>();
		int [] telefono = {9151592,9151591,9151593};		
		dict.insert("Angel", telefono[0]);
		dict.insert("Angel", telefono[1]);
		dict.insert("Jose",  telefono[2]);
		TreeSet <Integer> cjtoTelefonos = new TreeSet <Integer>();
		for (int cont = 0; cont < 3; cont++)
			cjtoTelefonos.add(telefono[cont]);
		
		Iterable<Entry <String,Integer>> it = dict.findAll("Angel");
		for (Entry <String,Integer> contacto : it) {
			assertEquals(cjtoTelefonos.contains(contacto.getValue()),true);		
		}
	}

	@Test
	public void testInsert() {
		AVLOrderedDict <Integer,Integer> dict = new AVLOrderedDict <Integer,Integer>();
		for (int cont = 0; cont < 1000; cont++) {
			dict.insert((int)(Math.random()*1000), cont);
		}
	}

	@Test
	public void testRemove() {
		AVLOrderedDict <String,Integer> dict = new AVLOrderedDict <String,Integer>();
		int [] telefono = {9151592,9151591,9151593};		
		dict.insert("Angel", telefono[0]);
		dict.insert("Angel", telefono[1]);
		dict.insert("Jose",  telefono[2]);
		Entry <String,Integer> e1 = dict.find("Jose");
		dict.remove(e1);
		Entry <String,Integer> f1 = dict.find("Jose");
		assertEquals(f1,null);		
		assertEquals(dict.size(),2);		
		Entry <String,Integer> e2 = dict.find("Angel");
		dict.remove(e2);
		assertEquals(dict.size(),1);		
		Entry <String,Integer> e3 = dict.find("Angel");
		dict.remove(e3);
		assertEquals(dict.size(),0);
	}

	@Test
	public void testRemoveUno() {
		AVLOrderedDict <String,Integer> dict = new AVLOrderedDict <String,Integer>();
		dict.insert("Angel", 9151592);
		Entry <String,Integer> e = dict.find("Angel");
		dict.remove(e);
		Entry <String,Integer> f = dict.find("Angel");
		assertEquals(f,null);		
	}

	@Test
	public void testRemoveDos() {
		AVLOrderedDict <String,Integer> dict = new AVLOrderedDict <String,Integer>();
		dict.insert("Jose", 9151590);
		dict.insert("Angel", 9151592);
		dict.insert("Angel", 9151591);
		Entry <String,Integer> e0 = dict.find("Jose");
		Entry <String,Integer> e1 = dict.find("Angel");
		Entry <String,Integer> e2 = dict.find("Angel");

		dict.remove(e0);
		dict.remove(e1);
		dict.remove(e2);
		Entry <String,Integer> f = dict.find("Angel");
		assertEquals(f,null);		
	}
	
	
}
