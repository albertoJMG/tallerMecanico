<%-- 
    Document   : factura
    Created on : 26-feb-2020, 14:09:58
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-15"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="taller" class="modelo.Taller"/>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<fmt:message bundle="${mensajes}" key="cabeceraF" var="cabeceraTitulo"></fmt:message>
<t:layout>
    <div class="noImprimir">
        <jsp:include page="../cabecera.jsp">
            <jsp:param name="titulo" value="${cabeceraTitulo}"/>
            <jsp:param name="cerrar" value="CerrarSesion"/>
            <jsp:param name="volver" value="gestionCliente.jsp"/>
        </jsp:include>
    </div>

    <h1 class="text-center mt-5 display-2"><fmt:message bundle="${mensajes}" key="facturaOnline"/></h1>
    <div class="container-fluid col-10 border bg-light pt-5 px-5">
        <div class="row col-12">
            <div class="col-6 d-flex align-items-center">
                <div>
                    <p>Taller Mecanico</p>
                    <p>C.I.F. B10101010</p>
                    <p>Avd. JSP</p>
                    <p>C.P.:10001</p>
                    <p>Telf: 600600600</p>
                </div>
            </div>
            <div  class="col-6 text-right">
                <p><c:out value="${reparacionFactura.vehiculo.cliente.nombre} ${reparacionFactura.vehiculo.cliente.apellidos}"/></p>
                <p>Id <fmt:message bundle="${mensajes}" key="reparacion"/>: <c:out value="${reparacionFactura.id}"/> </p>
                <p><fmt:message bundle="${mensajes}" key="vehiculo"/>: <c:out value="${reparacionFactura.vehiculo.marca} ${reparacionFactura.vehiculo.modelo}"/></p>
                <div class="border col-8 pt-2" style="float: right">
                    <p><fmt:message bundle="${mensajes}" key="descripcion"/>: <c:out value="${reparacionFactura.descripcionAveria}"/> </p>
                    <p><fmt:message bundle="${mensajes}" key="fechaInicio"/>: <fmt:formatDate type="date" dateStyle="SHORT" value="${reparacionFactura.fechaSolicitud}"/> </p>
                    <p><fmt:message bundle="${mensajes}" key="fechaFinalizacion"/>: <fmt:formatDate type="date" dateStyle="SHORT" value="${reparacionFactura.fechaTerminacion}"/></p>
                </div>
            </div>
        </div>
        <hr>
        <div>
            <h4 class="text-center m-5 text-uppercase"><fmt:message bundle="${mensajes}" key="actuaciones"/></h4>
            <table class="table table-bordered border-bottom text-center">
                <thead >
                    <tr>
                        <th class="col-2"><fmt:message bundle="${mensajes}" key="actuacion"/></th>
                        <th><fmt:message bundle="${mensajes}" key="descripcion"/></th>
                        <th class="col-2"><fmt:message bundle="${mensajes}" key="coste"/></th>

                    </tr>
                </thead>
                <tbody>

                    <c:forEach var="a" items="${reparacionFactura.actuaciones}">
                        <c:if test="${a.fechaFinalizacion != null}">
                        <tr>
                            <td><c:out value="${a.id}"/></td>
                            <td><c:out value="${a.descripcion}"/></td>
                            <td><fmt:formatNumber value="${a.presupuesto}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                        </tr>
                        </c:if>
                    </c:forEach>
            </table>
        </div>


        <div class="border text-right p-5" >
            <c:forEach var="precio" items="${reparacionFactura.actuaciones}">
                <c:if test="${precio.fechaFinalizacion != null}">
                    <c:set var="total" value="${total + precio.presupuesto}"/>
                </c:if>
            </c:forEach>
            <p><fmt:message bundle="${mensajes}" key="fechaFinalizacion"/>: <fmt:formatNumber value="${total}" type="currency" minFractionDigits="2" maxFractionDigits="2"/></p>
            <p> I.V.A. 21%: <fmt:formatNumber value="${total * 0.21}" type="currency" minFractionDigits="2" maxFractionDigits="2"/></p>
            <p><strong>TOTAL: <fmt:formatNumber value="${total*1.21}" type="currency" minFractionDigits="2" maxFractionDigits="2"/></strong></p>
        </div>
        <div class="row d-flex justify-content-center m-5">
            <button type="button" name="imprimir" onclick="window.print()" class="btn btn-primary col-2 noImprimir"><fmt:message bundle="${mensajes}" key="imprimir"/></button>
        </div>
    </div>
</t:layout>
