package material.tree.bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import material.tree.exceptions.InvalidPositionException;
import material.tree.Position;

/** 
 * Realization of a red-black Tree by extending a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */

/** Realization of a dictionary by means of a red-black tree. */
public class RBTree<E> implements BinarySearchTree<E> {

//Esta clase es necesaria para guardar el valor de la altura AVL en los nodos BTNodes
	
    private class RBInfo<T> implements Comparable<RBInfo<T>>, Position<T>{

    	private boolean isRed; // we add a color field to a BTNode
        private T element;
        private Position<RBInfo<T>> pos;

        RBInfo(T element) {
            this.element = element;
        }
        
        public void setTreePosition(Position<RBInfo<T>> pos){
        	this.pos = pos;
        }
        
        public Position<RBInfo<T>> getTreePosition(){
        	return this.pos;
        }
    	public boolean isRed() {
    		return isRed;
    	}

    	public void setRed() {
    		isRed = true;
    	}

    	public void setBlack() {
    		isRed = false;
    	}

    	public void setColor(boolean color) {
    		isRed = color;
    	}

        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(RBInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable <T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);
                
            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }
    }
    

    private class RBTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<RBInfo<T>>> it;

        public RBTreeIterator(Iterator<Position<RBInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<RBInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }
    
    private LinkedBinarySearchTree<RBInfo<E>> bst = new LinkedBinarySearchTree<>();
    private ReestructurableBinaryTree<RBInfo<E>> resBT = new ReestructurableBinaryTree<>();
    
    public RBTree(){
    	bst.binTree = resBT;
    }
    
    @Override
    public Position<E> find(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value);
        Position<RBInfo<E>> output = bst.find(searchedValue);

        if (output == null) {
            return null;
        }
        return output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
    	RBInfo<E> searchedValue = new RBInfo<>(value);
    	List<Position<E>> aux = new ArrayList<> ();
        for (Position<RBInfo<E>> n : bst.findAll(searchedValue)){
			aux.add(n.getElement());
        }
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

   
    @Override
    public int size() {
        return bst.size();
    }

	/**
	 * Inserts an element into the RBTree and returns the newly created postion.
	 */
	public Position<E> insert(E e) {
        RBInfo<E> aux = new RBInfo<>(e);

		Position<RBInfo<E>> posZ = bst.insert(aux);
		aux.setTreePosition(posZ);
		aux.setRed();
		
		if (this.bst.binTree.isRoot(posZ))
			aux.setBlack();
		else
			remedyDoubleRed(aux); // fix a double-red color violation
		return aux;
	}

	/** Remedies a double red violation at a given node caused by insertion. */
	protected void remedyDoubleRed(RBInfo<E> nodeZ) {
		Position<RBInfo<E>> posV = this.bst.binTree.parent(nodeZ.getTreePosition());
		RBInfo<E> nodeV = posV.getElement(); 
		
		if (this.bst.binTree.isRoot(posV))
			return;
		if (!nodeV.isRed())
			return;
		// we have a double red: posZ and posV
		Position<RBInfo<E>> uncleZ = this.bst.binTree.sibling(posV);
		final boolean blackUncle = (uncleZ.getElement() == null) || (!uncleZ.getElement().isRed);
		if (blackUncle) { // Case 1: trinode restructuring
			posV = this.resBT.restructure(nodeZ.getTreePosition(), this.bst);
			posV.getElement().setBlack();
			this.bst.binTree.left(posV).getElement().setRed();
			this.bst.binTree.right(posV).getElement().setRed();
		} else { // Case 2: recoloring
			nodeV.setBlack();
			this.bst.binTree.sibling(posV).getElement().setBlack();;
			Position<RBInfo<E>> posU = this.bst.binTree.parent(posV);
			if (this.bst.binTree.isRoot(posU))
				return;
			RBInfo<E> nodeU = posU.getElement();
			nodeU.setRed();
			remedyDoubleRed(nodeU);
		}
	}

	/** Removes and returns an entry from the dictionary. */
	public E remove(Position<E> posV) throws InvalidPositionException {
		RBInfo<E> nodeV = this.checkPosition(posV); // may throw an InvalidPositionException
		boolean isRed = nodeV.isRed();
		
		E toReturn = posV.getElement(); // entry to be returned
		
		Position<RBInfo<E>> posW = this.bst.getLeafToRemove(nodeV.getTreePosition());
		Position<RBInfo<E>> posR = this.bst.binTree.sibling(posW);
		RBInfo<E> nodeR = posR.getElement();
		final boolean isRedNodeR = (nodeR != null) && (nodeR.isRed());
		
		this.bst.removeLeaf(posW);
		
		
		if (!isRed){
			remedyDoubleBlack(posR);
		}
		
		

//		if (wasParentRed || this.bst.binTree.isRoot(posR) || isRedNodeR){
//			if (isRedNodeR)
//				nodeR.setBlack();
//		}
//		else {
//			remedyDoubleBlack(posR);
//		}
		
		return toReturn;
	}
        
        

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(Position<RBInfo<E>> posR) {
		boolean oldColor;
		Position<RBInfo<E>>  posZ;
		RBInfo<E>  nodeZ;
		Position<RBInfo<E>> posX = this.bst.binTree.parent(posR);
		RBInfo<E> nodeX = posX.getElement();
		Position<RBInfo<E>> posY = this.bst.binTree.sibling(posR);
		RBInfo<E> nodeY = posY.getElement();
				
		final boolean blackNodeY = (nodeY == null) || (!nodeY.isRed());
		
		if (blackNodeY) {
			posZ = redChild(posY);
			if (posZ != null) { // Case 1: trinode restructuring
				oldColor = nodeX.isRed(); 
				posZ = this.resBT.restructure(posZ, this.bst);
				posZ.getElement().setColor(oldColor);
				if (posR.getElement() != null)
					posR.getElement().setBlack();
				this.bst.binTree.left(posZ).getElement().setBlack();
				this.bst.binTree.right(posZ).getElement().setBlack();
				return;
			}
			if (posR.getElement() != null)
				posR.getElement().setBlack();
			nodeY.setRed();
			if (!nodeX.isRed()) { // Case 2: recoloring
				if (!this.bst.binTree.isRoot(posX))
					remedyDoubleBlack(posX);
				return;
			}
			nodeX.setBlack();
			return;
		} // Case 3: adjustment
		if (posY == this.bst.binTree.right(posX))
			posZ = this.bst.binTree.right(posY);
		else
			posZ = this.bst.binTree.left(posY);
		this.resBT.restructure(posZ, this.bst);
		nodeY.setBlack();
		nodeX.setRed();
		remedyDoubleBlack(posR);
	}


	/** Returns a red child of a node. */
	protected Position<RBInfo<E>> redChild(Position<RBInfo<E>> pos) {
		Position<RBInfo<E>> child = this.bst.binTree.left(pos);
		final boolean redLeftChild = child.getElement() != null && child.getElement().isRed();
		if (redLeftChild){
			return child;
		}
		
		child = this.bst.binTree.right(pos);
		final boolean redRightChild = child.getElement() != null && child.getElement().isRed();
		if (redRightChild){
			return child;
		}
		return null;
	}

	
	
	@Override
	public Iterator<Position<E>> iterator() {
		Iterator<Position<RBInfo<E>>> bstIt = bst.iterator();
		RBTreeIterator <E> it = new RBTreeIterator<>(bstIt);
		return it;		
	}
	
	/** If v is a good tree node, cast to RBPosition, else throw exception */
	 /**
     * If v is a good tree node, cast to TreePosition, else throw exception
     */
    private RBInfo<E> checkPosition(Position<E> p) throws InvalidPositionException {
        if (p == null) {
            throw new InvalidPositionException("The position of the RB node is null");
        } else if (!(p instanceof RBInfo)) {
            throw new InvalidPositionException("The position of the RB node is not RB");
        } else {
        	RBInfo<E> aux = (RBInfo<E>) p;
            return aux;
        }
    }

    @Override
    public Iterable <Position<E>> findRange (E minValue, E maxValue) {
         RBInfo<E> min = new RBInfo<E>(minValue);
         RBInfo<E> max = new RBInfo<E>(maxValue);
         
         Iterable <Position<RBInfo<E>>> output = bst.findRange(min, max);
         
         List<Position<E>> l = new ArrayList<Position<E>>();
         for (Position<RBInfo<E>> aux: output) {
             l.add(new RBInfo (aux));
         }
         
         return l;
       
    }
}