package material.tree.maps;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class buscadorGasolineras {

    static HashTableMapLP<String, ArrayList<GasStation>> mapa;
    static boolean archivo_cargado = false;

    public static void main(String[] args) throws FileNotFoundException {

        //PROGRAMA
        Scanner teclado = new Scanner(System.in);
        String opcion;
        do {
            System.out.println("\nOptions:");
            System.out.println("1.- CARGAR MAPA");
            System.out.println("2.- GASOLINERAS EN MI CUADRANTE");
            System.out.println("3.- GASOLINERAS EN UN RADIO DETERMINADO");
            System.out.println("4.- DISTANCIA A GASOLINERA MAS CERCANA CON NOMBRE DETERMINADO");
            System.out.println("5.- SALIR\n");
            opcion = teclado.nextLine();
            switch (opcion) {
                case "1":
                    System.out.println("Escribe el nombre del archivo para cargar: (Ejemplo: eess_GOA_30102013.csv)");
                    String archivo = teclado.nextLine();
                    try {
                        cargarArchivoCSV(archivo);
                        archivo_cargado = true;
                    } catch (FileNotFoundException error) {
                        System.out.println("Error al cargar archivo: " + error.getMessage());
                    }
                    break;

                case "2": //GASOLINERAS EN MI CUADRANTE
                    if (!archivo_cargado) {
                        System.out.println("¡Aún no ha cargado un archivo!");
                    } else {
                        double latitud;
                        double longitud;
                        try {
                            System.out.println("Indique su latitud actual: (Ejemplo: 42.2792)");
                            latitud = Double.valueOf(teclado.nextLine());
                            System.out.println("Indique su longitud actual: (Ejemplo: 2.9511)");
                            longitud = Double.valueOf(teclado.nextLine());
                            mostrarGasolineras((ArrayList<GasStation>) serviceQuery(latitud, longitud));
                        } catch (NumberFormatException error) {
                            System.out.println("Se produjo un error: " + error.getMessage());
                        }
                    }
                    break;

                case "3": //GASOLINERAS EN UN RADIO DETERMINADO
                    if (!archivo_cargado) {
                        System.out.println("¡Aún no ha cargado un archivo!");
                    } else {
                        double latitud;
                        double longitud;
                        double radio;
                        try {
                            System.out.println("Indique su latitud actual: (Ejemplo: 42.2792)");
                            latitud = Double.valueOf(teclado.nextLine());
                            System.out.println("Indique su longitud actual: (Ejemplo: 2.9511)");
                            longitud = Double.valueOf(teclado.nextLine());
                            System.out.println("Indique radio de búsqueda: (Ejemplo: 0.05)");
                            radio = Double.valueOf(teclado.nextLine());
                            mostrarGasolineras((ArrayList<GasStation>) serviceQuery(latitud, longitud, radio));
                        } catch (NumberFormatException error) {
                            System.out.println("Se produjo un error: " + error.getMessage());
                        }
                    }
                    break;

                case "4": //DISTANCIA A GASOLINERA MAS CERCANA CON NOMBRE DETERMINADO
                    if (!archivo_cargado) {
                        System.out.println("¡Aún no ha cargado un archivo!");
                    } else {
                        double latitud;
                        double longitud;
                        String nombre;
                        try {
                            System.out.println("Indique su latitud actual: (Ejemplo: 42.2792)");
                            latitud = Double.valueOf(teclado.nextLine());
                            System.out.println("Indique su longitud actual: (Ejemplo: 2.9511)");
                            longitud = Double.valueOf(teclado.nextLine());
                            System.out.println("Indique nombre de gasolinera: (Ejemplo: REPSOL)");
                            nombre = teclado.nextLine();
                            double dis = serviceQuery(latitud, longitud, nombre);
                            if (dis > 0) {
                                System.out.println("La distancia a la gasolinera \"" + nombre + "\" es: " + dis
                                        + " [Aproximadamente " + grados_kilometros(dis) + " Km]");
                            }
                        } catch (NumberFormatException error) {
                            System.out.println("Se produjo un error: " + error.getMessage());
                        }
                    }
                    break;
            }
        } while (!opcion.equals("5"));
    }

    static public Iterable<GasStation> serviceQuery(double lat, double lon) {
        Rectangle rectangle = new Rectangle(lat, lon);
        ArrayList devueltas = mapa.get(rectangle.toString());  //saco el valor del mapa
        return devueltas;
    }

    static public Iterable<GasStation> serviceQuery(double lat, double lon, double rad) {
        ArrayList<GasStation> devueltas = new ArrayList();
        Rectangle noroeste = new Rectangle(lat + rad, lon - rad);
        Rectangle sureste = new Rectangle(lat - rad, lon + rad);
        //double radRed = noroeste.valueRectangle(rad);
        System.out.println("SE ESTÁ BUSCANDO... ESPERE");
        //A continuacion vamos mirando cuadrado a cuadrado
        for (double x = noroeste.getLatitud_n(); x > sureste.getLatitud_n(); x = x - 0.0011) {  
            for (double y = noroeste.getLongitud_o(); y < sureste.getLongitud_o(); y = y + 0.0011) {
                ArrayList<GasStation> listaGasolineras = (ArrayList) serviceQuery(x, y);
                if (listaGasolineras != null) {
                    for (GasStation gas : listaGasolineras) {
                        if (cumpleDistancia(lat, lon, gas.getLatitud(), gas.getLongitud(), rad)) {
                            devueltas.add(gas);
                        }
                    }
                }
            }
        }
        return devueltas;
    }

    static private boolean cumpleDistancia(double x1, double y1, double x2, double y2, double rad) {
        double distancia = distancia(x1, y1, x2, y2);
        return distancia <= rad;  //true si está dentro del radio o en la linea
    }

    static private double distancia(double x1, double y1, double x2, double y2) {
        double hipotenusa;
        return hipotenusa = Math.hypot(x2 - x1,y2 - y1);
       
    }

    public static double serviceQuery(double latitud, double longitud, String name) {  
        double distancia = -1;
        double distanciaAux;
        GasStation mejor = null;
        ArrayList<GasStation> list = new ArrayList<>();
        Iterable<Entry<String, ArrayList<GasStation>>> entries = mapa.entries(); //Saco todas las gasolineras
        for (Entry<String, ArrayList<GasStation>> entry : entries) {  
            list = entry.getValue();
            for (GasStation g : list) {
                if (g.getDescripcion().contains(name)) {  //Saco las que coinciden con el nombre
                    distanciaAux = distancia(g.getLatitud(), g.getLongitud(), latitud, longitud);
                    if (distancia == -1) {  //
                        mejor = g;
                        distancia = distanciaAux;
                    } else if (distancia > distanciaAux) { 
                        mejor = g;
                        distancia = distanciaAux;
                    }
                }
            }
        }
        System.out.print(mejor.getDescripcion());
        
        return distancia;
    }

    static private void mostrarGasolineras(ArrayList<GasStation> gasolineras) {
        if (gasolineras != null) {
            System.out.println("\n ----------------------------");
            System.out.println("GASOLINERA\\S ENCONTRADA\\S:");
            for (GasStation gasStation : gasolineras) {
                System.out.println("- " + gasStation.getDescripcion() + " [Latitud: " + gasStation.getLatitud() + " ]" + " [Longitud: " + gasStation.getLongitud() + " ]");
            }
        } else {
            System.out.println("No hay Gasolineras");
        }
    }

    private static void cargarArchivoCSV(String archivo) throws FileNotFoundException {
        mapa = new HashTableMapLP();
        
        archivo = "C:\\Users\\Alex\\Desktop\\EDA_Practica2_2014\\src\\material\\tree\\maps\\eess_GOA_30102013.csv";
        File file = new File(archivo);
        Scanner lector = new Scanner(file);
        lector.nextLine();
        lector.nextLine();
        int ngasolineras = 0;
        while (lector.hasNext()) {
            String linea = lector.nextLine();
            String[] lineaDividida = linea.split(";");
            double longitud = Double.parseDouble(lineaDividida[0]);
            double latitud = Double.parseDouble(lineaDividida[1]);
            String descripcion = "#" + (ngasolineras + 1) + " ";
            for (int i = 2; i < lineaDividida.length; i++) {
                descripcion += lineaDividida[i];
            }
            GasStation nuevaGasolinera = new GasStation(longitud, latitud, descripcion);
            Rectangle rectangle = nuevaGasolinera.rectangle();
            ArrayList<GasStation> lista = mapa.get(rectangle.toString());  //Saco las gasolineras
            if (lista == null) {
                ArrayList<GasStation> listaNueva = new ArrayList();
                listaNueva.add(nuevaGasolinera);
                mapa.put(rectangle.toString(), listaNueva);
            } else {
                lista.add(nuevaGasolinera);
            }

            ngasolineras++;
        }
        System.out.println("Se ha cargado el archivo \"eess_GOA_30102013.csv\" y se han cargado"
                + " un total de " + ngasolineras + " gasolineras\n");
    }

    private static double grados_kilometros(double dis) {
        dis = dis * 111;
        dis = dis * 10;
        int aux = (int) dis;
        dis = aux / 10.0;
        return dis;
    }

}
