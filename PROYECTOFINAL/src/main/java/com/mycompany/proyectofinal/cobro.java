
package com.mycompany.proyectofinal;


public class cobro {
    
    public double calcularMonto(String modo) {

        if (modo == null) return 0;

        modo = modo.toLowerCase();

        switch (modo) {
            case "variable":
                return 10.0; // Q10
            case "plano":
                return 15.0; // Q15
            default:
                return 0;    // modo inválido
        }
    }
}
    

