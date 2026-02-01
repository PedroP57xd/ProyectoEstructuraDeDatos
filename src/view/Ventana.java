package view;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {

    private PanelMap panelMapa; 

    public Ventana() {
        setTitle("Visualizador BFS y DFS");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelMapa = new PanelMap();
        add(panelMapa, BorderLayout.CENTER);
        crearPanelControl();
    }
    private void crearPanelControl() {
    JPanel panelSur = new JPanel(new GridLayout(1, 5, 10, 0));

    JButton btnAgregarNodo = new JButton("Añadir nodo");
    JButton btnBFS = new JButton("Ejecutar BFS");
    JButton btnDFS = new JButton("Ejecutar DFS");
    JButton btnLimpiar = new JButton("Limpiar");
    JButton btnComparar = new JButton("Comparar");

    btnAgregarNodo.addActionListener(e -> {
        panelMapa.activarModoAgregarNodo();
    });

    panelSur.add(btnAgregarNodo);
    panelSur.add(btnBFS);
    panelSur.add(btnDFS);
    panelSur.add(btnLimpiar);
    panelSur.add(btnComparar);

    add(panelSur, BorderLayout.SOUTH);
}
}
