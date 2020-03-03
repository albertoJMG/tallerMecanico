<%-- 
    Document   : gestionCliente
    Created on : 18-feb-2020, 20:49:37
    Author     : alberto
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<fmt:message bundle="${mensajes}" key="cabeceraG" var="cabeceraTitulo"></fmt:message>
<t:layout>
    <%-- Inicio Cabecera --%>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="${cabeceraTitulo}"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="CerrarSesion"/>
    </jsp:include>
    <%-- Fin Cabecera --%>
    <div class="container-fluid col-7 d-flex flex-column ">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h2 class="text-center text-uppercase"><fmt:message bundle="${mensajes}" key="vehiculos"/></h2>
                <table class="table table-hover border-bottom text-center">
                    <thead class="thead-dark">
                        <tr>
                            <th class="col-2"><fmt:message bundle="${mensajes}" key="marca"/></th>
                            <th class="col-2"><fmt:message bundle="${mensajes}" key="modelo"/></th>
                            <th class="col-1"><fmt:message bundle="${mensajes}" key="reparaciones"/></th>
                            <th class="col-2"><fmt:message bundle="${mensajes}" key="acciones"/></th>
                            <th class="col-1"><fmt:message bundle="${mensajes}" key="gestion"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- Mostramos todos los vehiculos del cliente--%>
                        <c:forEach var="v" items="${cliente.vehiculos}">
                            <tr>
                                <td>${v.marca}</td>
                                <td>${v.modelo}</td>
                                <td>${v.reparaciones.size()}</td>
                                <c:url var="reparar" value="RegistrarReparacion">
                                    <c:param name="id" value="${v.id}"/>
                                </c:url>
                                <td><a href="${reparar}" class="btn btn-primary"><fmt:message bundle="${mensajes}" key="solicitarReparacion"/> </a></td>
                                <c:url var="seguimiento" value="SeguimientoCliente">
                                    <c:param name="id" value="${v.id}"/>
                                </c:url>
                                <%-- Si se han solicitado reparaciones aparecera el enlace para hacer el seguimiento --%>
                                <td>
                                    <c:if test="${v.reparaciones.size()>0}"><a href="${seguimiento}" class="btn btn-primary "><fmt:message bundle="${mensajes}" key="seguimiento"/> </a></c:if>
                                    </td>
                                </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="registroVehiculo.jsp" class="btn btn-primary btn-block"><fmt:message bundle="${mensajes}" key="botonRegistrar"/> </a>
            </div>

        </div>
        
        <div class="row col-6 align-items-center offset-3 mt-5">
            <a href="editarCliente.jsp" class="btn btn-primary btn-block text-uppercase"><fmt:message bundle="${mensajes}" key="editarCuenta"/> </a>
        </div>
    </div>

</t:layout>
