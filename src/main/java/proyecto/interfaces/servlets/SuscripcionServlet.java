package proyecto.interfaces.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import proyecto.interfaces.dao.SuscripcionDAO;
import proyecto.interfaces.entities.Suscripcion;
import proyecto.interfaces.entities.SuscripcionEstandar;
import proyecto.interfaces.entities.SuscripcionFamiliar;
import proyecto.interfaces.entities.SuscripcionPremium;
import proyecto.interfaces.enums.TipoSuscripcion;
import proyecto.interfaces.exceptions.PinInvalidoException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/SuscripcionServlet")
public class SuscripcionServlet extends HttpServlet {

  private SuscripcionDAO dao;

  @Override
  public void init() throws ServletException {
    super.init();
    dao = new SuscripcionDAO();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String accion = request.getParameter("accion");
    if (accion == null) accion = "visualizarTodas";

    switch (accion) {
      case "visualizarTodas":
        visualizarTodas(request, response);
        break;
      case "mostrarFormulario":
        request.getRequestDispatcher("/WEB-INF/vistas/formularioSuscripcion.jsp").forward(request, response);
        break;
      case "filtrar":
        filtrarPorCodigo(request, response);
        break;
      case "agrupar":
        agruparPorTipo(request, response);
        break;
      default:
        visualizarTodas(request, response);
        break;
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String accion = request.getParameter("accion");

    if ("registrar".equals(accion)) {
      registrarSuscripcion(request, response);
    } else {
      visualizarTodas(request, response);
    }
  }

  private void visualizarTodas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Suscripcion> listaSuscripciones = dao.getAll();
    request.setAttribute("listaSuscripciones", listaSuscripciones);

    request.getRequestDispatcher("/WEB-INF/vistas/suscripciones.jsp").forward(request, response);
  }

  private void registrarSuscripcion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
      int diasVigencia = Integer.parseInt(request.getParameter("diasVigencia"));
      String tipoParam = request.getParameter("tipo");

      TipoSuscripcion tipo = TipoSuscripcion.valueOf(tipoParam.toUpperCase());
      Suscripcion nuevaSuscripcion = null;

      switch (tipo) {
        case ESTANDAR:
          nuevaSuscripcion = new SuscripcionEstandar(idUsuario, diasVigencia);
          break;
        case PREMIUM:
          nuevaSuscripcion = new SuscripcionPremium(idUsuario, diasVigencia);
          break;
        case FAMILIAR:
          // El parseInt solo se ejecuta si eligen FAMILIAR, evitando errores si está vacío en los otros planes
          int pin = Integer.parseInt(request.getParameter("pinAcceso"));
          nuevaSuscripcion = new SuscripcionFamiliar(idUsuario, diasVigencia, pin);
          break;
      }

      if (nuevaSuscripcion != null) {
        dao.insert(nuevaSuscripcion);
      }

      // CORRECCIÓN: Redirección segura usando el Context Path
      response.sendRedirect(request.getContextPath() + "/SuscripcionServlet?accion=visualizarTodas&exito=1");

    } catch (PinInvalidoException e) {
      request.setAttribute("error", e.getMessage());
      request.getRequestDispatcher("/WEB-INF/vistas/formularioSuscripcion.jsp").forward(request, response);

    } catch (NumberFormatException e) {
      request.setAttribute("error", "Error: Los campos numéricos son obligatorios y deben tener un formato válido.");
      request.getRequestDispatcher("/WEB-INF/vistas/formularioSuscripcion.jsp").forward(request, response);
    }
  }

  private void filtrarPorCodigo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      int codigo = Integer.parseInt(request.getParameter("codigoBuscado"));
      Suscripcion suscripcion = dao.getById(codigo);

      request.setAttribute("suscripcionFiltrada", suscripcion);
      request.getRequestDispatcher("/WEB-INF/vistas/suscripciones.jsp").forward(request, response);

    } catch (NumberFormatException e) {
      // CORRECCIÓN: Redirección segura usando el Context Path
      response.sendRedirect(request.getContextPath() + "/SuscripcionServlet?accion=visualizarTodas&error_busqueda=1");
    }
  }

  private void agruparPorTipo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Map<TipoSuscripcion, List<Suscripcion>> mapa = dao.agruparPorTipo();

    request.setAttribute("mapaSuscripciones", mapa);
    request.getRequestDispatcher("/WEB-INF/vistas/suscripcionesAgrupadas.jsp").forward(request, response);
  }
}