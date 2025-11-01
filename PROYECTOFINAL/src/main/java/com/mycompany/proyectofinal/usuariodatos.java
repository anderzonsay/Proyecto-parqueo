
package com.mycompany.proyectofinal;

import java.sql.*;
import javax.swing.JOptionPane;



public class usuariodatos {
    
  public void agregarusuario(Usuariomolde messi  ,vehiculo ander) {
    String sql = "INSERT INTO Usuario (Nombres, Carnet) VALUES (?, ?)";
    String coche1= "INSERT INTO vehiculo (Placa, Tipovehiculo, Area, Id) VALUES (?, ?, ?, ?)";

    try (Connection conexion = conexiondatos.conectar();
         PreparedStatement mini = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        mini.setString(1, messi.getNombre());
        mini.setInt(2, messi.getCarnet());

        mini.executeUpdate();

        try (ResultSet resultado = mini.getGeneratedKeys()) {
            if (resultado.next()) {
                int id = resultado.getInt(1);
                messi.setUsuarioid(id);
            }
        }
         // Insertar vehículo con el ID del usuario
                    try (PreparedStatement stmtVeh = conexion.prepareStatement(coche1)) {
                        stmtVeh.setString(1, ander.getPlaca());
                        stmtVeh.setString(2, ander.getModelo());
                        stmtVeh.setString(3, ander.getRol());
                        stmtVeh.setInt(4, messi.getUsuarioid());
                        stmtVeh.executeUpdate();
                    }
            
        

        JOptionPane.showMessageDialog(null, "✅ Usuario agregado correctamente");

    } catch (SQLException x) {
        JOptionPane.showMessageDialog(null, "❌ Error al agregar usuario: " + x.getMessage());
    }
}
}