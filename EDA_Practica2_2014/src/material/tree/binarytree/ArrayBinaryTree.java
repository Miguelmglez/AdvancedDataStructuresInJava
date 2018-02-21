package material.tree.binarytree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import material.tree.exceptions.BoundaryViolationException;
import material.tree.exceptions.EmptyTreeException;
import material.tree.exceptions.InvalidPositionException;
import material.tree.exceptions.NonEmptyTreeException;
import material.tree.Position;

public class ArrayBinaryTree<E> implements BinaryTree<E> {

    private class BTPos<T> implements Position<T> {

        private BTPos<E>[] arbol;
        private T element;
        private int index, left, right, parent;

        private BTPos(T e, int i) {
            element = e;
            index = i;
            left = -1;
            right = -1;
            parent = -1;
        }

        @Override
        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public int getParent() {
            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }

        public int index() {
            return index;
        }

        @Override
        public String toString() {
            return ("[" + element + "," + index + "}");
        }
    }

    private class ArrayBinaryTreeIterator<T> implements Iterator<Position<T>> {

        private Queue<BTPos<T>> nodeQueue = new ArrayDeque<>();

        private ArrayBinaryTreeIterator(BTPos<T> root) {
            nodeQueue.add(root);
        }

        @Override
        public boolean hasNext() {
            return (nodeQueue.size() != 0);
        }

        /**
         * This method visits the nodes of a arbolArray by following a
         * breath-first search
         */
        @Override
        public Position<T> next() {     //Modificado
            BTPos<T> aux = nodeQueue.remove();   //Saco un BTNode de la cola de nodos
            int rightChild = aux.getRight();        //Saco su hijo derecho 
            int leftChild = aux.getLeft();      //Saco su hijo izquierdo

            if (rightChild != -1) {    //En caso de que sea distinto de null
                BTPos<E> auxRight = arbolArray[rightChild]; //Accedo al arrayList en la posicion rightChild
                nodeQueue.add((BTPos<T>) auxRight);   //Lo añado a la cola de nodos
            } else if (leftChild != -1) {
                BTPos<E> auxLeft = arbolArray[leftChild]; //Accedo al arrayList en la posicion rightChild
                nodeQueue.add((BTPos<T>) auxLeft);    //Lo añado a la cola de nodos
            }

            return aux;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private BTPos<E>[] arbolArray;
    private List<Integer> libres;
    private int size;

    public ArrayBinaryTree(int tam) {
        arbolArray = (BTPos<E>[]) new BTPos[tam];
        this.size = 0;
        libres = new ArrayList<Integer>();
        for (int i = 0; i < tam; i++) {
            libres.add(i);
        }
    }

    @Override
    public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTPos<E> node = checkPosition(v);
        int leftPos = node.getLeft();

        if (leftPos == -1) {
            throw new BoundaryViolationException("No left child");
        }
        return arbolArray[leftPos];
    }

    @Override
    public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTPos<E> node = checkPosition(v);
        int rightPos = node.getRight();
        if (rightPos == -1) {
            throw new BoundaryViolationException("No right child");
        }
        return arbolArray[rightPos];
    }

    @Override
    public boolean hasLeft(Position<E> v) throws InvalidPositionException {
        BTPos<E> node = checkPosition(v);
        return (node.getLeft() != -1);
    }

    @Override
    public boolean hasRight(Position<E> v) throws InvalidPositionException {
        BTPos<E> node = checkPosition(v);
        return (node.getRight() != -1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public Position<E> root() throws EmptyTreeException {   //Modificado
        if (isEmpty()) {
            throw new EmptyTreeException("The tree is empty");
        } else {
            return arbolArray[0];
        }
    }

    /**
     * Returns the parent of a node.
     */
    @Override
    public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTPos<E> node = checkPosition(v);
        int parentPos = node.getParent();

        if (parentPos == -1) {
            throw new BoundaryViolationException("No parent");
        }
        return arbolArray[parentPos];
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) throws InvalidPositionException {
        //Modificado
        BTPos<E> pos = checkPosition(v);
        List<Position<E>> hijos = new ArrayList<>();
        if (hasLeft(pos)) {
            hijos.add(left(v));
        }
        if (hasRight(pos)) {
            hijos.add(right(v));
        }
        return Collections.unmodifiableCollection(hijos);
    }

    @Override
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        checkPosition(v);
        return (hasLeft(v) || hasRight(v));
    }

    @Override
    public boolean isLeaf(Position<E> v) throws InvalidPositionException {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) throws InvalidPositionException {
        checkPosition(v);
        return (v == root());
    }

    @Override
    public Iterator<Position<E>> iterator() {
        BTPos root = (BTPos) this.root();
        return new ArrayBinaryTreeIterator<>(root);
    }

    public E replace(Position<E> p, E e) throws InvalidPositionException {
        BTPos<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    /**
     * Return the sibling of a node
     */
    public Position<E> sibling(Position<E> p) throws InvalidPositionException,
            BoundaryViolationException {        //Modificado
        BTPos<E> node = checkPosition(p);
        int parentPos = node.getParent();
        BTPos<E> parent = arbolArray[parentPos];  //posicion del padre del nodo pasado

        if (parentPos != -1) {      //Si tiene padre
            BTPos<E> sibPos = new BTPos<E>(null, -1);  //Creo un nuevo BTPos donde guardar la salida
            int sibIndex; //  
            int leftPos = parent.getLeft(); //Posicion hijo izquierdo
            if (leftPos == node.getIndex()) {   //Si el nodo pasado es el hijo izqdo
                if (parent.getRight() != -1) {   //Compruebo que tenga hijo derecho para devolverlo
                    int hermanoDer = parent.getRight();  //cojo el hijo derecho
                    BTPos<E> sibRight;
                    sibRight = arbolArray[hermanoDer];  //guardo el BTPos del hijo derecho
                    sibPos.setElement(sibRight.getElement());  //Actualizo el elemento de sibPos
                    sibIndex = sibRight.getIndex(); //el nuevo indice de sibPos va a ser el indice hijo derecho
                    sibPos.setIndex(sibIndex);  // actualizo el indice de sibPos
                } else {  //Si no tiene hijo derecho, entonces no tiene hermano
                    throw new BoundaryViolationException("No sibling");
                }

            } else {  //Si nodo pasado es hijo derecho
                if (parent.getLeft() != -1) {  //Si tiene hijo izqdo 
                    int hermanoIzq = parent.getLeft();
                    BTPos<E> sibLeft;
                    sibLeft = arbolArray[hermanoIzq];//Guardo BTPos del hijo izqdo
                    sibPos.setElement(sibLeft.getElement());  //Actualizo el elemento de sibPos
                    sibIndex = sibLeft.getIndex();  //el nuevo indice de sibPos va a ser el indice hijo izqdo
                    sibPos.setIndex(sibIndex);  //actualizo el indice de sibPos
                } else {   //Si no tiene hijo derecho, entonces no tiene hermano
                    throw new BoundaryViolationException("No sibling");
                }
            }
            if (sibPos.getIndex() != -1) {  //Si hemos actualizado el indice de sibPos y no es nulo
                return sibPos;
            }
        }
        throw new BoundaryViolationException("No sibling");
    }

    /**
     * Adds a root node to an empty arbolArray
     */
    public Position<E> addRoot(E e) throws NonEmptyTreeException {
        if (!isEmpty()) {
            throw new NonEmptyTreeException("Tree already has a root");
        }
        this.size = 1;
        arbolArray[0] = new BTPos<>(e, 0);
        libres.remove(0);
        return arbolArray[0];
    }

    /**
     * Inserts a left child at a given node.
     */
    public Position<E> insertLeft(Position<E> p, E e) //Modificado
            throws InvalidPositionException {
        BTPos<E> node = checkPosition(p);  //padre

        if (hasLeft(p)) {   //Si tiene hijo izqdo
            throw new InvalidPositionException("Node already has a left child");
        }
        if (!this.libres.isEmpty()) {   //Si el vector de libres no esta vacio
            int pos = libres.remove(0);  //Saco el primer elemento libre en la lista

            BTPos<E> newNode = new BTPos(e, pos);  //Creo un nuevo nodo
            
            node.setLeft(pos);   //establezco como hijo izquierdo del position a pos
            newNode.setParent(node.getIndex());  //Le pongo puntero al padre con valor indice del position pasado
            arbolArray[pos] = newNode;  //En la posicion pos del ABT meto el nodo creado
            size++;
            return newNode;
        } else {   
            throw new InvalidPositionException("Tree is full");
        }
    }

    /**
     * Inserts a right child at a given node.
     */
    public Position<E> insertRight(Position<E> p, E e) //Modificado
            throws InvalidPositionException {
        BTPos<E> node = checkPosition(p);  //padre

        if (hasRight(p)) {   //Si tiene hijo izqdo
            throw new InvalidPositionException("Node already has a left child");
        }
        if (!this.libres.isEmpty()) {  //Si el vector de libres no esta vacio
            int pos = libres.remove(0);  

            BTPos<E> newNode = new BTPos(e, pos);  //Creo un nuevo nodo
            newNode.setParent(node.index);  //Le pongo puntero al padre con valor indice del position pasado
            node.setRight(pos);  //establezco como hijo derecho del position a pos
            this.arbolArray[pos] = newNode; //En la posicion pos del ABT meto el nodo creado
            size++;
            return newNode;
        } else {    
            throw new InvalidPositionException("Tree is full");
        }
    }

    /**
     * Removes a node with zero or one child.
     */
    public E remove(Position<E> p) throws InvalidPositionException {  //Modificado
        BTPos<E> node = checkPosition(p);
        int leftPos = node.getLeft();
        int rightPos = node.getRight();
        E element = node.getElement();
        if (leftPos != -1 && rightPos != -1) {//si el node tiene 2 hijos, excepcion
            throw new InvalidPositionException(
                    "Cannot remove node with two children");
        }//si tiene 1 o 0 hijos
        BTPos<E> child = null;
        //BTPos<E> parent = tree[node.getParent()];//padre del node
        if (node.getParent() == -1) {//si node es la raiz
            if (leftPos != -1) {//si es el hijo izquierdo del nodo a borrar
                child = this.arbolArray[leftPos];
                child.setParent(-1);//actualizo el atributo parent a -1
                libres.add(0, node.index);//añado a free la posicion del nodo borrado
            } else if (rightPos != -1) {//si es el hijo derecho
                child = arbolArray[rightPos];
                child.setParent(-1);//actualizo el atributo parent a -1
                libres.add(0, node.index);
            } else { // Node NO tiene hijos
                libres.add(0, node.index);//añado a la lista de libres la posicion cero
            }
        } else {//Node NO es la raiz
            BTPos<E> parentNode = arbolArray[node.getParent()];
            if (leftPos != -1) {
                child = arbolArray[leftPos];
            } else if (rightPos != -1) {
                child = arbolArray[rightPos];
            }
            if (parentNode.getLeft() == node.getIndex()) {//Node eres el hijo izquierdo??
                if (child != null) {
                    if (parentNode.getLeft() == node.index) {//si es el hijo izquierdo
                        parentNode.setLeft(child.getIndex());//actualizo atributoleft del padre por la pos del hijo de node
                        child.setParent(parentNode.getIndex());//actualizo atributoParent de child
                    } else if (parentNode.getRight() == node.index) {//si es el hijo derecho
                        parentNode.setRight(child.getIndex());//actualizo atributoRight del padre por la pos del hijo de node
                        child.setParent(parentNode.getIndex());//actualizo atributoParent de child
                    }
                }
                libres.add(node.index);
            } else if (parentNode.getRight() == node.getIndex()) {
                if (child != null) {
                    if (parentNode.getLeft() == node.index) {//si es el hijo izquierdo
                        parentNode.setLeft(child.getIndex());//actualizo atributoleft del padre por la pos del hijo de node
                        child.setParent(parentNode.getIndex());//actualizo atributoParent de child
                    } else if (parentNode.getRight() == node.index) {//si es el hijo derecho
                        parentNode.setRight(child.getIndex());//actualizo atributoRight del padre por la pos del hijo de node
                        child.setParent(parentNode.getIndex());//actualizo atributoParent de child
                    }
                }
                libres.add(node.index);
            }
        }
        size--;
        return element;
    }

    /**
     * Attaches two trees to be subtrees of a leaf node.
     */   //Modificado
    public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2) throws InvalidPositionException {
        BTPos<E> nodo = this.checkPosition(p);
        if (this.isInternal(p)) {
            throw new InvalidPositionException("Invalid Position Exception");
        }
        if (!t1.isEmpty()) {
            E elem = t1.root().getElement();
            Position<E> nuevoHijo = this.insertLeft(p, elem);
            Position<E> position_origen_copia = t1.root(); //Este estara en el arbol que hay que copiarse
            Position<E> position_destino_copia = nuevoHijo; //Este position esta ya en nuestro arbol. El contenido es igual que el anterior pero es un position distinto.
            attachAux((ArrayBinaryTree<E>) t1, position_origen_copia, position_destino_copia);
        }
        if (!t2.isEmpty()) {
            E elem = t2.root().getElement();
            Position<E> nuevoHijo = this.insertRight(p, elem);

            Position<E> position_origen_copia = t2.root(); //Este estara en el arbol que hay que copiarse
            Position<E> position_destino_copia = nuevoHijo; //Este position esta ya en nuestro arbol. El contenido es igual que el anterior pero es un position distinto.
            attachAux((ArrayBinaryTree<E>) t2, position_origen_copia, position_destino_copia);
        }
    }

    private void attachAux(ArrayBinaryTree<E> arbol, Position<E> position_origen_copia, Position<E> position_destino_copia) {
        BTPos<E> origen = checkPosition(position_origen_copia);

        if (origen.getLeft() != -1) {
            Position<E> hijo = arbol.left(origen);
            E elem = hijo.getElement();
            Position<E> nuevoHijo = this.insertLeft(position_destino_copia, elem);

            position_origen_copia = hijo;
            position_destino_copia = nuevoHijo;
            attachAux(arbol, position_origen_copia, position_destino_copia);
        }
        if (origen.getRight() != -1) {
            Position<E> hijo = arbol.left(origen);
            E elem = hijo.getElement();
            Position<E> nuevoHijo = this.insertRight(position_destino_copia, elem);

            position_origen_copia = hijo;
            position_destino_copia = nuevoHijo;
            attachAux(arbol, position_origen_copia, position_destino_copia);
        }
    }

    /**
     * Swap the elements at two nodes
     */
    public void swapElements(Position<E> p1, Position<E> p2)
            throws InvalidPositionException {
        BTPos<E> node1 = checkPosition(p1);
        BTPos<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
    }

    private BTPos<E> checkPosition(Position<E> p)
            throws InvalidPositionException {
        if (p == null || !(p instanceof BTPos)) {
            throw new InvalidPositionException("The position is invalid");
        }
        return (BTPos<E>) p;
    }

    /**
     * Creates a list storing the the nodes in the subtree of a node, ordered
     * according to the preorder traversal of the subtree.
     */
    public void preorderPositions(Position<E> p, List<Position<E>> pos)
            throws InvalidPositionException {
        checkPosition(p);
        pos.add(p);
        if (hasLeft(p)) {
            preorderPositions(left(p), pos); // recurse on left child
        }
        if (hasRight(p)) {
            preorderPositions(right(p), pos); // recurse on right child
        }
    }

    /**
     * Creates a list storing the the nodes in the subtree of a node, ordered
     * according to the inorder traversal of the subtree.
     */
    public void inorderPositions(Position<E> v, List<Position<E>> pos)
            throws InvalidPositionException {
        if (hasLeft(v)) {
            inorderPositions(left(v), pos); // recurse on left child
        }
        pos.add(v);
        if (hasRight(v)) {
            inorderPositions(right(v), pos); // recurse on right child
        }
    }
    
    private int calculateSizeAux(BTPos<E> n){  //nuevo. Creado por mi
        if(n!= null){
            List<Position<E>> list = new ArrayList<>(); //Creo una nueva lista de position
            preorderPositions(n,list);  //Recorro en preorden
            for(Position<E> e : list){  
                BTPos<E> node = checkPosition(e);
                libres.add(0, node.getIndex());  //Los añado al vector de libres
                size--;  //Y resto al tamaño
            }
        }
        return size;
    }

    /**
     * @return The size above a node
     */
    private int calculateSize(BTPos<E> n) {  //Modificado
        int left = n.getLeft();
        int right = n.getRight();

        if (n == null) {
            return 0;
        } ////devuelve el BTPos de left y el BTPos de right
        else {
            return 1 + calculateSize(arbolArray[left]) + calculateSize(arbolArray[right]);
        }
    }

    /**
     * Convert the node passed by parameter in the new root.
     *
     * @param v new root node
     */
    public void subTree(Position<E> v) {    //Modificado
        arbolArray[0] = checkPosition(v);
        size = calculateSize(arbolArray[0]);

    }

    //------------------------------PRACTICA 3---------------------------------
    /**
     * Removes the subtree rooted at pOrig
     */
    public Position<E> removeSubtree(Position<E> pOrig) throws InvalidPositionException {
        BTPos<E> node = checkPosition(pOrig);
        int parent = node.getParent();

        if (parent == -1) {  //Si es raiz
            arbolArray[0].setIndex(-1);  //Arbol vacio
        } else {
            BTPos<E> parentNode = arbolArray[parent];  //BTPos del padre
            if (node == arbolArray[parent]) {  //Si es el hijo izquierdo
                parentNode.setLeft(-1);
                          
            } else {  //Si es el derecho
                parentNode.setRight(-1);
                
            }
        }
        removeSubtreeAux(node);  //Llamada recursiva para eliminar el subarbol
        size = calculateSizeAux(node);
        return node;
    }

    private void removeSubtreeAux(Position<E> pNode) throws InvalidPositionException {
        BTPos<E> pNode2 = checkPosition(pNode);
        libres.add(pNode2.index);  //Añado el indice al conjunto de los libres
        pNode2.setIndex(-1);

        if (hasLeft(pNode2)) {  //Eliminacion recursiva de los hijos izquierdos
            removeSubtreeAux(this.left(pNode2));
            
        }
        if (hasRight(pNode2)) {  //Eliminacion recursiva hijos derechos
            removeSubtreeAux(this.right(pNode2));
            
        }

    }
}
