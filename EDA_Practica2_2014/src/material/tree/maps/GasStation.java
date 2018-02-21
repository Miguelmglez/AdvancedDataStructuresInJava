package material.tree.maps;

public class GasStation {
    
    double longitud;
    double latitud;
    String descripcion;

    public GasStation(double longitud, double latitud, String descripcion) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.descripcion = descripcion;
    }

    public GasStation() {
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Rectangle rectangle(){
        Rectangle rectangle = new Rectangle(this.latitud,this.longitud);  
        return rectangle;
    }
    
}
