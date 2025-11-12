
package com.mycompany.proyectofinal;

import java.sql.*;
import javax.swing.JOptionPane;

public class Spot {
    
   
   public void registrarEntrada(String placa) {
    String sqlBuscarVehiculo = "SELECT Tipovehiculo, Area FROM vehiculo WHERE Placa = ?";
    String sqlBuscarArea = "SELECT Id FROM Area WHERE Rol = ? AND Tipovehiculo = ?";
    String sqlBuscarSpot = "SELECT Id FROM spot WHERE Estado = 'libre' AND Area = ? AND Tipodevehiculo = ? LIMIT 1";
    String sqlActualizarSpot = "UPDATE spot SET Estado = 'ocupado' WHERE Id = ?";
    String sqlInsertarTicket = "INSERT INTO tickets (placa, area_id, spot_id, fecha_ingreso, modo, monto) VALUES (?, ?, ?, datetime('now'), 'hora', 0)";

    try (Connection conn = conexiondatos.conectar();
         PreparedStatement psVehiculo = conn.prepareStatement(sqlBuscarVehiculo);
         PreparedStatement psArea = conn.prepareStatement(sqlBuscarArea);
         PreparedStatement psSpot = conn.prepareStatement(sqlBuscarSpot);
         PreparedStatement psUpdateSpot = conn.prepareStatement(sqlActualizarSpot);
         PreparedStatement psInsertTicket = conn.prepareStatement(sqlInsertarTicket)) {

        // 1️⃣ Buscar datos del vehículo
        psVehiculo.setString(1, placa);
        ResultSet rsVehiculo = psVehiculo.executeQuery();

        if (!rsVehiculo.next()) {
            JOptionPane.showMessageDialog(null, "❌ Vehículo no encontrado con la placa: " + placa);
            return;
        }

        String tipoVehiculo = rsVehiculo.getString("Tipovehiculo");
        String area = rsVehiculo.getString("Area");

        // 2️⃣ Verificar que el área exista y coincida con el tipo de vehículo
        psArea.setString(1, area);
        psArea.setString(2, tipoVehiculo);
        ResultSet rsArea = psArea.executeQuery();

        if (!rsArea.next()) {
            JOptionPane.showMessageDialog(null, "⚠️ No existe un área válida para el vehículo de tipo " + tipoVehiculo);
            return;
        }

        String areaId = rsArea.getString("Id");

        // 3️⃣ Buscar un spot libre en esa área
        psSpot.setString(1, areaId);
        psSpot.setString(2, tipoVehiculo);
        ResultSet rsSpot = psSpot.executeQuery();

        if (rsSpot.next()) {
            int spotId = rsSpot.getInt("Id");

            // 4️⃣ Cambiar estado del spot a ocupado
            psUpdateSpot.setInt(1, spotId);
            psUpdateSpot.executeUpdate();

            // 5️⃣ Crear el ticket de entrada
            psInsertTicket.setString(1, placa);
            psInsertTicket.setString(2, areaId);
            psInsertTicket.setInt(3, spotId);
            psInsertTicket.executeUpdate();

            JOptionPane.showMessageDialog(null, "✅ Vehículo con placa " + placa +
                    " estacionado en área " + areaId + ", spot #" + spotId);
        } else {
            JOptionPane.showMessageDialog(null, "⚠️ No hay spots libres disponibles en el área " + areaId);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "❌ Error al registrar entrada: " + e.getMessage());
        e.printStackTrace();
    }
}


    public void registrarSalida(String placa) {
    String sqlBuscarTicket = """
        SELECT t.spot_id, s.Area
        FROM tickets t
        JOIN spot s ON t.spot_id = s.Id
        WHERE t.placa = ? AND t.fecha_salida IS NULL
    """;

    String sqlActualizarSpot = "UPDATE spot SET Estado = 'libre' WHERE Id = ?";
    String sqlActualizarTicket = "UPDATE tickets SET fecha_salida = datetime('now') WHERE placa = ? AND fecha_salida IS NULL";

    try (Connection conn = conexiondatos.conectar();
         PreparedStatement psTicket = conn.prepareStatement(sqlBuscarTicket);
         PreparedStatement psSpot = conn.prepareStatement(sqlActualizarSpot);
         PreparedStatement psUpdateTicket = conn.prepareStatement(sqlActualizarTicket)) {

        // 1️⃣ Buscar el ticket activo y obtener spot y área
        psTicket.setString(1, placa);
        ResultSet rs = psTicket.executeQuery();

        if (rs.next()) {
            int spotId = rs.getInt("spot_id");
            String area = rs.getString("Area");

            // 2️⃣ Liberar el spot
            psSpot.setInt(1, spotId);
            psSpot.executeUpdate();

            // 3️⃣ Marcar salida del ticket
            psUpdateTicket.setString(1, placa);
            psUpdateTicket.executeUpdate();

            JOptionPane.showMessageDialog(null, "🚗 Vehículo con placa " + placa +
                    " ha salido del área " + area + ". Spot #" + spotId + " liberado.");
        } else {
            JOptionPane.showMessageDialog(null, "⚠️ No se encontró un ticket activo para la placa: " + placa);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "❌ Error al registrar salida: " + e.getMessage());
        e.printStackTrace();
    }
}
    }
