
package com.mycompany.proyectofinal;


public class vehiculo {
    
    private String Rol;

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }
    
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
    public vehiculo(String placa, String modelo, String Rol) {
        this.placa = placa;
     
        this.modelo = modelo;
        
        this.Rol = Rol;
        
     
   
    } 
}
    
    
    

