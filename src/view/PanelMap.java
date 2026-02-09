package view;

import controller.BFS;
import model.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import controller.BFSPaths;
import controller.DFS;
import controller.DFSPaths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import model.ResultadosBusqueda;

public class PanelMap extends JPanel {

    private int indiceRuta = 0;
    private Timer animador;
    private static final int RADIO_NODO = 8;
    private Image mapa;
    private List<Nodo> nodos;
    private List<Nodo> rutaActual = new ArrayList<>();
    private List<List<Nodo>> rutas = new ArrayList<>();
    private Color colorRuta = Color.BLUE;

    private ModoInteraccion modo = ModoInteraccion.NINGUNO;

    private Nodo nodoSeleccionadoParaUnion = null;
    private Nodo nodoInicio = null;
    private Nodo nodoFin = null;

    private int contadorId = 1;

    public PanelMap() {
        nodos = new ArrayList<>();
        mapa = new ImageIcon(
                getClass().getResource("/resources/Fondo Mapa.png")
        ).getImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                manejarClick(e.getX(), e.getY());
            }
        });
    }

    // Modos
    public void setModo(ModoInteraccion nuevoModo) {
        detenerAnimacion();

        this.modo = nuevoModo;

        nodoSeleccionadoParaUnion = null; // reset

        if (modo == ModoInteraccion.AGREGAR_NODO) {

            setCursor(Cursor.getPredefinedCursor(
                    Cursor.CROSSHAIR_CURSOR));

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
            case UNIR_UNI:
                unirUnidireccional(x, y);
                repaint();
                break;
            case UNIR_BI:
                unirBidireccional(x, y);
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

        detenerAnimacion(); // ⬅️

        nodos.clear();
        rutas.clear();

        nodoInicio = null;
        nodoFin = null;

        rutaActual.clear();

        repaint();
    }

    // Graficos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Fondo Imagen
        g.drawImage(mapa, 0, 0, getWidth(), getHeight(), this);

        // ===== ARISTAS =====
        g2.setStroke(new BasicStroke(4f));

        for (Nodo n : nodos) {

            Point p1 = n.getPosicion();

            for (Nodo v : n.getVecinos()) {

                Point p2 = v.getPosicion();

                boolean esBi
                        = v.getVecinos().contains(n);

                // Ajustar punto final
                Point fin = ajustarAlBorde(p1, p2);

                if (esBi) {

                    g2.setColor(Color.DARK_GRAY);

                    g2.drawLine(
                            p1.x, p1.y,
                            fin.x, fin.y
                    );

                } else {

                    g2.setColor(Color.LIGHT_GRAY);

                    g2.drawLine(
                            p1.x, p1.y,
                            fin.x, fin.y
                    );

                    dibujarFlecha(g2,
                            p1.x, p1.y,
                            fin.x, fin.y
                    );
                }
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
        detenerAnimacion();

        if (nodoInicio == null || nodoFin == null) {
            return;
        }

        rutas = BFSPaths.buscar(nodoInicio, nodoFin);

        animarRutas();
    }

    public void ejecutarDFS() {
        detenerAnimacion();

        if (nodoInicio == null || nodoFin == null) {
            return;
        }

        rutas = DFSPaths.buscar(nodoInicio, nodoFin);

        animarRutas();
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

            List<String> lineas = br.lines()
                    .map(String::trim) // quitar espacios
                    .filter(l -> !l.isEmpty()) // quitar vacías
                    .toList();

            int i = 0;

            // ================= NODOS =================
            if (!lineas.get(i).equals("# NODOS")) {
                throw new RuntimeException("Archivo sin # NODOS");
            }

            i++;

            while (!lineas.get(i).equals("# ARISTAS")) {

                String[] p = lineas.get(i).split(",");

                int x = Integer.parseInt(p[0]);
                int y = Integer.parseInt(p[1]);

                nodos.add(new Nodo(contadorId++, x, y));

                i++;
            }

            i++; // saltar # ARISTAS

            // ================= ARISTAS =================
            while (!lineas.get(i).equals("# INICIO_FIN")) {

                String linea = lineas.get(i);

                boolean bidireccional = linea.contains("<->");
                boolean unidireccional = linea.contains(">");

                String[] p;

                if (bidireccional) {
                    p = linea.split("<->");
                } else {
                    p = linea.split(">");
                }

                int a = Integer.parseInt(p[0]);
                int b = Integer.parseInt(p[1]);

                if (a < nodos.size() && b < nodos.size()) {

                    Nodo na = nodos.get(a);
                    Nodo nb = nodos.get(b);

                    na.agregarVecino(nb);

                    if (bidireccional) {
                        nb.agregarVecino(na);
                    }
                }

                i++;
            }

            i++; // saltar # INICIO_FIN

            // ================= INICIO / FIN =================
            String[] sf = lineas.get(i).split(",");

            int ini = Integer.parseInt(sf[0]);
            int fin = Integer.parseInt(sf[1]);

            if (ini >= 0 && fin >= 0
                    && ini < nodos.size()
                    && fin < nodos.size()) {

                nodoInicio = nodos.get(ini);
                nodoFin = nodos.get(fin);

                nodoInicio.setTipo(Nodo.Tipo.INICIO);
                nodoFin.setTipo(Nodo.Tipo.FIN);
            }

            repaint();
            revalidate();

            System.out.println("Grafo cargado correctamente. Nodos: " + nodos.size());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar archivo:\n" + ex.getMessage());
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

                Nodo a = nodos.get(i);

                for (Nodo b : a.getVecinos()) {

                    int j = nodos.indexOf(b);

                    // ¿Es bidireccional?
                    boolean esBi = b.getVecinos().contains(a);

                    if (esBi) {

                        // Guardar solo una vez
                        if (i < j) {
                            pw.println(i + "<->" + j);
                        }

                    } else {

                        // Unidireccional
                        pw.println(i + ">" + j);
                    }
                }
            }

            pw.println("\n# INICIO_FIN");
            int iInicio = nodoInicio != null ? nodos.indexOf(nodoInicio) : -1;
            int iFin = nodoFin != null ? nodos.indexOf(nodoFin) : -1;

            pw.println(iInicio + "," + iFin);

        } catch (Exception ex) {
        }
    }

    private void unirUnidireccional(int x, int y) {

        Nodo n = obtenerNodoCercano(x, y);
        if (n == null) {
            return;
        }

        if (nodoSeleccionadoParaUnion == null) {

            // Primer click
            nodoSeleccionadoParaUnion = n;

        } else if (nodoSeleccionadoParaUnion != n) {

            // Segundo click → A → B
            nodoSeleccionadoParaUnion.agregarVecino(n);

            nodoSeleccionadoParaUnion = null;
        }
    }

    private void unirBidireccional(int x, int y) {

        Nodo n = obtenerNodoCercano(x, y);
        if (n == null) {
            return;
        }

        if (nodoSeleccionadoParaUnion == null) {

            // Primer click
            nodoSeleccionadoParaUnion = n;

        } else if (nodoSeleccionadoParaUnion != n) {

            // A ↔ B
            nodoSeleccionadoParaUnion.agregarVecino(n);
            n.agregarVecino(nodoSeleccionadoParaUnion);

            nodoSeleccionadoParaUnion = null;
        }
    }

    private void mostrarRutas(String tipo) {

        if (rutas.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "No hay caminos con " + tipo);

            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Caminos ").append(tipo).append(":\n\n");

        int i = 1;

        for (List<Nodo> r : rutas) {

            sb.append("Ruta ").append(i++).append(": ");

            for (Nodo n : r) {

                sb.append(n.getId()).append(" ");
            }

            sb.append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);

        JScrollPane scroll
                = new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(400, 300));

        JOptionPane.showMessageDialog(this,
                scroll,
                "Resultados " + tipo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void buscarMejor() {

        detenerAnimacion(); // ⬅️ IMPORTANTE

        if (rutas.isEmpty()) {
            return;
        }

        List<Nodo> mejor = rutas.get(0);

        for (List<Nodo> r : rutas) {

            if (r.size() < mejor.size()) {

                mejor = r;
            }
        }

        pintarRuta(mejor, Color.MAGENTA);
    }

    private void animarRutas() {

        if (rutas.isEmpty()) {
            return;
        }

        indiceRuta = 0;

        if (animador != null && animador.isRunning()) {
            animador.stop();
        }

        animador = new javax.swing.Timer(1200, e -> {

            if (indiceRuta < rutas.size()) {

                // Cambiar color según índice
                Color c = Color.getHSBColor(
                        (float) indiceRuta / rutas.size(),
                        1f,
                        1f
                );

                pintarRuta(rutas.get(indiceRuta), c);

                indiceRuta++;

            } else {

                animador.stop();
            }
        });

        animador.start();
    }

    private void dibujarFlecha(Graphics2D g,
            int x1, int y1,
            int x2, int y2) {

        double dx = x2 - x1;
        double dy = y2 - y1;

        double angulo = Math.atan2(dy, dx);

        int largo = 10;
        int ancho = 6;

        int x = x2;
        int y = y2;

        int xA = (int) (x - largo * Math.cos(angulo - Math.PI / 6));
        int yA = (int) (y - largo * Math.sin(angulo - Math.PI / 6));

        int xB = (int) (x - largo * Math.cos(angulo + Math.PI / 6));
        int yB = (int) (y - largo * Math.sin(angulo + Math.PI / 6));

        Polygon flecha = new Polygon();

        flecha.addPoint(x, y);
        flecha.addPoint(xA, yA);
        flecha.addPoint(xB, yB);

        g.fillPolygon(flecha);
    }

    private Point ajustarAlBorde(Point desde, Point hasta) {

        double dx = hasta.x - desde.x;
        double dy = hasta.y - desde.y;

        double dist = Math.sqrt(dx * dx + dy * dy);

        double ratio = (dist - RADIO_NODO) / dist;

        int x = (int) (desde.x + dx * ratio);
        int y = (int) (desde.y + dy * ratio);

        return new Point(x, y);
    }

    private void detenerAnimacion() {

        if (animador != null && animador.isRunning()) {

            animador.stop();
        }
    }

    private ResultadosBusqueda medirBFS() {

        long inicio = System.nanoTime();

        List<Nodo> ruta = BFS.ejecutar(nodoInicio, nodoFin);

        long fin = System.nanoTime();

        long tiempoMs = (fin - inicio) / 1_000;

        int longitud = ruta == null ? 0 : ruta.size();

        return new ResultadosBusqueda("BFS", tiempoMs, longitud);
    }

    private ResultadosBusqueda medirDFS() {

        long inicio = System.nanoTime();

        List<Nodo> ruta = DFS.ejecutar(nodoInicio, nodoFin);

        long fin = System.nanoTime();

        long tiempoMs = (fin - inicio) / 1_000;

        int longitud = ruta == null ? 0 : ruta.size();

        return new ResultadosBusqueda("DFS", tiempoMs, longitud);
    }

    private void mostrarTabla(ResultadosBusqueda bfs,
            ResultadosBusqueda dfs) {

        String[] columnas = {
            "Algoritmo",
            "Tiempo (ms)",
            "Longitud Ruta"
        };

        Object[][] datos = {
            {
                bfs.getAlgoritmo(),
                bfs.getTiempo(),
                bfs.getLongitud()
            },
            {
                dfs.getAlgoritmo(),
                dfs.getTiempo(),
                dfs.getLongitud()
            }
        };

        JTable tabla = new JTable(datos, columnas);

        JScrollPane scroll = new JScrollPane(tabla);

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Comparación BFS vs DFS",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void compararAlgoritmos() {

        if (nodoInicio == null || nodoFin == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione inicio y fin primero");
            return;
        }

        ResultadosBusqueda bfs = medirBFS();
        ResultadosBusqueda dfs = medirDFS();

        mostrarTabla(bfs, dfs);
    }

}
