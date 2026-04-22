package proyecto.interfaces.dao;

import proyecto.interfaces.AdminConexion;
import proyecto.interfaces.DAO;
import proyecto.interfaces.entities.Suscripcion;
import proyecto.interfaces.entities.SuscripcionEstandar;
import proyecto.interfaces.entities.SuscripcionFamiliar;
import proyecto.interfaces.entities.SuscripcionPremium;
import proyecto.interfaces.enums.TipoSuscripcion;
import proyecto.interfaces.exceptions.PinInvalidoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuscripcionDAO implements DAO<Suscripcion, Integer>, AdminConexion {
  @Override
  public void insert(Suscripcion objeto) {
    String sql = "INSERT INTO suscripciones (id_usuario, dias_vigencia, tipo_suscripcion, pin_acceso_parental) VALUES (?, ?, ?, ?)";

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      pstmt.setInt(1, objeto.getIdUsuario());
      pstmt.setInt(2, objeto.getDiasVigencia());
      pstmt.setString(3, objeto.getTipo().name());

      // Validamos si es Familiar para insertar el PIN, si no, enviamos NULL
      if (objeto instanceof SuscripcionFamiliar) {
        pstmt.setInt(4, ((SuscripcionFamiliar) objeto).getPinAccesoParental());
      } else {
        pstmt.setNull(4, Types.INTEGER);
      }

      pstmt.executeUpdate();

      // Recuperamos el ID autogenerado por MySQL y se lo asignamos al objeto Java
      try (ResultSet rsKeys = pstmt.getGeneratedKeys()) {
        if (rsKeys.next()) {
          objeto.setCodigoSuscripcion(rsKeys.getInt(1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Suscripcion> getAll() {
    List<Suscripcion> lista = new ArrayList<>();
    String sql = "SELECT * FROM suscripciones";

    try (Connection conn = obtenerConexion();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Suscripcion sub = mapearSuscripcion(rs);
        if (sub != null) {
          lista.add(sub);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return lista;
  }

  @Override
  public void update(Suscripcion objeto) {
    String sql = "UPDATE suscripciones SET id_usuario = ?, dias_vigencia = ?, tipo_suscripcion = ?, pin_acceso_parental = ? WHERE codigo_suscripcion = ?";

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, objeto.getIdUsuario());
      pstmt.setInt(2, objeto.getDiasVigencia());
      pstmt.setString(3, objeto.getTipo().name());

      if (objeto instanceof SuscripcionFamiliar) {
        pstmt.setInt(4, ((SuscripcionFamiliar) objeto).getPinAccesoParental());
      } else {
        pstmt.setNull(4, Types.INTEGER);
      }

      pstmt.setInt(5, objeto.getCodigoSuscripcion());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(Integer id) {
    String sql = "DELETE FROM suscripciones WHERE codigo_suscripcion = ?";

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Suscripcion getById(Integer id) {
    Suscripcion sub = null;
    String sql = "SELECT * FROM suscripciones WHERE codigo_suscripcion = ?";

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          sub = mapearSuscripcion(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return sub;
  }

  @Override
  public boolean existsById(Integer id) {
    boolean existe = false;
    String sql = "SELECT 1 FROM suscripciones WHERE codigo_suscripcion = ?";

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      try (ResultSet rs = pstmt.executeQuery()) {
        existe = rs.next(); // Retorna true si hay al menos un registro
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return existe;
  }

  // --- MÉTODOS PRIVADOS DE APOYO ---

  private Suscripcion mapearSuscripcion(ResultSet rs) throws SQLException {
    int codigo = rs.getInt("codigo_suscripcion");
    int idUsuario = rs.getInt("id_usuario");
    int dias = rs.getInt("dias_vigencia");
    TipoSuscripcion tipo = TipoSuscripcion.valueOf(rs.getString("tipo_suscripcion"));

    Suscripcion sub = null;

    try {
      switch (tipo) {
        case ESTANDAR:
          sub = new SuscripcionEstandar(idUsuario, dias);
          break;
        case PREMIUM:
          sub = new SuscripcionPremium(idUsuario, dias);
          break;
        case FAMILIAR:
          int pin = rs.getInt("pin_acceso_parental");
          sub = new SuscripcionFamiliar(idUsuario, dias, pin);
          break;
      }
      // Asignamos el ID real de la base de datos al objeto
      if (sub != null) {
        sub.setCodigoSuscripcion(codigo);
      }
    } catch (PinInvalidoException e) {
      // Si la BD tiene un PIN corrupto
      System.err.println("Error de integridad de datos en la BD para el código: " + codigo);
      e.printStackTrace();
    }

    return sub;
  }

  public Map<TipoSuscripcion, List<Suscripcion>> agruparPorTipo() {
    Map<TipoSuscripcion, List<Suscripcion>> mapaAgrupado = new HashMap<>();
    // Obtenemos todas desde la BD
    List<Suscripcion> todas = this.getAll();

    for (Suscripcion s : todas) {
      TipoSuscripcion tipoActual = s.getTipo();
      if (!mapaAgrupado.containsKey(tipoActual)) {
        mapaAgrupado.put(tipoActual, new ArrayList<>());
      }
      mapaAgrupado.get(tipoActual).add(s);
    }
    return mapaAgrupado;
  }

}
