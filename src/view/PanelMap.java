package view;

import model.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import controller.BFS;
import controller.DFS;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class PanelMap extends JPanel {

    private Image mapa;
    private List<Nodo> nodos;
    private List<Nodo> rutaActual = new ArrayList<>();
    private Color colorRuta = Color.BLUE;

    private ModoInteraccion modo = ModoInteraccion.NINGUNO;

    private Nodo nodoSeleccionadoParaUnion = null;
    private Nodo nodoInicio = null;
    private Nodo nodoFin = null;

    private int contadorId = 1;

    public PanelMap() {
        nodos = new ArrayList<>();
        mapa = new ImageIcon("Assets/Fondo Mapa.png").getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                manejarClick(e.getX(), e.getY());
            }
        });
    }

    // Modos
    public void setModo(ModoInteraccion nuevoModo) {
        this.modo = nuevoModo;

        if (modo == ModoInteraccion.AGREGAR_NODO) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    // click
    private void manejarClick(int x, int y) {

        switch (modo) {

            case AGREGAR_NODO:
                nodos.add(new Nodo(contadorId++, x, y));
                repaint();
                break;

            case QUITAR_NODO:
                eliminarNodoCercano(x, y);
                repaint();
                break;

            case SELECCIONAR_INICIO:
                seleccionarInicio(x, y);
                repaint();
                break;

            case SELECCIONAR_FIN:
                seleccionarFin(x, y);
                repaint();
                break;
            case UNIR_NODOS:
                unirNodos(x, y);
                repaint();
                break;

            default:
                break;
        }
    }

    // Seleccion
    private void seleccionarInicio(int x, int y) {
        Nodo n = obtenerNodoCercano(x, y);
        if (n != null) {
            if (nodoInicio != null) {
                nodoInicio.setTipo(Nodo.Tipo.NORMAL);
            }
            nodoInicio = n;
            nodoInicio.setTipo(Nodo.Tipo.INICIO);
        }
    }

    private void seleccionarFin(int x, int y) {
        Nodo n = obtenerNodoCercano(x, y);
        if (n != null) {
            if (nodoFin != null) {
                nodoFin.setTipo(Nodo.Tipo.NORMAL);
            }
            nodoFin = n;
            nodoFin.setTipo(Nodo.Tipo.FIN);
        }
    }

    // utilidades
    private Nodo obtenerNodoCercano(int x, int y) {
        for (Nodo n : nodos) {
            if (n.getPosicion().distance(x, y) <= 12) {
                return n;
            }
        }
        return null;
    }

    private void eliminarNodoCercano(int x, int y) {
        Nodo n = obtenerNodoCercano(x, y);
        if (n == null) {
            return;
        }

        for (Nodo otro : nodos) {
            otro.getVecinos().remove(n);
        }

        if (n == nodoInicio) {
            nodoInicio = null;
        }
        if (n == nodoFin) {
            nodoFin = null;
        }

        nodos.remove(n);
    }

    public void limpiarNodos() {
        nodos.clear();
        nodoInicio = null;
        nodoFin = null;
        repaint();
    }

    // Graficos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Fondo Imagen
        g.drawImage(mapa, 0, 0, getWidth(), getHeight(), this);

        // Conexiones
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(Color.GRAY);

        for (Nodo n : nodos) {
            Point p1 = n.getPosicion();
            for (Nodo vecino : n.getVecinos()) {
                Point p2 = vecino.getPosicion();
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // ruta 
        g2.setStroke(new BasicStroke(6f)); // más gruesa
        g2.setColor(colorRuta);

        for (int i = 0; i < rutaActual.size() - 1; i++) {
            Point p1 = rutaActual.get(i).getPosicion();
            Point p2 = rutaActual.get(i + 1).getPosicion();
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // nodox
        for (Nodo n : nodos) {
            Point p = n.getPosicion();

            switch (n.getTipo()) {
                case INICIO ->
                    g.setColor(Color.GREEN);
                case FIN ->
                    g.setColor(Color.RED);
                default ->
                    g.setColor(Color.BLACK);
            }

            g.fillOval(p.x - 8, p.y - 8, 16, 16);
        }
    }

    private void unirNodos(int x, int y) {
        Nodo n = obtenerNodoCercano(x, y);
        if (n == null) {
            return;
        }

        if (nodoSeleccionadoParaUnion == null) {
            // Primer nodo
            nodoSeleccionadoParaUnion = n;
        } else if (nodoSeleccionadoParaUnion != n) {
            // Segundo nodo → crear conexión bidireccional
            nodoSeleccionadoParaUnion.agregarVecino(n);
            n.agregarVecino(nodoSeleccionadoParaUnion);
            nodoSeleccionadoParaUnion = null;
        }
    }

    private void pintarRuta(List<Nodo> ruta, Color color) {
        this.rutaActual = ruta;
        this.colorRuta = color;
        repaint();
    }

    public void ejecutarBFS() {
        System.out.println("BFS presionado");

        if (nodoInicio == null || nodoFin == null) {
            System.out.println("Inicio o fin NO definidos");
            return;
        }

        List<Nodo> ruta = BFS.ejecutar(nodoInicio, nodoFin);
        pintarRuta(ruta, Color.BLUE);
    }

    public void ejecutarDFS() {
        if (nodoInicio == null || nodoFin == null) {
            return;
        }

        List<Nodo> ruta = DFS.ejecutar(nodoInicio, nodoFin);
        pintarRuta(ruta, Color.ORANGE);
    }

public void cargarGrafo() {
    JFileChooser chooser = new JFileChooser();

    if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
        return;
    }

    File archivo = chooser.getSelectedFile();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

        nodos.clear();
        rutaActual.clear();
        nodoInicio = null;
        nodoFin = null;

        List<String> lineas = br.lines().toList();

        int i = 0;
        int id = 0;

        // ===== LEER NODOS =====
        while (i < lineas.size() && !lineas.get(i).trim().equals("# ARISTAS")) {
            String linea = lineas.get(i).trim();

            if (!linea.isEmpty() && !linea.startsWith("#")) {
                String[] p = linea.split(",");

                nodos.add(new Nodo(
                        id++,
                        Integer.parseInt(p[0].trim()),
                        Integer.parseInt(p[1].trim())
                ));
            }
            i++;
        }

        i++; // saltar # ARISTAS

        // ===== LEER ARISTAS =====
        while (i < lineas.size() && !lineas.get(i).trim().equals("# INICIO_FIN")) {
            String linea = lineas.get(i).trim();

            if (!linea.isEmpty()) {
                String[] e = linea.split("-");
                int a = Integer.parseInt(e[0].trim());
                int b = Integer.parseInt(e[1].trim());

                nodos.get(a).agregarVecino(nodos.get(b));
                nodos.get(b).agregarVecino(nodos.get(a));
            }
            i++;
        }

        i++; // saltar # INICIO_FIN

        // ===== LEER INICIO Y FIN =====
        if (i < lineas.size()) {
            String[] sf = lineas.get(i).trim().split(",");

            nodoInicio = nodos.get(Integer.parseInt(sf[0].trim()));
            nodoFin = nodos.get(Integer.parseInt(sf[1].trim()));

            nodoInicio.setTipo(Nodo.Tipo.INICIO);
            nodoFin.setTipo(Nodo.Tipo.FIN);
        }

        repaint();

        System.out.println("Grafo cargado correctamente. Nodos: " + nodos.size());

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
    

    public void guardarGrafo() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = chooser.getSelectedFile();

        try (PrintWriter pw = new PrintWriter(archivo)) {

            pw.println("# NODOS");
            for (Nodo n : nodos) {
                Point p = n.getPosicion();
                pw.println(p.x + "," + p.y);
            }

            pw.println("\n# ARISTAS");
            for (int i = 0; i < nodos.size(); i++) {
                for (Nodo v : nodos.get(i).getVecinos()) {
                    int j = nodos.indexOf(v);
                    if (i < j) {
                        pw.println(i + "-" + j);
                    }
                }
            }

            pw.println("\n# INICIO_FIN");
            pw.println(
                    nodos.indexOf(nodoInicio) + ","
                    + nodos.indexOf(nodoFin)
            );

        } catch (Exception ex) {
        }
    }

}
