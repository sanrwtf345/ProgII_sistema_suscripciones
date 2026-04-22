package proyecto.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface AdminConexion {
  default Connection obtenerConexion() {
    String dbDriver = "com.mysql.cj.jdbc.Driver";
    // Cadena apuntando a la nueva BD de la clínica
    String dbCadenaConexion = "jdbc:mysql://localhost:3306/plataforma_suscripciones";
    String dbUsuario = "root";
    String dbPass = "root"; // Cambia si tu pass es distinta

    Connection conn = null;

    try {
      Class.forName(dbDriver);
      conn = DriverManager.getConnection(dbCadenaConexion, dbUsuario, dbPass);
    } catch (ClassNotFoundException e) {
      System.out.println("No se encontro el driver de la BD");
      throw new RuntimeException(e);
    } catch (SQLException e) {
      System.out.println("No se pudo conectar a la BD");
      throw new RuntimeException(e);
    }
    System.out.println("Conexión exitosa a la BD");
    return conn;
  }
}
