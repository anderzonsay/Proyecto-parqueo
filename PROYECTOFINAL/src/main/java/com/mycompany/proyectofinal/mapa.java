
package com.mycompany.proyectofinal;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.util.ArrayList;

public class mapa extends JPanel {

    private final ArrayList<Image> imagenes = new ArrayList<>();

    public mapa(String... rutas) {
        for (String ruta : rutas) {
            Image img = new ImageIcon(getClass().getResource(ruta)).getImage();
            imagenes.add(img);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 0;
        for (Image img : imagenes) {
            g.drawImage(img, x, 0, this);
            x += img.getWidth(this); // posiciona la siguiente imagen a la derecha
        }
    }
}


