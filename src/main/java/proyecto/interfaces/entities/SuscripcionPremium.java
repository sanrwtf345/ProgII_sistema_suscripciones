package proyecto.interfaces.entities;

import proyecto.interfaces.enums.TipoSuscripcion;

public class SuscripcionPremium extends Suscripcion {

  public SuscripcionPremium(int idUsuario, int diasVigencia) {
    super (idUsuario, diasVigencia, TipoSuscripcion.PREMIUM);
  }

}
