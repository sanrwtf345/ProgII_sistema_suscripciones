<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nueva Suscripción</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Registrar Nueva Suscripción</h4>
                </div>
                <div class="card-body">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="<%=request.getContextPath()%>/SuscripcionServlet" method="POST">
                        <input type="hidden" name="accion" value="registrar">

                        <div class="mb-3">
                            <label for="idUsuario" class="form-label">ID de Usuario</label>
                            <input type="number" class="form-control" id="idUsuario" name="idUsuario" required>
                        </div>

                        <div class="mb-3">
                            <label for="diasVigencia" class="form-label">Días de Vigencia</label>
                            <input type="number" class="form-control" id="diasVigencia" name="diasVigencia" required>
                        </div>

                        <div class="mb-3">
                            <label for="tipo" class="form-label">Tipo de Plan</label>
                            <select class="form-select" id="tipo" name="tipo" required>
                                <option value="ESTANDAR">Estándar</option>
                                <option value="PREMIUM">Premium</option>
                                <option value="FAMILIAR">Familiar</option>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="pinAcceso" class="form-label">PIN de Acceso Parental <small class="text-muted">(Solo para plan Familiar - 4 cifras)</small></label>
                            <input type="number" class="form-control" id="pinAcceso" name="pinAcceso" placeholder="Ej: 1234">
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success">Guardar Suscripción</button>

                           <a href="<%=request.getContextPath()%>/SuscripcionServlet?accion=visualizarTodas" class="btn btn-outline-secondary">Cancelar y Volver</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>