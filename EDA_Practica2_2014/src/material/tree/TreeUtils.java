package material.tree;

import java.util.*;

import material.tree.exceptions.BoundaryViolationException;
import material.tree.exceptions.EmptyTreeException;
import material.tree.exceptions.InvalidPositionException;
import material.tree.exceptions.NonEmptyTreeException;

public class TreeUtils<E> {

    /**
     * Returns an iterable collection of the leaf nodes
     */
    public /*static*/ <E> Iterable<Position<E>> front(Tree<E> tree) {  //Funciona
        
        List<Position<E>> list = new ArrayList<Position<E>>();  //Creo una lista de position
        if (!tree.isEmpty()) {  //Si el arbol no esta vacio, lo recorro con un iterator
            Iterator<Position<E>> it = tree.iterator();  
            while (it.hasNext()) {
                Position<E> pos =  it.next();
                if (tree.isLeaf(pos)) {  //Y si ademas es nodo hoja, lo añado a mi lista
                    list.add(pos);
                }
            }
            return (Iterable<Position<E>>) list;
        }
        return (Iterable<Position<E>>) list;
    }

    /**
     * Returns an integer with the depth of the tree
     */
    public /*static*/ <E> int depth(Tree<E> tree) {
        int depth = 0;
        if (tree.isEmpty()) {   //Si el arbol esta vacio, devuelvo 0
            depth = 0;
        } else {    //Si no esta vacio, llamo al metodo auxiliar que me he creado
            depth = profundidad(tree,tree.root());   //pasandole el arbol y la raiz (position)
        }
        return depth;
    }

    public /*static*/ <E> int profundidad(Tree<E> tree, Position<E> pos) {
        
        //El position pasado es la raiz como he puesto arriba
        //System.out.println (pos.getElement());
        if (tree.isLeaf(pos)) {  //Si el arbol esta vacio
            return 1;
        } else {  //Si no esta vacio, me creo un maximo 
            int max = 0;
            //Creo una lista de positions donde voy a guardar todos los hijos de la raiz
            Iterable<? extends Position<E>> lista =  tree.children(pos);
         
            Iterator<? extends Position<E>> it = lista.iterator();  
            while (it.hasNext()) {  
                Position<E> paux = (Position<E>) it.next();
                int aux = profundidad(tree,paux);  //Llamo recursivamente para comprobar si ese elemento
                if (aux > max) {   //de la lista es mayor que el maximo. Si lo es, lo pongo como maximo.
                    max = aux;   //Es decir, recorro todos los nodos hasta llegar al ultimo y lo guardo
                }                       //para devolverlo
            }
            return 1 + max;
        }
    }  

    /**
     * Returns an integer with the degree of the tree
     */
    public /*static*/ <E> int degree(Tree<E> tree) {   //Grado arbol = nº hijos
        int grado = 0;
        
        if (tree.isEmpty()) {   //si el arbol esta vacio, el grado es 0
            grado = 0;
        } else {  //Si no esta vacio
            Iterator<Position<E>> it = tree.iterator();
            while (it.hasNext()) {
                Position<E> pos = (Position<E>) it.next();
                if (!tree.isLeaf(pos)) {  //Si el elemento no es hoja
                    Iterable<? extends Position<E>> hijos = tree.children(pos);  //Guardo una lista de hijos 
                    Iterator<? extends Position<E>> it2 = hijos.iterator();
                    int grado2 = 0;
                    while (it2.hasNext()) {
                        Position<E> pos2 = (Position<E>) it2.next();
                        grado2 = grado2 + 1;  
                        if(grado2 > grado)
                            grado = grado2;
                    }    
                }
            }
        }
        return grado;
    }    
}