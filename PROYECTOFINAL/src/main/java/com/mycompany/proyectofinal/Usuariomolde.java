
package com.mycompany.proyectofinal;


public class Usuariomolde {
    private int usuarioid;

    public int getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }
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
    public Usuariomolde(String Nombre, int Carnet, vehiculo Usuario){
      this.Nombre= Nombre;
      this.Carnet= Carnet;
      this.Usuario= Usuario;
      

      
      
    }
    
}
