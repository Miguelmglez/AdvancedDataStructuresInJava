package material.tree.bst;

import java.util.ArrayList;
import java.util.List;
import material.tree.Position;
import material.tree.bst.AVLTree;
import material.tree.bst.LinkedBinarySearchTree;
import material.tree.maps.HashTableMap;
import material.tree.orderedDict.OrderedDictionary;


public class Ejercicio2_BinarySearch {
    public Iterable <String> autoComplete (BinarySearchTree<String> arbol, String prefijo) {
        List <String> resultado = new  ArrayList <String>();
        String palabra2 = "";
	for (int i = 0; i< prefijo.length(); i++) { //Vamos a guardar una palabra para comparar con el prefijo.
                        //Para el prefijo "com", se guarda "con" en palabra2 para buscar todas las que tengan prefijo "com" 
		if (i == prefijo.length()-1) {
                    palabra2 = palabra2 + "z";  //(prefijo.charAt(i)+1); 
                            //La z es un apaño porque al hacer debug sacaba co110.
		}else {
                    palabra2 = palabra2 + prefijo.charAt(i);
		}
	}
        Iterable <Position<String>> lista = arbol.findRange (prefijo, palabra2);  //Guardamos en una lista todas las que estén entre prefijo y palabra2 
	
	for (Position<String> p: lista) {   //Añado todas las palabras sin repetirlas que estan entre prefijo y palabra2
		if (!p.getElement().equals (palabra2)) {
			resultado.add (p.getElement());
		}
	}
        return resultado;
    }
}
