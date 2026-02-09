package controller;

import model.Nodo;
import java.util.*;

public class DFSPaths {

    private static List<List<Nodo>> resultados;

    public static List<List<Nodo>> buscar(Nodo inicio, Nodo fin) {

        resultados = new ArrayList<>();

        Set<Nodo> visitados = new HashSet<>();
        List<Nodo> camino = new ArrayList<>();

        dfs(inicio, fin, visitados, camino);

        return resultados;
    }

    private static void dfs(Nodo actual,
                            Nodo fin,
                            Set<Nodo> visitados,
                            List<Nodo> camino) {

        visitados.add(actual);
        camino.add(actual);

        if (actual == fin) {

            resultados.add(
                new ArrayList<>(camino)
            );

        } else {

            for (Nodo v : actual.getVecinos()) {

                if (!visitados.contains(v)) {

                    dfs(v, fin, visitados, camino);
                }
            }
        }

        // Backtracking
        visitados.remove(actual);
        camino.remove(camino.size() - 1);
    }
}
