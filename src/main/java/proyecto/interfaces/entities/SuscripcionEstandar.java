package proyecto.interfaces.entities;

import proyecto.interfaces.enums.TipoSuscripcion;

public class SuscripcionEstandar extends Suscripcion {

  public SuscripcionEstandar(int idUsuario, int diasVigencia) {
    super(idUsuario, diasVigencia, TipoSuscripcion.ESTANDAR);
  }

}
