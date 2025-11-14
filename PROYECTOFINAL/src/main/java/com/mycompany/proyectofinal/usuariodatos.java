
package com.mycompany.proyectofinal;

import java.sql.*;
import javax.swing.JOptionPane;



public class usuariodatos {
    
  public void agregarUsuario(Usuariomolde usuario, vehiculo vehiculo) {
    String sqlUsuario = "INSERT INTO Usuario ( Nombres, Carnet) VALUES (?, ?)";
    String sqlVehiculo = "INSERT INTO vehiculo (Placa, Tipovehiculo, Area, IdUsuario) VALUES (?, ?, ?, ?)";

    try (Connection conexion = conexiondatos.conectar();
         PreparedStatement psUsuario = conexion.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

        // Insertar usuario
        psUsuario.setString(1, usuario.getNombre());
        psUsuario.setLong(2, usuario.getCarnet());
        psUsuario.executeUpdate();

        // Obtener ID autogenerado
        try (ResultSet rs = psUsuario.getGeneratedKeys()) {
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                usuario.setUsuarioid(idGenerado);

                // Insertar vehículo con el ID del usuario
                try (PreparedStatement psVehiculo = conexion.prepareStatement(sqlVehiculo)) {
                    psVehiculo.setString(1, vehiculo.getPlaca());
                    psVehiculo.setString(2, vehiculo.getModelo()); // 'carro' o 'moto'
                    psVehiculo.setString(3, vehiculo.getRol());          // 'A01', 'A02', 'A03'
                    psVehiculo.setInt(4, idGenerado);

                    psVehiculo.executeUpdate();
                }
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ No se generó ID de usuario. No se pudo registrar el vehículo.");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "✅ Usuario y vehículo agregados correctamente.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  }
