
package material.tree.orderedDict;

import java.util.ArrayList;

public class Ejercicio2_OrderedDict {
    public ArrayList <String> autoComplete (OrderedDictionary<String,String> dict, String prefijo) {
        ArrayList <String> resultado = new  ArrayList <String>();
        String palabra2 = "";
	for (int i = 0; i< prefijo.length(); i++) {
		if (i==prefijo.length() -1) {
			palabra2 = palabra2 + "z";  //(prefijo.charAt(i)+1);  //Vamos a guardar una palabra para comparar con el prefijo.
                                    //Para el prefijo "com", se guarda "con" en palabra2 para buscar todas las que tengan prefijo "com" 
                                    //La z es un apaño porque al hacer debug sacaba co110.
                }else {
			palabra2 = palabra2 + prefijo.charAt(i);
		}
	}
        Iterable<Entry<String, String>> lista = dict.findRange (prefijo, palabra2);
	
	for (Entry<String, String> p: lista) {  //Añado todas las palabras sin repetirlas que estan entre prefijo y palabra2
		if (!p.getKey().equals (palabra2)) {
			resultado.add (p.getKey());
		}
	}
        return resultado;
    }
}
