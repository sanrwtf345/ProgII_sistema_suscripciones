<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Redirigimos automáticamente al Servlet pidiendo la acción por defecto (cartelera principal)
    response.sendRedirect("SuscripcionServlet?accion=visualizarTodas");
%>