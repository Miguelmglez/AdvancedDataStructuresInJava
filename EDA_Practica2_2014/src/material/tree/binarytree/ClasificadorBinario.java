package material.tree.binarytree;

import java.util.Scanner;
import material.tree.Position;

public class ClasificadorBinario {
    public static void main(String args[]) {
        String string;
        LinkedBinaryTree<String> tree = new LinkedBinaryTree<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Bienvenido");
        for (;;) {//creo un bucle infinito
            System.out.println("¿Con qué tipo de objeto vamos a jugar?");
            String object = input.nextLine();
            System.out.println("Piensa en un " + object + "y pulsa una tecla o escribe salir. ");
            string = input.nextLine();
            if (string.equals("salir")) {//si el usuario escribe "salir"
                break;//el bucle infinito se rompe
            }//en caso de que el usuario quiera jugar
            //Primera jugada
            if (tree.isEmpty()) {//comprobamos si el arbol esta vacio
                System.out.println("¿Qué " + object + "has pensando?");
                string = input.nextLine();
                tree.addRoot(string);//Añadimos string como raiz del arbol
            } else {//si el arbol no esta vacio
                Position<String> pos = tree.root();
                while (tree.isInternal(pos)) {  //Mientras no sea hoja (tenga nodos internos)
                    System.out.println(pos.getElement());
                    string = input.nextLine();
                    if (string.equals("si")) {  //Si responde si, nos vamos por la rama derecha
                        pos = tree.right(pos);
                    } else {
                        pos = tree.left(pos);  //Si no por la rama izquierda
                    }
                }
                System.out.println("En un " + pos.getElement() + "?(si/no): ");
                string = input.nextLine();
                if (string.equals("si")) {
                    System.out.println("¡¡He acertado!!");
                } else {
                    System.out.println("¿Qué " + object + " has pensado?");
                    String newAnimal = input.nextLine();
                    System.out.println("Escribe una pregunta con respuesta afirmativa para un "
                            + newAnimal + " y negativa para un " + pos.getElement());
                    String newQuestion = input.nextLine();
                    String antAnimal = tree.replace(pos, newQuestion);
                    tree.insertRight(pos, newAnimal);  // Por la derecha metemos al nuevo animal
                    tree.insertLeft(pos, antAnimal);  // Por la izquierda dejamos animal anterior
                }
            }
        }
    }
}
