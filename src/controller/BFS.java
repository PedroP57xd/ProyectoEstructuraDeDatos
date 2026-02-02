package controller;

import model.Nodo;
import java.util.*;

public class BFS {

    public static List<Nodo> ejecutar(Nodo inicio, Nodo fin) {

        Queue<Nodo> cola = new LinkedList<>();
        Map<Nodo, Nodo> predecesor = new HashMap<>();
        Set<Nodo> visitados = new HashSet<>();

        cola.add(inicio);
        visitados.add(inicio);
        predecesor.put(inicio, null);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            if (actual == fin) {
                return reconstruirRuta(predecesor, fin);
            }

            for (Nodo vecino : actual.getVecinos()) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    predecesor.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        return new ArrayList<>();
    }

    private static List<Nodo> reconstruirRuta(
            Map<Nodo, Nodo> predecesor,
            Nodo fin) {

        List<Nodo> ruta = new ArrayList<>();
        Nodo actual = fin;

        while (actual != null) {
            ruta.add(actual);
            actual = predecesor.get(actual);
        }

        Collections.reverse(ruta);
        return ruta;
    }
}
