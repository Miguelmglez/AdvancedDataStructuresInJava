package material.tree;

import java.util.*;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import material.tree.exceptions.BoundaryViolationException;
import material.tree.exceptions.EmptyTreeException;
import material.tree.exceptions.InvalidPositionException;
import material.tree.exceptions.NonEmptyTreeException;

public class LCRSTree<E> implements Tree<E> {

    private LCRSTreeNode<E> root; // reference to the root
    private int size; // number of nodes

    private class LCRSTreeNode<T> implements Position<T> {

        private T element;
        private LCRSTreeNode<T> parent;
        private LCRSTreeNode<T> sibling;
        private LCRSTreeNode<T> child;

        @Override
        public T getElement() {
            return element;
        }

        public LCRSTreeNode(T element, LCRSTreeNode<T> parent,
                LCRSTreeNode<T> sibling, LCRSTreeNode<T> child) {
            setElement(element);
            setParent(parent);
            setSibling(sibling);
            setChild(child);
        }

        public LCRSTreeNode<T> getParent() {
            return parent;
        }

        public void setParent(LCRSTreeNode<T> parent) {
            this.parent = parent;
        }

        public LCRSTreeNode<T> getSibling() {
            return sibling;
        }

        public void setSibling(LCRSTreeNode<T> sibling) {
            this.sibling = sibling;
        }

        public LCRSTreeNode<T> getChild() {
            return child;
        }

        public void setChild(LCRSTreeNode<T> child) {
            this.child = child;
        }

        public void setElement(T element) {
            this.element = element;
        }

    }

    private class LCRSTreeIterator<T> implements Iterator<Position<T>> {

        private Queue<LCRSTreeNode<T>> nodeQueue = new ArrayDeque<LCRSTreeNode<T>>();

        private LCRSTreeIterator(LCRSTreeNode<T> root) {
            nodeQueue.add(root);
        }

        @Override
        public boolean hasNext() {
            return (nodeQueue.size() != 0);
        }

        /**
         * This method visits the nodes of a tree by following a breath-first
         * search
         */
        @Override
        // NO SE RECORRE DE LA MISMA MANERA YA QUE LA ESTRUCTURA ES DIFERENTE
        public Position<T> next() {  //Modificado
            
            LCRSTreeNode<T> aux = nodeQueue.remove();
            LCRSTreeNode<T> aux2 = aux.getChild();  //Me situo en los demas elementos 
            while (aux2 != null) {   //Mientras que no este vacia la lista de nodos
                nodeQueue.add(aux2);  //Los añado a la cola de nodos
                aux2 = aux2.getSibling();  //Accedo a su hermano y lo guardo en el mismo para recorrerlos todos
            }
            return aux;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /**
     * Returns an iterable collection of the children of a node.
     */
    @Override
    public Iterable<? extends Position<E>> children(Position<E> v)
            throws InvalidPositionException {      //Modificado
        LCRSTreeNode<E> pos = checkPosition(v);
        List<Position<E>> children = new ArrayList<>();

        if (isLeaf(v)) {
            throw new InvalidPositionException("External nodes have no children");
        }

        LCRSTreeNode<E> posChild = pos.getChild();   //Accedo al hijo del puntero dado
        while (posChild != null) {
            children.add(posChild);
            posChild = posChild.getSibling();

        }

        return children;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        return !isLeaf(v);
    }

    @Override
    // REVISAR
    public boolean isLeaf(Position<E> p) throws InvalidPositionException {
        LCRSTreeNode<E> node = checkPosition(p);
        return (node.getChild() == null);
    }

    @Override
    public boolean isRoot(Position<E> p) throws InvalidPositionException {
        LCRSTreeNode<E> node = checkPosition(p);
        return (node == this.root());
    }

    @Override
    public Position<E> parent(Position<E> p) throws InvalidPositionException,
            BoundaryViolationException {
        LCRSTreeNode<E> node = checkPosition(p);
        Position<E> parentPos = (Position<E>) node.getParent();
        if (parentPos == null) {
            throw new BoundaryViolationException("No parent");
        }
        return parentPos;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if (root == null) {
            throw new EmptyTreeException("The tree is empty");
        }
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<E>> it = new LCRSTreeIterator<E>(this.root);
        return it;
    }

    private LCRSTreeNode<E> checkPosition(Position<E> p)
            throws InvalidPositionException {
        if (p == null || !(p instanceof LCRSTreeNode)) {
            throw new InvalidPositionException("The position is invalid");
        }
        return (LCRSTreeNode<E>) p;
    }

    /**
     * Replaces the element at a node.
     */
    public E replace(Position<E> p, E e) throws InvalidPositionException {
        LCRSTreeNode<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    /**
     * Adds a root node to an empty tree
     */
    public Position<E> addRoot(E e) throws NonEmptyTreeException {
        if (!isEmpty()) {
            throw new NonEmptyTreeException("Tree already has a root");
        }
        size = 1;
        root = new LCRSTreeNode<E>(e, null, null, null);
        //Nuevo arbol (elem, padre a null, hermano a null, hijo a ??
        return root;
    }

    private void preOrderAux(Position<E> p, List<Position<E>> pos)
            throws InvalidPositionException {
        pos.add(p);
        for (Position<E> w : children(p)) {
            preOrderAux(w, pos); // recurse on each child
        }
    }

    /**
     * Creates a collection storing the the nodes in the subtree of a node,
     * ordered according to the preorder traversal of the subtree.
     */
    public Iterable<Position<E>> preOrder() {
        List<Position<E>> positions = new ArrayList<Position<E>>();
        if (this.size != 0) {
            preOrderAux(this.root, positions);
        }
        return positions;
    }

    private void posOrderAux(Position<E> p, List<Position<E>> pos)
            throws InvalidPositionException {
        for (Position<E> w : children(p)) {
            posOrderAux(w, pos); // recurse on each child
        }
        pos.add(p);
    }

    /**
     * Creates a collection storing the the nodes in the subtree of a node,
     * ordered according to the preorder traversal of the subtree.
     */
    public Iterable<Position<E>> posOrder() {
        List<Position<E>> positions = new ArrayList<Position<E>>();
        if (this.size != 0) {
            posOrderAux(this.root, positions);
        }
        return positions;
    }

    /**
     * Swap the elements at two nodes
     */
    public void swapElements(Position<E> p1, Position<E> p2)
            throws InvalidPositionException {
        LCRSTreeNode<E> node1 = checkPosition(p1);
        LCRSTreeNode<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
    }

    /**
     * Add a new node whose parent is pointed by a given position.
     *
     * @param p The position of the parent, e the element stored in the new
     * created node.
     * @throws InvalidPositionException
     */
    public Position<E> add(E element, Position<E> p) {   //Modificado
        LCRSTreeNode<E> parent = checkPosition(p);
        LCRSTreeNode<E> newNode = new LCRSTreeNode<E>(element, parent, null, null);
        LCRSTreeNode<E> pAux;  //puntero auxiliar

        if (parent.getChild() == null) {  //Si padre no tiene hijos, se añade sin mas
            parent.setChild(newNode);
            newNode.setParent(parent);
        } else if (parent.getChild() != null) {  //Si padre tiene hijos, recorremos los hijos y ponemos el nuevo 
            //elemento como ultimo hermano 
            pAux = parent.getChild();   //Pongo el puntero auxiliar apuntando al hijo del padre dado
            if (pAux.getSibling() == null) {  //Si no tiene mas hermanos, le añado como hermano de este
                pAux.setSibling(newNode);
            } else {  //En caso contrario (tiene mas hermanos)
                while (pAux.getSibling() != null) {  //Mientras tenga hermanos recorro hasta el ultimo
                    pAux = pAux.getSibling();   //Avanzamos el puntero hermano a hermano
                }
                pAux.setSibling(newNode);   //Cuando no tenga mas hermanos, lo añado REVISAR
            }
        }
        size++;
        return (Position<E>) newNode;
    }

    /**
     * Remove a node and its corresponding subtree rooted at node.
     *
     * @param p The position of the node to be removed.
     * @throws InvalidPositionException
     */
    public void remove(Position<E> p) throws InvalidPositionException {  //Modificado
        LCRSTreeNode<E> node = checkPosition(p);
        if (node.getParent() != null) {  //Si el nodo tiene padre, recorro desde el nodo
            LCRSTreeIterator<E> it = new LCRSTreeIterator<>(node);
            int cont = 0;  //En el contador guardo el numero de elementos que hay por debajo del nodo
            while (it.hasNext()) {
                it.next();
                cont++;
            }  //Al eliminar el nodo, se van a eliminar todos sus hijos y demas elementos por debajo
            size = size - cont;  //por lo que esta es la forma mas rapida de eliminar y actualizar el tamaño

            LCRSTreeNode<E> hijo = node.getChild();
            LCRSTreeNode<E> padre = node.getParent();
            LCRSTreeNode<E> hermano = node.getSibling();
            if ((hijo != null) && (hijo.getSibling() != null)) {  //Si nodo tiene hijos e hijo tiene hermano
                hijo.setParent(padre);  //Hijo de nodo a eliminar pasa a tener como padre al padre del nodo a eliminar
                hijo.getSibling().setSibling(hermano);
                //hermano del Hijo del nodo a eliminar pasa a tener como hermano al hermano nodo a eliminar

            } else if ((hijo == null) && (hermano != null)) {  //Si no tiene hijos pero sí hermanos
                hermano.setParent(padre);
            } else if ((hijo != null) && (hijo.getSibling() == null)) { //Si nodo tiene hijos e hijo no tiene hermano
                hijo.setParent(padre);
                hijo.setSibling(hermano); //Hermano del hijo va a ser hermano del nodo a eliminar
            }
            node.setParent(null);  //Poniendo todos sus punteros a null, se elimina el nodo automaticamente
            node.setChild(null);
            node.setSibling(null);
            node.setElement(null);

        } else {
            this.root = null;
            this.size = 0;
        }
    }

    public Position<E> moveSubtree(Position<E> pOrig, Position<E> pDest) throws InvalidPositionException {
        //Creado
        LCRSTreeNode<E> origen = checkPosition(pOrig);
        LCRSTreeNode<E> destino = checkPosition(pDest);
        LCRSTreeNode<E> pAux, pAux2;  //Puntero auxiliar
        LCRSTreeNode<E> pAuxHijo, pAuxHermano;

        if (origen.getParent() == null) {
            throw new InvalidPositionException("The position is root");
        } else {
            pAux2 = origen.getParent(); //Puntero al padre
            pAuxHijo = pAux2.getChild();  //Puntero al hijo del padre
            pAuxHermano = pAuxHijo.getSibling();
            
            if (pAuxHijo == origen) {                
                pAux2.setChild(origen.getSibling());
            } else {
                while (pAuxHermano != origen) {
                    pAuxHijo = pAuxHermano;  //puntero al hermano anterior
                    pAuxHermano = pAuxHermano.getSibling();  //Sigo recorriendo hermanos                
                }
                pAuxHijo.setSibling(origen.getSibling());
            }
            origen.setParent(destino);
            origen.setSibling(null);

            if (destino.getChild() == null) {  //Si destino no tiene hijos, se añade sin mas
                destino.setChild(origen);
            } else {  //Caso contrario (tiene hijos). Lo añadimos como hijo de destino y los hijos que ya tenia, pasan como hermanos de origen
                pAux = destino.getChild();  //Puntero auxiliar al hijo de destino
                while (pAux.getSibling() != null) {  //Recorro los hermanos del hijo de destino hasta que no haya mas
                    pAux = pAux.getSibling();
                }
                pAux.setSibling(origen);  //Cuando no haya mas, el siguiente hermano sera el nodo origen
                
            }
        }
        return origen;   //Devuelvo el nodo origen 
    }

}
