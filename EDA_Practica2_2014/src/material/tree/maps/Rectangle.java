package material.tree.maps;

public final class Rectangle {

    double latitud_n;
    double longitud_o;
  

    @Override
    public String toString() {
        return latitud_n + "/" + longitud_o;
    }

    public Rectangle(double latitud_n, double longitud_o) {
        this.latitud_n = valueRectangle(latitud_n);
        this.longitud_o = valueRectangle(longitud_o);
    }

    public double getLatitud_n() {
        return latitud_n;
    }

    public void setLatitud_n(double latitud_n) {
        this.latitud_n = latitud_n;
    }

    public double getLongitud_o() {
        return longitud_o;
    }

    public void setLongitud_o(double longitud_o) {
        this.longitud_o = longitud_o;
    }

    public double valueRectangle(double numero) {  //Aproximar valor
        numero = numero * 1000;
        int aux = (int) numero;
        numero = aux / 1000.0;
        return numero;
    }

}
