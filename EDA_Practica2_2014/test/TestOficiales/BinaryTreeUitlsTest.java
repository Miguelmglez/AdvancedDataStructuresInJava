package TestOficiales;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import junit.framework.TestCase;
import material.tree.Position;
import material.tree.TreeUtils;
import material.tree.binarytree.ArrayBinaryTree;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.BinaryTreeUtils;
import material.tree.binarytree.LinkedBinaryTree;

/**
 *
 * @author Abraham Duarte
 */
public class BinaryTreeUitlsTest extends TestCase {
	private ArrayBinaryTree<String> abt = new ArrayBinaryTree<>(100);
	private LinkedBinaryTree<String>  lbt = new LinkedBinaryTree<>();


	public void setTree_ABT() {

		this.abt = new ArrayBinaryTree <>(100);
		Position <String> p = this.abt.addRoot("1");
		Position <String> p1 = this.abt.insertLeft(p, "2");
		Position <String> p2 = this.abt.insertRight(p, "3");
		this.abt.insertLeft(p1, "4");
		this.abt.insertRight(p1, "5");
		this.abt.insertLeft(p2, "6");
		this.abt.insertRight(p2, "7");
	}

	
	public void setTree_LBT() {

		lbt = new LinkedBinaryTree <>();
		Position <String> p = lbt.addRoot("A");
		Position <String> p1 = lbt.insertLeft(p, "B");
		Position <String> p2 = lbt.insertRight(p, "C");
		lbt.insertLeft(p1, "D");
		lbt.insertRight(p1, "E");
		lbt.insertLeft(p2, "F");
		lbt.insertRight(p2, "G");
	}

	public void testMirror_LBT() {
		this.setTree_LBT();
		BinaryTree<String> mirroredTree =  BinaryTreeUtils.mirror(this.lbt);
		StringBuilder s = new StringBuilder();
		
		Iterator<Position<String>> it = mirroredTree.iterator();
		while(it.hasNext()){
			Position<String> p = it.next();
			s.append(p.getElement());
			
		}
		assertEquals(s.toString(), "ACBGFED");
	}
	
	public void testMirror_ABT() {
		this.setTree_ABT();
		BinaryTree<String> mirroredTree =  BinaryTreeUtils.mirror(this.abt);
		StringBuilder s = new StringBuilder();
		
		Iterator<Position<String>> it = mirroredTree.iterator();
		while(it.hasNext()){
			Position<String> p = it.next();
			s.append(p.getElement());
			
		}
		assertEquals(s.toString(), "1327654");
	}
	
	public void testLevel_LBT() {
		lbt = new LinkedBinaryTree <>();
		Position <String> p = lbt.addRoot("A");		
		Position <String> p1 = lbt.insertLeft(p, "B");
		Position <String> p2 = lbt.insertRight(p, "C");
		lbt.insertLeft(p1, "D");
		lbt.insertRight(p1, "E");
		lbt.insertLeft(p2, "F");
		Position <String> p3 = lbt.insertRight(p2, "G");		
		int i1 =  BinaryTreeUtils.level(this.lbt, p);
		assertEquals(i1, 0);
		
		i1 =  BinaryTreeUtils.level(this.lbt, p1);
		assertEquals(i1, 1);
		
		i1 =  BinaryTreeUtils.level(this.lbt, p3);
		assertEquals(i1, 2);
	}
	
	public void testLevel_ABT() {
		abt = new ArrayBinaryTree <>(100);
		Position <String> p = abt.addRoot("1");		
		Position <String> p1 = abt.insertLeft(p, "2");
		Position <String> p2 = abt.insertRight(p, "3");
		abt.insertLeft(p1, "4");
		abt.insertRight(p1, "5");
		abt.insertLeft(p2, "6");
		Position <String> p3 = abt.insertRight(p2, "7");		
		int i1 =  BinaryTreeUtils.level(this.abt, p);
		assertEquals(i1, 0);
		
		i1 =  BinaryTreeUtils.level(this.abt, p1);
		assertEquals(i1, 1);
		
		i1 =  BinaryTreeUtils.level(this.abt, p3);
		assertEquals(i1, 2);
	}
	
	
	
	public void testContains_LBT() {
		this.setTree_LBT();
                
		boolean a =  BinaryTreeUtils.contains(this.lbt, "F");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.lbt, "A");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.lbt, "B");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.lbt, "C");
		assertEquals(a, true);

		a =  BinaryTreeUtils.contains(this.lbt, "D");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.lbt, "X");
		assertEquals(a, false);
	}
	
	
	public void testContains_ABT() {
		this.setTree_ABT();
		boolean a =  BinaryTreeUtils.contains(this.abt, "1");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.abt, "2");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.abt, "3");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.abt, "4");
		assertEquals(a, true);

		a =  BinaryTreeUtils.contains(this.abt, "5");
		assertEquals(a, true);
		
		a =  BinaryTreeUtils.contains(this.abt, "0");
		assertEquals(a, false);
	}
}