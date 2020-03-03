<%-- 
    Document   : solicitarReparacion
    Created on : 18-feb-2020, 22:59:46
    Author     : alberto
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<fmt:message bundle="${mensajes}" key="cabeceraR" var="cabeceraTitulo"></fmt:message>
<t:layout>
    <%-- Inicio Cabecera --%>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="${cabeceraTitulo}"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="gestionCliente.jsp"/>
    </jsp:include>
    <%-- Fin Cabecera --%>
    <div class="container-fluid col-5">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h2 class="text-center"><fmt:message bundle="${mensajes}" key="vehiculo"/>: ${vehiculo.marca} ${vehiculo.modelo}</h2>
                <p><fmt:message bundle="${mensajes}" key="fechaMatriculacion"/>: <fmt:formatDate type="date" dateStyle="SHORT" value="${vehiculo.fechaPrimeraMatriculacion}"/></p>
                <form method="POST" action="RegistrarReparacion">
                    <input type="hidden" name="id" value="${vehiculo.id}">
                    <label for="descripcion"><fmt:message bundle="${mensajes}" key="descripcionProblema"/></label>
                    <textarea name="descripcion" id="descripcion" class="form-control" maxlength="250" required></textarea>
                    <br>
                    <input type="submit" value="<fmt:message bundle="${mensajes}" key="formalizarReparacion"/>" class="btn btn-primary btn-block">
                </form>
            </div>
        </div>
    </div>
</t:layout>
