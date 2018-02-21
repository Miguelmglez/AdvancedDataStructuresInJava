package material.tree.bst;

import java.util.ArrayList;
import java.util.Random;

public class Ejercicio3 {
//--------------------------------------------------------------------------------------------------------------------------	
	public static void main(String[] args) { //PROGRAMA PRINCIPAL

	//INSERCIONES ORDENADAS

	insercionesOrdenadas(1000);
	System.out.println();
	insercionesOrdenadas(10000);
	System.out.println();
	//insercionesOrdenadas(100000); //Stack overflow
	//System.out.println();
	
	//INSERCIONES DESORDENADAS;
	
	insercionesAleatorias(1000);
	System.out.println();
	insercionesAleatorias(10000);
	System.out.println();
	insercionesAleatorias(100000);
	System.out.println();
	
	}
	
//--------------------------------------------------------------------------------------------------------------------------	
	
	public static void insercionesOrdenadas(int maximo){
		System.out.println("INSERCIONES ORDENADAS ("+maximo+" elementos)");
	//insertarOrdenBST
		BinarySearchTree<Integer> arbolBS = new LinkedBinarySearchTree<Integer>(); //crear arbol vacio
		double tiempoInicioBST = System.currentTimeMillis();  //Cogemos inicio del tiempo
		for (int i=0; i<maximo; i++){ //insertamos todos los elementos
			arbolBS.insert(i);
		}
		double tiempoFinBST = System.currentTimeMillis();   //Fin del tiempo
		double tiempoBSTOrden=tiempoFinBST-tiempoInicioBST;  //Sacamos el tiempo que ha tardado
		System.out.println("Tiempo en insertar "+maximo+" elementos ordenados en BST: "+ tiempoBSTOrden +" ms");
		
	//insertarOrdenAVL
		AVLTree<Integer> arbolAVL = new AVLTree<Integer>();//crear arbol vacio
		double tiempoInicioAVL = System.currentTimeMillis();
		for (int i=0; i<maximo; i++){ //insertamos todos los elementos
			arbolAVL.insert(i);
		}
		double tiempoFinAVL = System.currentTimeMillis();
		double tiempoAVLOrden=tiempoFinAVL-tiempoInicioAVL;
		System.out.println("Tiempo en insertar "+maximo+" elementos ordenados en AVL: "+ tiempoAVLOrden +" ms");
		
	//insertarOrdenRB
		RBTree<Integer> arbolRB = new RBTree<Integer>();//crear arbol vacio
		double tiempoInicioRB = System.currentTimeMillis();
		for (int i=0; i<maximo; i++){ //insertamos todos los elementos
			arbolRB.insert(i);
		}
		double tiempoFinRB = System.currentTimeMillis();
		double tiempoRBOrden=tiempoFinRB-tiempoInicioRB;
		System.out.println("Tiempo en insertar "+maximo+" elementos ordenados en RB: "+ tiempoRBOrden +" ms");
		
	//busqueda de un elemento
		System.out.println("BUSQUEDA DE UN NUMERO ALEATORIO");
		Random r = new Random();
		int numeroAleatorio=(int)(r.nextDouble()*maximo+1); //generamos un numero aleatorio que usaremos para buscar
		
		double tiempoInicioBusquedaBST = System.currentTimeMillis();
		//arbolBS.find(numeroAleatorio);
		double tiempoFinBusquedaBST = System.currentTimeMillis();
		double tiempoBusquedaBST=tiempoFinBusquedaBST-tiempoInicioBusquedaBST;
		System.out.println("Tiempo en buscar "+numeroAleatorio+" en BST: "+tiempoBusquedaBST*1000+" ns");
		
		double tiempoInicioBusquedaAVL = System.currentTimeMillis();
		arbolAVL.find(numeroAleatorio);
		double tiempoFinBusquedaAVL = System.currentTimeMillis();
		double tiempoBusquedaAVL=tiempoFinBusquedaAVL-tiempoInicioBusquedaAVL;
		System.out.println("Tiempo en buscar "+numeroAleatorio+" en AVL: "+tiempoBusquedaAVL*1000+" ns");
		
		double tiempoInicioBusquedaRB = System.currentTimeMillis();
		arbolRB.find(numeroAleatorio);
		double tiempoFinBusquedaRB = System.currentTimeMillis();
		double tiempoBusquedaRB=tiempoFinBusquedaRB-tiempoInicioBusquedaRB;
		System.out.println("Tiempo en buscar "+numeroAleatorio+" en RB: "+tiempoBusquedaRB*1000+" ns");
	}
//--------------------------------------------------------------------------------------------------------------------------	
	public static void insercionesAleatorias(int maximo){
		System.out.println("INSERCIONES ALEATORIAS ("+maximo+" elementos)");
		Random r = new Random();
	//creamos una lista para guardar los maximos aleatorios para no insertar elementos repetidos	
		ArrayList<Integer> lista = new ArrayList<Integer>(); 
	//insertamos numeros aleatorios no repetidos
		while (lista.size()!=maximo){
			int numeroInsertar = (int)(r.nextDouble()*maximo+1);
			if (!lista.contains(numeroInsertar)){  //Si el numero a insertar no esta, le añadimos
				lista.add(numeroInsertar);
			}
		}
	
		
	//insertarAleatorioBST
		BinarySearchTree<Integer> arbolBS = new LinkedBinarySearchTree<Integer>();
		double tiempoInicioBST = System.currentTimeMillis();
		for (int i:lista){ //para cada elemento de la lista, lo insertamos en el arbol
			arbolBS.insert(i);
		}
		double tiempoFinBST = System.currentTimeMillis();
		double tiempoBSTOrden=tiempoFinBST-tiempoInicioBST;
		System.out.println("Tiempo en insertar "+maximo+" elementos aleatorios en BST: "+ tiempoBSTOrden +" ms");
		
	//insertarAleatorioAVL
		AVLTree<Integer> arbolAVL = new AVLTree<Integer>();
		double tiempoInicioAVL = System.currentTimeMillis();
		for (int j:lista){//para cada elemento de la lista, lo insertamos en el arbol
			arbolAVL.insert(j);
		}
		double tiempoFinAVL = System.currentTimeMillis();
		double tiempoAVLOrden=tiempoFinAVL-tiempoInicioAVL;
		System.out.println("Tiempo en insertar "+maximo+" elementos aleatorios en AVL: "+ tiempoAVLOrden +" ms");
		
	//insertarAleatoriasRB
		RBTree<Integer> arbolRB = new RBTree<Integer>();
		double tiempoInicioRB = System.currentTimeMillis();
		for (int k:lista){//para cada elemento de la lista, lo insertamos en el arbol
			arbolRB.insert(k);
		}
		double tiempoFinRB = System.currentTimeMillis();
		double tiempoRBOrden=tiempoFinRB-tiempoInicioRB;
		System.out.println("Tiempo en insertar "+maximo+" elementos aleatorios en RB: "+ tiempoRBOrden +" ms");

	//busqueda de un elemento
		System.out.println("BUSQUEDA DE UN NUMERO ALEATORIO");
		int numeroAleatorio=(int)(r.nextDouble()*maximo+1); //generamos un numero aleatorio que usaremos para buscar
		
		double tiempoInicioBusquedaBST = System.currentTimeMillis();
		//arbolBS.find(numeroAleatorio);
		double tiempoFinBusquedaBST = System.currentTimeMillis();
		double tiempoBusquedaBST=tiempoFinBusquedaBST-tiempoInicioBusquedaBST;
		System.out.println("Tiempo en buscar "+numeroAleatorio+" en BST: "+tiempoBusquedaBST*1000+" ns");
		
		double tiempoInicioBusquedaAVL = System.currentTimeMillis();
		arbolAVL.find(numeroAleatorio);
		double tiempoFinBusquedaAVL = System.currentTimeMillis();
		double tiempoBusquedaAVL=tiempoFinBusquedaAVL-tiempoInicioBusquedaAVL;
		System.out.println("Tiempo en buscar "+numeroAleatorio+" en AVL: "+tiempoBusquedaAVL*1000+" ns");
		
		double tiempoInicioBusquedaRB = System.currentTimeMillis();
		arbolRB.find(numeroAleatorio);
		double tiempoFinBusquedaRB = System.currentTimeMillis();
		double tiempoBusquedaRB=tiempoFinBusquedaRB-tiempoInicioBusquedaRB;
		System.out.println("Tiempo en buscar "+numeroAleatorio+" en RB: "+tiempoBusquedaRB*1000+" ns");
	}
}        