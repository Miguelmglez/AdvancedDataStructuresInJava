package material.tree;

import java.util.*;
import java.io.IOException;

import material.tree.exceptions.BoundaryViolationException;
import material.tree.exceptions.EmptyTreeException;
import material.tree.exceptions.InvalidPositionException;
import material.tree.exceptions.NonEmptyTreeException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {   
    
    public static void show(LinkedTree<String> tree, List<Position<String>> p) {  //Mostrar el arbol
        if (tree.isEmpty()) {
            System.out.println();
        } else {
            List<Position<String>> hijos = new LinkedList<>();
            System.out.println(tree.root().getElement() + "(Nodo 0)"); //Imprimimos raiz y numero de nodo = 0
            preOrden(tree, tree.root(), hijos, p, 1);  //Imprimir resto de hijos en preOrden
        }
    }

    public static void preOrden(LinkedTree<String> tree, Position<String> v,
                                 List<Position<String>> pos, List<Position<String>> p, int tabulador) {
        pos.add(v);  //Anyadimos la raiz y hacemos el recorrido en preorden
        if (!tree.isLeaf(v)) {
            for (Position<String> w : tree.children(v)) {
                for (int i = 0; i < tabulador; i++) {
                    System.out.print("\t");
                }
                System.out.println((String) w.getElement() + "(Nodo: " + p.indexOf(w) + ")");
                
                preOrden(tree, w, pos, p, tabulador + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Position<String>> position = new ArrayList<>();
        //Convertir URL en un arbol
        LinkedTree<String> tree = new LinkedTree<>();
        Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/Tree_(data_structure)").get(); 
        //Con jSoup parseamos el HTML para convertir la web en un arbol
        Elements lista_hs = doc.select("h0,h1,h2,h3,h4,h5,h6,h7");
        Iterator<Element> it = lista_hs.iterator(); //Utilizamos el iterator para recorrer la lista de hs
        int nivel;

        if (it.hasNext()) {  //elemento raiz
            Element e = (Element) it.next(); //Guardamos el elemento
            String tag = e.tagName();  //Tipo de etiqueta
            String hs = e.text(); //Texto del elemento
            Position<String> actual = tree.addRoot(hs); //Anyadir elemento como raiz
            position.add(actual);
            nivel = tag.charAt(1); //obtener numero de hs, coincide con el nivel que ocupa en el arbol
            //ponemos las etiquetas de la web en orden arborescente
            while (it.hasNext()) {
                e = (Element) it.next(); //guardamos el elemento
                tag = e.tagName(); //tipo de etiqueta
                hs = e.text(); //texto del elemento
                int nivelNuevo = tag.charAt(1); //Guardamos el nuevo nivel que tiene ese elemento

                if (nivelNuevo > nivel) { //HIJO
                    Position<String> nuevo = tree.add(hs, actual);
                    position.add(nuevo);
                    nivel = tag.charAt(1);
                    actual = nuevo;
                } else if (nivelNuevo == nivel) {  //HERMANO
                    Position<String> padre = tree.parent(actual);
                    Position<String> nuevo = tree.add(hs, padre);
                    position.add(nuevo);
                    //nivel = tag.charAt(1);
                    actual = nuevo;
                } else {  //Subir nivel
                    Position<String> padre = null;
                    while (nivel >= nivelNuevo) {
                        padre = tree.parent(actual);
                        actual = padre;
                        nivel--;
                    }
                    nivel = nivelNuevo;
                    Position<String> nuevo = tree.add(hs, padre);
                    position.add(nuevo);
                    actual = nuevo;
                }
            }

        }
       // System.out.println("Elementos del arbol: " + tree.size());
        //MENU
        System.out.println("Write the URL to be parsed: ");
        Scanner teclado = new Scanner(System.in);
        String url = teclado.nextLine();
        System.out.println("Html parsed.");
        String cadena;
        int opcion;
        int nodo;
        int nodo1;
        int padre;
        int orden;
        Position<String> posPadre;
        List<Position<String>> listaHijos;
        do {
            System.out.println();
            System.out.println("Options:");
            System.out.println(" 1- Show TOC");
            System.out.println(" 2- Move node");
            System.out.println(" 3- Delete node");
            System.out.println(" 4- Add node");
            System.out.println(" 5- Exit");
            opcion = Integer.parseInt(teclado.nextLine());
            switch (opcion) {
                case 1:
                    System.out.println(" 1- Show TOC");
                    show(tree, position);
                    break;

                case 2:
                    System.out.println(" 2- Move node");
                    System.out.println("Which node and the associated subtree do you want to move (provide the\n" +
                        "Item number)?:");
                    nodo = Integer.parseInt(teclado.nextLine());
                    Position<String> origen = position.get(nodo);
                    System.out.println("Which node will be the new parent node (provide the Item number)?:");
                    padre = Integer.parseInt(teclado.nextLine());
                    Position<String> destino = position.get(padre);
                    
                    tree.moveSubtree(origen, destino);
                    System.out.println("The node has been moved");

                    break;
                case 3:
                    System.out.println(" \" 3- Delete node\"");
                    System.out.println("Node to be deleted (Provide the Item number): ");
                    nodo = Integer.parseInt(teclado.nextLine());
                 
                    Position<String> mover2 = position.get(nodo);
                    position.remove(nodo);
                    tree.remove(mover2);
                    System.out.println("The node and the associated subtree have been deleted");

                    break;
                case 4:
                    System.out.println(" 4- Add node");
                    System.out.println("Write the text for the new node");
                    cadena = teclado.nextLine();
                    System.out.println("Which node will be the parent of the new node (Provide the Item\n" +
                            "number)??");
                    orden = Integer.parseInt(teclado.nextLine());
                    //Position<String> pos = nodePosition(tree, orden);
                    Position<String> pos = position.get(orden);
                    position.add(tree.add(cadena, pos));
                    System.out.println("The node has been created");
                    break;
                case 5:
                    System.out.println(" 5- Exit");
                    System.out.println("Have a nice day!");
                    break;
            }
        } while (opcion < 5);
    }
    
}
