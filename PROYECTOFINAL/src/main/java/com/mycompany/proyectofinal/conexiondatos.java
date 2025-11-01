
package com.mycompany.proyectofinal;

import java.sql.*;

 
public class conexiondatos {
    
      // Datos de la conexión (ajusta según tu base de datos)
    private static final String URL = "jdbc:mysql://Basededatos:3306/basededatos.db";
   
    
    private static Connection conexion;

    // Método para conectar
    public static Connection conectar() {
         Connection conexion = null;
        
        try {
            
            
            conexion = DriverManager.getConnection(URL);
            System.out.println("✅ Conexión establecida correctamente.");
        
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos -> " + e.getMessage());
        }
        return conexion;
    }

    // Método para desconectar
    public static void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al cerrar la conexión -> " + e.getMessage());
        }
    }
}
    

