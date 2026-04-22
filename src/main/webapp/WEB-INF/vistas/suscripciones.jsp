<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Suscripciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Panel de Suscripciones</h2>
        <div>
            <a href="SuscripcionServlet?accion=mostrarFormulario" class="btn btn-primary">NUEVA SUSCRIPCIÓN</a>
            <a href="SuscripcionServlet?accion=agrupar" class="btn btn-outline-dark">Ver Agrupadas</a>
        </div>
    </div>

    <c:if test="${param.exito == '1'}">
        <div class="alert alert-success">Suscripción registrada correctamente.</div>
    </c:if>
    <c:if test="${param.error_busqueda == '1'}">
        <div class="alert alert-danger">Error: Ingrese un código numérico válido para buscar.</div>
    </c:if>

    <div class="card mb-4 shadow-sm">
        <div class="card-body">
            <form action="SuscripcionServlet" method="GET" class="d-flex">
                <input type="hidden" name="accion" value="filtrar">
                <input type="number" class="form-control me-2" name="codigoBuscado" placeholder="Buscar por Código Único" required>
                <button type="submit" class="btn btn-secondary">Buscar</button>
                <a href="SuscripcionServlet?accion=visualizarTodas" class="btn btn-link">Limpiar</a>
            </form>
        </div>
    </div>

    <c:if test="${suscripcionFiltrada != null}">
        <div class="alert alert-info">
            <strong>Resultado de búsqueda:</strong>
            Código: ${suscripcionFiltrada.codigoSuscripcion} | Usuario: ${suscripcionFiltrada.idUsuario} | Tipo: ${suscripcionFiltrada.tipo}
        </div>
    </c:if>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table table-hover table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>Código</th>
                        <th>ID Usuario</th>
                        <th>Tipo de Plan</th>
                        <th>Vigencia (Días)</th>
                        <th>PIN Parental</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty listaSuscripciones}">
                            <tr>
                                <td colspan="5" class="text-center">No hay suscripciones registradas.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="s" items="${listaSuscripciones}">
                                <tr>
                                    <td><strong>#${s.codigoSuscripcion}</strong></td>
                                    <td>${s.idUsuario}</td>
                                    <td>
                                        <span class="badge
                                            ${s.tipo == 'ESTANDAR' ? 'bg-secondary' :
                                              s.tipo == 'PREMIUM' ? 'bg-warning text-dark' : 'bg-success'}">
                                            ${s.tipo}
                                        </span>
                                    </td>
                                    <td>${s.diasVigencia}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${s.tipo == 'FAMILIAR'}">
                                                ${s.pinAccesoParental}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">N/A</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>