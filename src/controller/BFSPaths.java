package controller;

import model.Nodo;
import java.util.*;

public class BFSPaths {

    public static List<List<Nodo>> buscar(Nodo inicio, Nodo fin) {

        List<List<Nodo>> resultados = new ArrayList<>();

        Queue<List<Nodo>> cola = new LinkedList<>();

        // Primer camino
        List<Nodo> inicial = new ArrayList<>();
        inicial.add(inicio);

        cola.add(inicial);

        while (!cola.isEmpty()) {

            List<Nodo> camino = cola.poll();

            Nodo ultimo = camino.get(camino.size() - 1);

            // Llegó al final
            if (ultimo == fin) {

                resultados.add(camino);
                continue;
            }

            // Expandir vecinos (nivel)
            for (Nodo v : ultimo.getVecinos()) {

                if (!camino.contains(v)) {

                    List<Nodo> nuevo =
                            new ArrayList<>(camino);

                    nuevo.add(v);

                    cola.add(nuevo);
                }
            }
        }

        return resultados;
    }
}
