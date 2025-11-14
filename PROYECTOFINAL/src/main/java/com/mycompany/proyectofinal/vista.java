
package com.mycompany.proyectofinal;

import javax.swing.JFrame;

public class vista extends JFrame {

    private final mapa mapaI;

    public vista() {

        setTitle("Mapa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // cargar las imágenes en el panel Mapa
        mapaI = new mapa("/Imagenes/logomapa.jpeg", "/Imagenes/parqueo1.jpeg");

        // agregar el panel al JFrame
        add(mapaI);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}


