package proyecto.interfaces.entities;

import proyecto.interfaces.enums.TipoSuscripcion;

import java.util.Objects;

public abstract class Suscripcion {
  // Variable estática que pertenece a la clase, no a los objetos
  private static int generadorCodigo = 1;
  private int codigoSuscripcion;
  private int idUsuario;
  private int diasVigencia;
  private TipoSuscripcion tipo;

  public Suscripcion(int idUsuario, int diasVigencia, TipoSuscripcion tipo) {
    // Asignamos el valor actual y luego lo incrementamos para la próxima instancia
    this.codigoSuscripcion = generadorCodigo++;
    this.idUsuario = idUsuario;
    this.diasVigencia = diasVigencia;
    this.tipo = tipo;
  }

  //getters
  public static int getGeneradorCodigo() {
    return generadorCodigo;
  }

  public int getCodigoSuscripcion() {
    return codigoSuscripcion;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public int getDiasVigencia() {
    return diasVigencia;
  }

  public TipoSuscripcion getTipo() {
    return tipo;
  }

  //setters
  public static void setGeneradorCodigo(int generadorCodigo) {
    Suscripcion.generadorCodigo = generadorCodigo;
  }

  public void setCodigoSuscripcion(int codigoSuscripcion) {
    this.codigoSuscripcion = codigoSuscripcion;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public void setDiasVigencia(int diasVigencia) {
    this.diasVigencia = diasVigencia;
  }

  public void setTipo(TipoSuscripcion tipo) {
    this.tipo = tipo;
  }

  @Override
  public String toString() {
    return "Suscripcion{" +
        "codigoSuscripcion=" + codigoSuscripcion +
        ", idUsuario=" + idUsuario +
        ", diasVigencia=" + diasVigencia +
        ", tipo=" + tipo +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Suscripcion that = (Suscripcion) o;
    return codigoSuscripcion == that.codigoSuscripcion && idUsuario == that.idUsuario && diasVigencia == that.diasVigencia && tipo == that.tipo;
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoSuscripcion, idUsuario, diasVigencia, tipo);
  }
}
