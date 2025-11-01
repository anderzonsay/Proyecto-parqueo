
package com.mycompany.proyectofinal;


public class vehiculo {
    
    private String placa;
  
    private String modelo;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
  
   

    
     

    // 🔹 Constructor con parámetros
    public vehiculo(String placa, String modelo) {
        this.placa = placa;
     
        this.modelo = modelo;
     
   
    } 
}
    
    
    

