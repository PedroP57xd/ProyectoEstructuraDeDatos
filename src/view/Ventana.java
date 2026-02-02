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

    private void crearMenu() {

        JMenuBar menuBar = new   JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemCargar = new JMenuItem("Cargar grafo");
        JMenuItem itemGuardar = new JMenuItem("Guardar grafo");
        JMenuItem itemSalir = new JMenuItem("Salir");

        itemCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        itemCargar.addActionListener(e -> panelMap.cargarGrafo());

        itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
        itemGuardar.addActionListener(e -> panelMap.guardarGrafo());

        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemCargar);
        menuArchivo.add(itemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        menuBar.add(menuArchivo);

        JMenu menuNodo = new JMenu("Nodo");

        JMenuItem itemAgregar = new JMenuItem("Agregar");
        JMenuItem itemQuitar = new JMenuItem("Quitar");
        JMenuItem itemLimpiar = new JMenuItem("Limpiar");
        JMenuItem itemConectar = new JMenuItem("Conectar");

        itemConectar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
        itemConectar.addActionListener(e
                -> panelMap.setModo(ModoInteraccion.UNIR_NODOS));

        menuNodo.add(itemConectar);

        itemAgregar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        itemAgregar.addActionListener(e
                -> panelMap.setModo(ModoInteraccion.AGREGAR_NODO));

        itemQuitar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        itemQuitar.addActionListener(e
                -> panelMap.setModo(ModoInteraccion.QUITAR_NODO));
        itemLimpiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        itemLimpiar.addActionListener(e
                -> panelMap.limpiarNodos());

        menuNodo.add(itemAgregar);
        menuNodo.add(itemQuitar);
        menuNodo.addSeparator();
        menuNodo.add(itemLimpiar);

        JMenu menuAlgoritmos = new JMenu("Algoritmos");
        JMenuItem itemBFS = new JMenuItem("BFS");
        JMenuItem itemDFS = new JMenuItem("DFS");

        menuAlgoritmos.add(itemBFS);

        menuAlgoritmos.add(itemDFS);

        itemBFS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
        itemBFS.addActionListener(e -> {
            System.out.println("Click en BFS");
            panelMap.ejecutarBFS();
        });

        itemDFS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        itemDFS.addActionListener(e -> panelMap.ejecutarDFS());

        JMenu menuSeleccion = new JMenu("Selección");

        JMenuItem itemInicio = new JMenuItem("Elegir Inicio");
        JMenuItem itemFin = new JMenuItem("Elegir Fin");
        JMenuItem itemComparar = new JMenuItem("Comparar");

        itemInicio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        itemInicio.addActionListener(e
                -> panelMap.setModo(ModoInteraccion.SELECCIONAR_INICIO));

        itemFin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        itemFin.addActionListener(e
                -> panelMap.setModo(ModoInteraccion.SELECCIONAR_FIN));

        menuSeleccion.add(itemInicio);
        menuSeleccion.add(itemFin);
        menuSeleccion.addSeparator();
        menuSeleccion.add(itemComparar);

        menuBar.add(menuNodo);
        menuBar.add(menuAlgoritmos);
        menuBar.add(menuSeleccion);

        setJMenuBar(menuBar);
    }
}
