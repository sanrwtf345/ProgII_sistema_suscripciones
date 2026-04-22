package proyecto.interfaces.exceptions;

public class PinInvalidoException extends RuntimeException {
  public PinInvalidoException() {
    super("Error: El PIN de acceso parental debe tener exactamente 4 cifras.");
  }
}
