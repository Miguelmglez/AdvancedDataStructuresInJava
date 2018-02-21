package material.tree.binarytree;

import java.util.*;
import material.tree.*;
import material.tree.binarytree.*;
import material.tree.exceptions.BoundaryViolationException;

public class BinaryTreeUtils<E> {    //Acabada!
    /**
    * Given a tree the method returns a new tree where all left chlids
    * become right childs and vice versa
    */
    public static <E> BinaryTree<E> mirror(BinaryTree<E> binTree) {
        if (binTree instanceof LinkedBinaryTree) {  //Comprobamos si es linkedbinarytree o arraybinarytree
            LinkedBinaryTree<E> mirror = new LinkedBinaryTree<>();
            if(binTree.isEmpty()){  //Si el arbol que me pasas esta vacio, 
            return mirror;   //Devuelvo mi arbol creado 
            }
             else {
                Position<E> root = binTree.root();   //Saco la raiz del arbol
                mirrorAux(root, mirror, binTree);  //Llamo al metodo auxiliar pasandole la raiz y los dos arboles
                return mirror;  //Devuelvo mi arbol creado
            }

        } else if (binTree instanceof ArrayBinaryTree) { 
            ArrayBinaryTree<E> mirror = new ArrayBinaryTree<>(100);
            if (binTree.isEmpty()) {
                return mirror;
            } else {
                Position<E> root = binTree.root();
                mirrorAux(root, mirror, binTree);
                return mirror;
            }
        }
        return null;
    }

    private static <E> void mirrorAux(Position<E> node, BinaryTree<E> mirror, BinaryTree<E> binTree) {
        if (mirror instanceof LinkedBinaryTree) {
            LinkedBinaryTree<E> btMirror_hi = new LinkedBinaryTree<>(); //Me creo un arbol de nodos por la izquierda
            LinkedBinaryTree<E> btMirror_hd = new LinkedBinaryTree<>(); //Me creo un arbol de nodos por la derecha
            LinkedBinaryTree<E> mirror1 = (LinkedBinaryTree<E>) mirror; //Casteo mirror
            if (binTree.hasLeft(node)) {
                mirrorAux(binTree.left(node), btMirror_hi, binTree); //saco el espejo del lado izquierdo
            }
            if (binTree.hasRight(node)) {
                mirrorAux(binTree.right(node), btMirror_hd, binTree); //saco el espejo del lado derecho
            }
            Position<E> r = mirror1.addRoot(node.getElement()); //Anyado al nodo como raiz del espejo
            mirror1.attach(r, btMirror_hd, btMirror_hi); //Creo mi arbol espejo adjuntando lado derecho e izquierdo a la raiz
        } else if (mirror instanceof ArrayBinaryTree) {
            ArrayBinaryTree<E> btMirror_hi = new ArrayBinaryTree<>(10000); //Me creo un arbol de nodos por la izquierda
            ArrayBinaryTree<E> btMirror_hd = new ArrayBinaryTree<>(10000); //Me creo un arbol de nodos por la derecha
            ArrayBinaryTree<E> mirror1 = (ArrayBinaryTree<E>) mirror; //Casteo mirror
            if (binTree.hasLeft(node)) {
                mirrorAux(binTree.left(node), btMirror_hi, binTree); //saco el espejo del lado izquierdo
            }
            if (binTree.hasRight(node)) {
                mirrorAux(binTree.right(node), btMirror_hd, binTree); //saco el espejo del lado derecho
            }
            Position<E> r = mirror1.addRoot(node.getElement()); //Anyado al nodo como raiz del espejo
            mirror1.attach(r, btMirror_hd, btMirror_hi); //Creo mi arbol espejo adjuntando lado derecho e izquierdo a la raiz
        }
    }
    
    /**
    * Determines whether the element e is the tree or not
    */
    public static <E> boolean contains (BinaryTree<E> binTree ,E e){    
        if(binTree.isEmpty()){   //Si es arbol vacio, falso (no contiene nada)
            return false;
        }
        else{  //Caso contrario, no estar vacio
            if(binTree.root().getElement().equals(e)){  //si el contenido de la raiz es el del elemento pasado
                return true;                        //entonces true (si esta)
            }
            else{  //Si no es el elemento pasado
                boolean encontrado = false;   //Me creo una variable booleana encontrado
                
                Iterator<Position<E>> it = binTree.iterator();  //Recorro el arbol
                while(it.hasNext() && !encontrado){  
                    Position<E> nodo = (Position<E>) it.next();
                    if(nodo.getElement().equals(e)){  //En caso de que encuentre el nodo, pongo encontrado a true
                        encontrado = true;
                    }
                }
                return encontrado;  //Y por tanto devuelvo la variable boolean encontrado
            }
        }
    }
    
    /**
    * Determines the level of a node in the tree
    */
    public static <E> int level(BinaryTree<E> binTree, Position<E> p) throws BoundaryViolationException {
        int nivel = 0;
        if (p.equals(binTree.root())) {
            nivel = 0;
        } else {
            p = binTree.parent(p);
            while (p != null) {
                try {
                    nivel++;
                    p = binTree.parent(p);
                } catch (Exception e) {
                    return nivel;
                }
            }
        }
        return nivel;
    }
      
     
}
