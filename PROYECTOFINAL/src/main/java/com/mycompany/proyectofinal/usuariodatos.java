
package com.mycompany.proyectofinal;

import java.sql.*;
import javax.swing.JOptionPane;



public class usuariodatos {
    
  public void agregarusuario(Usuariomolde messi){
      
      String sql= "INSERT INTO Usuario (Nombres, Carnet) VALUES (?, ?)";
      try(Connection conexion=conexiondatos.conectar();PreparedStatement mini= conexion.prepareStatement(sql)){
       conexion.setAutoCommit(false);
       mini.setString(1,messi.getNombre() );
       mini.setInt(2, messi.getCarnet());
       mini.executeUpdate();
       JOptionPane.showMessageDialog(null, "conexion exitosa", "conectado", JOptionPane.INFORMATION_MESSAGE);
       
      }
      
   catch(SQLException x) {
     JOptionPane.showMessageDialog(null, "conexion fallida" +x.getMessage(), "error", JOptionPane.ERROR_MESSAGE); 
     
     
  }
    
}
  }
