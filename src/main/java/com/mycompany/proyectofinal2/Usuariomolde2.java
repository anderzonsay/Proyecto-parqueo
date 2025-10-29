
package com.mycompany.proyectofinal2;


public class Usuariomolde2 {
    
     private String Nombre;
    private int Carnet;
    private vehiculo Usuario;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getCarnet() {
        return Carnet;
    }

    public void setCarnet(int Carnet) {
        this.Carnet = Carnet;
    }

    public vehiculo getUsuario() {
        return Usuario;
    }

    public void setUsuario(vehiculo Usuario) {
        this.Usuario = Usuario;
    }

   
    
    //Constructor
    public Usuariomolde2(String Nombre, int Carnet, vehiculo Usuario){
      this.Nombre= Nombre;
      this.Carnet= Carnet;
      this.Usuario= Usuario;
      

      
      
    }
    
    
}
