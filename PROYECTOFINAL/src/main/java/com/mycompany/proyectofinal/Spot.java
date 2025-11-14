
package com.mycompany.proyectofinal;

import java.sql.*;
import javax.swing.JOptionPane;

public class Spot {

    // ============================
    //     CLASE DE COBROS
    // ============================
    public static class Cobro {

        public double cobroPlano() {
            return 15.0;
        }

        public double cobroVariable(double minutos) {
            double horas = Math.ceil(minutos / 60.0);
            return horas * 10.0;
        }
    }

    // ============================
    //       REGISTRAR ENTRADA
    // ============================
    public void registrarEntrada(String placa, String modo, double billete, String metodoPago) {

        String sqlVehiculo = "SELECT Tipovehiculo, Area FROM vehiculo WHERE Placa = ?";
        String sqlArea = "SELECT Id FROM Area WHERE Rol = ? AND Tipovehiculo = ?";
        String sqlSpot = "SELECT Id FROM spot WHERE Estado = 'libre' AND Area = ? LIMIT 1";

        String sqlInsertTicket = """
            INSERT INTO tickets (placa, area_id, spot_id, fecha_ingreso, modo, monto, metodo_pago)
            VALUES (?, ?, ?, datetime('now'), ?, ?, ?)
        """;

        String sqlUpdateSpot = "UPDATE spot SET Estado = 'ocupado' WHERE Id = ?";

        try (Connection conn = conexiondatos.conectar();
             PreparedStatement psVehiculo = conn.prepareStatement(sqlVehiculo);
             PreparedStatement psArea = conn.prepareStatement(sqlArea);
             PreparedStatement psSpot = conn.prepareStatement(sqlSpot);
             PreparedStatement psInsert = conn.prepareStatement(sqlInsertTicket);
             PreparedStatement psUp = conn.prepareStatement(sqlUpdateSpot)) {

            // Vehículo
            psVehiculo.setString(1, placa);
            ResultSet rv = psVehiculo.executeQuery();
            if (!rv.next()) {
                JOptionPane.showMessageDialog(null, "❌ Vehículo no encontrado");
                return;
            }
            String tipo = rv.getString("Tipovehiculo");
            String areaRol = rv.getString("Area");

            // Área
            psArea.setString(1, areaRol);
            psArea.setString(2, tipo);
            ResultSet ra = psArea.executeQuery();
            if (!ra.next()) {
                JOptionPane.showMessageDialog(null, "❌ No existe un área válida para este vehículo");
                return;
            }
            String areaId = ra.getString("Id");

            // Spot
            psSpot.setString(1, areaId);
            ResultSet rs = psSpot.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "⚠️ No hay spots libres");
                return;
            }
            int spotId = rs.getInt("Id");

            // ============================
            //        COBRO ENTRADA
            // ============================
            Cobro cobro = new Cobro();
            double monto = 0;

            if (modo.equalsIgnoreCase("plano")) {
                monto = cobro.cobroPlano();
            }

            double cambio = billete - monto;
            if (cambio < 0) {
                JOptionPane.showMessageDialog(null, "❌ Billete insuficiente");
                return;
            }

            // Guardar ticket
            psUp.setInt(1, spotId);
            psUp.executeUpdate();

            psInsert.setString(1, placa);
            psInsert.setString(2, areaId);
            psInsert.setInt(3, spotId);
            psInsert.setString(4, modo.toLowerCase());
            psInsert.setDouble(5, monto);
            psInsert.setString(6, metodoPago.toLowerCase()); // <<<<<<<< MÉTODO DE PAGO
            psInsert.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "🚗 ENTRADA REGISTRADA\n" +
                "Modo: " + modo.toUpperCase() + "\n" +
                "Método pago: " + metodoPago.toUpperCase() + "\n" +
                "Monto cobrado: Q" + monto + "\n" +
                "Billete: Q" + billete + "\n" +
                "Cambio: Q" + cambio);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ============================
    //        REGISTRAR SALIDA
    // ============================
    public void registrarSalida(String placa, double billete, String metodoPago) {

        String sqlTicket = """
            SELECT ticket_id, spot_id, fecha_ingreso, modo
            FROM tickets 
            WHERE placa = ? AND fecha_salida IS NULL
        """;

        String sqlUpdateTicket = """
            UPDATE tickets
            SET fecha_salida = datetime('now'), monto = ?, metodo_pago = ?
            WHERE ticket_id = ?
        """;

        String sqlUpdateSpot = "UPDATE spot SET Estado = 'libre' WHERE Id = ?";

        try (Connection conn = conexiondatos.conectar();
             PreparedStatement psTicket = conn.prepareStatement(sqlTicket);
             PreparedStatement psUpdateTicket = conn.prepareStatement(sqlUpdateTicket);
             PreparedStatement psSpot = conn.prepareStatement(sqlUpdateSpot)) {

            psTicket.setString(1, placa);
            ResultSet rt = psTicket.executeQuery();

            if (!rt.next()) {
                JOptionPane.showMessageDialog(null, "❌ No existe ticket activo");
                return;
            }

            int ticketId = rt.getInt("ticket_id");
            int spotId = rt.getInt("spot_id");
            String modo = rt.getString("modo");
            String fechaIngreso = rt.getString("fecha_ingreso");

            double monto = 0;
            Cobro cobro = new Cobro();

            // ============================
            //          COBRO SALIDA
            // ============================
            if (modo.equalsIgnoreCase("variable")) {

                // minutos transcurridos
                String sqlMin = """
                    SELECT CAST((JULIANDAY('now') - JULIANDAY(?)) * 1440 AS INTEGER) AS minutos
                """;

                PreparedStatement psMin = conn.prepareStatement(sqlMin);
                psMin.setString(1, fechaIngreso);
                ResultSet rm = psMin.executeQuery();
                rm.next();
                int minutos = rm.getInt("minutos");

                monto = cobro.cobroVariable(minutos);

            } else if (modo.equalsIgnoreCase("plano")) {
                monto = 0; // ya pagó en entrada
            }

            // Cambio
            double cambio = billete - monto;
            if (cambio < 0) {
                JOptionPane.showMessageDialog(null, "❌ El billete no cubre el pago");
                return;
            }

            // Actualizar ticket
            psUpdateTicket.setDouble(1, monto);
            psUpdateTicket.setString(2, metodoPago.toLowerCase()); // <<<<<<<< MÉTODO DE PAGO
            psUpdateTicket.setInt(3, ticketId);
            psUpdateTicket.executeUpdate();

            // liberar spot
            psSpot.setInt(1, spotId);
            psSpot.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "🚗 SALIDA REGISTRADA\n" +
                "Modo: " + modo.toUpperCase() + "\n" +
                "Método pago: " + metodoPago.toUpperCase() + "\n" +
                "Monto: Q" + monto + "\n" +
                "Billete: Q" + billete + "\n" +
                "Cambio: Q" + cambio +
                "\nSpot liberado: " + spotId);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

