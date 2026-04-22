<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Suscripciones por Plan</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Suscripciones Agrupadas por Plan</h2>
        <a href="SuscripcionServlet?accion=visualizarTodas" class="btn btn-secondary">Volver al Listado General</a>
    </div>

    <c:forEach var="entrada" items="${mapaSuscripciones}">
        <div class="card shadow-sm mb-5">
            <div class="card-header
                ${entrada.key == 'ESTANDAR' ? 'bg-secondary text-white' :
                  entrada.key == 'PREMIUM' ? 'bg-warning text-dark' : 'bg-success text-white'}">
                <h4 class="mb-0">PLAN ${entrada.key}</h4>
            </div>
            <div class="card-body">
                <table class="table table-sm table-hover">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>ID Usuario</th>
                            <th>Días Vigencia</th>
                            <c:if test="${entrada.key == 'FAMILIAR'}">
                                <th>PIN Parental</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${entrada.value}">
                            <tr>
                                <td>#${s.codigoSuscripcion}</td>
                                <td>${s.idUsuario}</td>
                                <td>${s.diasVigencia} días</td>
                                <c:if test="${entrada.key == 'FAMILIAR'}">
                                    <td>${s.pinAccesoParental}</td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="text-end">
                    <small class="text-muted text-uppercase">Total en este plan: ${entrada.value.size()}</small>
                </div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${empty mapaSuscripciones}">
        <div class="alert alert-info text-center">No hay datos suficientes para agrupar.</div>
    </c:if>
</div>

</body>
</html>