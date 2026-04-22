package proyecto.interfaces.entities;

import proyecto.interfaces.enums.TipoSuscripcion;
import proyecto.interfaces.exceptions.PinInvalidoException;

public class SuscripcionFamiliar extends Suscripcion {

  private int pinAccesoParental;

  public SuscripcionFamiliar(int idUsuario, int diasVigencia, int pin)throws PinInvalidoException {
    super(idUsuario, diasVigencia, TipoSuscripcion.FAMILIAR);
    setPinAccesoParental(pin); // Validamos al momento de construir
  }

  public int getPinAccesoParental() {
    return pinAccesoParental;
  }

  public void setPinAccesoParental(int pin) throws PinInvalidoException {
    // Validamos que tenga exactamente 4 cifras (entre 1000 y 9999)
    if (pin < 1000 || pin > 9999) {
      throw new PinInvalidoException();
    }
    this.pinAccesoParental = pin;
  }

}
