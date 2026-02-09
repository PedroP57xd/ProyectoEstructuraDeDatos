package model;

public class ResultadosBusqueda {

    private String algoritmo;
    private long tiempo;
    private int longitud;

    public ResultadosBusqueda(String algoritmo, long tiempo, int longitud) {
        this.algoritmo = algoritmo;
        this.tiempo = tiempo;
        this.longitud = longitud;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public long getTiempo() {
        return tiempo;
    }

    public int getLongitud() {
        return longitud;
    }
}
