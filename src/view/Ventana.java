package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Ventana extends JFrame {

    private PanelMap panelMap;

    public Ventana() {

        setTitle("Visualizador de Grafos - BFS / DFS");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(new BorderLayout());

        panelMap = new PanelMap();
        add(panelMap, BorderLayout.CENTER);

        crearMenu();
    }

    // Crea la barra de menú principal
    private void crearMenu() {

        JMenuBar menuBar = new JMenuBar();

        /* ================= ARCHIVO ================= */

        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemCargar = new JMenuItem("Cargar grafo");
        JMenuItem itemGuardar = new JMenuItem("Guardar grafo");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemCargar.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)); // Ctrl + O
        itemGuardar.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)); // Ctrl + S
        itemSalir.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK)); // Ctrl + Q

        itemCargar.addActionListener(e -> panelMap.cargarGrafo());
        itemGuardar.addActionListener(e -> panelMap.guardarGrafo());
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemCargar);
        menuArchivo.add(itemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        menuBar.add(menuArchivo);

        /* ================= NODO ================= */

        JMenu menuNodo = new JMenu("Nodo");

        JMenuItem itemAgregar = new JMenuItem("Agregar");
        JMenuItem itemQuitar = new JMenuItem("Quitar");
        JMenuItem itemLimpiar = new JMenuItem("Limpiar");

        JMenuItem itemUnirUni = new JMenuItem("Unir Unidireccional");
        JMenuItem itemUnirBi = new JMenuItem("Unir Bidireccional");

        itemAgregar.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)); // Ctrl + A
        itemQuitar.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK)); // Ctrl + R
        itemLimpiar.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK)); // Ctrl + L

        itemUnirUni.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK)); // Ctrl + U
        itemUnirBi.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK)); // Ctrl + B

        itemAgregar.addActionListener(e ->
                panelMap.setModo(ModoInteraccion.AGREGAR_NODO));

        itemQuitar.addActionListener(e ->
                panelMap.setModo(ModoInteraccion.QUITAR_NODO));

        itemLimpiar.addActionListener(e ->
                panelMap.limpiarNodos());

        itemUnirUni.addActionListener(e ->
                panelMap.setModo(ModoInteraccion.UNIR_UNI));

        itemUnirBi.addActionListener(e ->
                panelMap.setModo(ModoInteraccion.UNIR_BI));

        menuNodo.add(itemAgregar);
        menuNodo.add(itemQuitar);
        menuNodo.addSeparator();
        menuNodo.add(itemLimpiar);
        menuNodo.addSeparator();
        menuNodo.add(itemUnirUni);
        menuNodo.add(itemUnirBi);

        menuBar.add(menuNodo);

        /* ================= ALGORITMOS ================= */

        JMenu menuAlgoritmos = new JMenu("Algoritmos");

        JMenuItem itemBFS = new JMenuItem("BFS");
        JMenuItem itemDFS = new JMenuItem("DFS");
        JMenuItem itemBuscar = new JMenuItem("Buscar mejor");

        itemBFS.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK)); // Ctrl + 1
        itemDFS.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK)); // Ctrl + 2
        itemBuscar.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK)); // Ctrl + Enter

        itemBFS.addActionListener(e -> panelMap.ejecutarBFS());
        itemDFS.addActionListener(e -> panelMap.ejecutarDFS());
        itemBuscar.addActionListener(e -> panelMap.buscarMejor());

        menuAlgoritmos.add(itemBFS);
        menuAlgoritmos.add(itemDFS);
        menuAlgoritmos.addSeparator();
        menuAlgoritmos.add(itemBuscar);

        menuBar.add(menuAlgoritmos);

        /* ================= SELECCIÓN ================= */

        JMenu menuSeleccion = new JMenu("Selección");

        JMenuItem itemInicio = new JMenuItem("Elegir Inicio");
        JMenuItem itemFin = new JMenuItem("Elegir Fin");
        JMenuItem itemComparar = new JMenuItem("Comparar");

        itemInicio.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK)); // Ctrl + I
        itemFin.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK)); // Ctrl + F
        itemComparar.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK)); // Ctrl + C

        itemInicio.addActionListener(e ->
                panelMap.setModo(ModoInteraccion.SELECCIONAR_INICIO));

        itemFin.addActionListener(e ->
                panelMap.setModo(ModoInteraccion.SELECCIONAR_FIN));

        itemComparar.addActionListener(e ->
                panelMap.compararAlgoritmos());

        menuSeleccion.add(itemInicio);
        menuSeleccion.add(itemFin);
        menuSeleccion.addSeparator();
        menuSeleccion.add(itemComparar);

        menuBar.add(menuSeleccion);

        /* ================= FINAL ================= */

        setJMenuBar(menuBar);
    }
}

