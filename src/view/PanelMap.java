package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelMap extends JPanel {

    private Image mapa;
    private List<Point> nodos;

    private boolean modoAgregarNodo = false;

    public PanelMap() {
        nodos = new ArrayList<>();
        mapa = new ImageIcon("Assets/Fondo Mapa.png").getImage();

        // Detectar clicks en el panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (modoAgregarNodo) {
                    agregarNodo(e.getX(), e.getY());
                    modoAgregarNodo = false; 
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
    }

    public void activarModoAgregarNodo() {
        modoAgregarNodo = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    private void agregarNodo(int x, int y) {
        nodos.add(new Point(x, y));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo
        g.drawImage(mapa, 0, 0, getWidth(), getHeight(), this);

        // Nodos
        g.setColor(Color.BLACK);
        for (Point p : nodos) {
            g.fillOval(p.x - 8, p.y - 8, 16, 16);
        }
    }
}

