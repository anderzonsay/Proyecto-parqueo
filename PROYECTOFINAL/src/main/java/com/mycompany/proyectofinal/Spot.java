
package com.mycompany.proyectofinal;

import java.sql.*;
import javax.swing.JOptionPane;

public class Spot {
    
   
    // Función para registrar una entrada de vehículo
    public void registrarEntrada(String placa) {
        String sqlBuscarVehiculo = "SELECT Tipovehiculo, Area FROM vehiculo WHERE Placa = ?";
        String sqlBuscarSpot = "SELECT Id FROM spot WHERE Estado = 'libre' AND Tipodevehiculo = ? AND Area = ? LIMIT 1";
        String sqlActualizarSpot = "UPDATE spot SET Estado = 'ocupado' WHERE Id = ?";
        
        try (Connection conn = conexiondatos.conectar();
             PreparedStatement psVehiculo = conn.prepareStatement(sqlBuscarVehiculo);
             PreparedStatement psSpot = conn.prepareStatement(sqlBuscarSpot);
             PreparedStatement psUpdate = conn.prepareStatement(sqlActualizarSpot)) {
            
            // Buscar el vehículo
            psVehiculo.setString(1, placa);
            ResultSet rsVehiculo = psVehiculo.executeQuery();

            if (!rsVehiculo.next()) {
                JOptionPane.showMessageDialog(null, "❌ Vehículo no encontrado con la placa: " + placa);
                return;
            }

            String tipoVehiculo = rsVehiculo.getString("Tipovehiculo");
            String area = rsVehiculo.getString("Area");

            // Buscar un spot libre compatible
            psSpot.setString(1, tipoVehiculo);
            psSpot.setString(2, area);
            ResultSet rsSpot = psSpot.executeQuery();

            if (rsSpot.next()) {
                int spotId = rsSpot.getInt("Id");

                // Actualizar el estado del spot
                psUpdate.setInt(1, spotId);
                psUpdate.executeUpdate();

                JOptionPane.showMessageDialog(null, "✅ Vehículo con placa " + placa + 
                        " ha sido estacionado en el spot #" + spotId);
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ No hay spots libres disponibles para ese tipo de vehículo y área.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al registrar entrada: " + e.getMessage());
        }
    }

    // Función para registrar una salida de vehículo
    public void registrarSalida(String placa) {
        String sqlBuscarSpot = "SELECT s.Id FROM spot s " +
                "JOIN vehiculo v ON v.Tipovehiculo = s.Tipodevehiculo AND v.Area = s.Area " +
                "WHERE v.Placa = ? AND s.Estado = 'ocupado'";
        String sqlActualizarSpot = "UPDATE spot SET Estado = 'libre' WHERE Id = ?";
        
        try (Connection conn = conexiondatos.conectar();
             PreparedStatement psBuscar = conn.prepareStatement(sqlBuscarSpot);
             PreparedStatement psUpdate = conn.prepareStatement(sqlActualizarSpot)) {
            
            psBuscar.setString(1, placa);
            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {
                int spotId = rs.getInt("Id");
                psUpdate.setInt(1, spotId);
                psUpdate.executeUpdate();

                JOptionPane.showMessageDialog(null, "🚗 Vehículo con placa " + placa + 
                        " ha salido. Spot #" + spotId + " ahora está libre.");
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ No se encontró un spot ocupado asociado a la placa: " + placa);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al registrar salida: " + e.getMessage());
        }
    }
}
    