package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Nodo {

    public enum Tipo {
        NORMAL,
        INICIO,
        FIN
    }

    private int id;
    private Point posicion;
    private List<Nodo> vecinos;
    private Tipo tipo;

    public Nodo(int id, int x, int y) {
        this.id = id;
        this.posicion = new Point(x, y);
        this.vecinos = new ArrayList<>();
        this.tipo = Tipo.NORMAL;
    }

    public List<Nodo> getVecinos() {
        return vecinos;
    }

    public void agregarVecino(Nodo nodo) {
        if (!vecinos.contains(nodo)) {
            vecinos.add(nodo);
        }
    }

    public Point getPosicion() {
        return posicion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
         
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
