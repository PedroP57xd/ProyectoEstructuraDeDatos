package controller;

import model.Nodo;
import java.util.*;

public class DFS {

    public static List<Nodo> ejecutar(Nodo inicio, Nodo fin) {

        Set<Nodo> visitados = new HashSet<>();
        List<Nodo> ruta = new ArrayList<>();

        boolean encontrado = dfsRecursivo(
                inicio,
                fin,
                visitados,
                ruta
        );

        if (encontrado) {
            return ruta;
        }

        return new ArrayList<>();
    }

    private static boolean dfsRecursivo(
            Nodo actual,
            Nodo fin,
            Set<Nodo> visitados,
            List<Nodo> ruta) {

        visitados.add(actual);
        ruta.add(actual);

        // Llegamos al final
        if (actual == fin) {
            return true;
        }

        // Explorar vecinos
        for (Nodo vecino : actual.getVecinos()) {

            if (!visitados.contains(vecino)) {

                if (dfsRecursivo(vecino, fin, visitados, ruta)) {
                    return true;
                }
            }
        }

        // Backtracking (retroceder)
        ruta.remove(ruta.size() - 1);

        return false;
    }
}
