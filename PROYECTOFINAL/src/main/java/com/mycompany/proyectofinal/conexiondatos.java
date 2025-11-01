package com.mycompany.proyectofinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conexiondatos {

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Asegúrate de que este archivo exista en la carpeta del proyecto
            String url = "jdbc:sqlite:Basededatos/basededatos.db";
            conexion = DriverManager.getConnection(url);
            System.out.println("✅ Conexión exitosa a SQLite");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al conectar con la base de datos -> " + e.getMessage());
        }
        return conexion;
    }
}
