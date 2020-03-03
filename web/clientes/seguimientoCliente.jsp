<%-- 
    Document   : seguimientoCliente
    Created on : 22-feb-2020, 23:31:02
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-15"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<fmt:message bundle="${mensajes}" key="cabeceraS" var="cabeceraTitulo"></fmt:message>
<t:layout>
    <%-- Inicio Cabecera --%>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="${cabeceraTitulo}"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="gestionCliente.jsp"/>
        
    </jsp:include>
    <%-- <jsp:param name="volver" value="RegistrarReparacion?volver=${vehiculo.id}"/> --%>
    <%-- Fin Cabecera --%>
    <div class="container-fluid col-11">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-11 my-4" >
                <h1 class="my-5 text-center display-2"><fmt:message bundle="${mensajes}" key="historico"/>: <span class="text-danger" >${vehiculo.marca} ${vehiculo.modelo}</span></h1>
                <%-- MOSTRANDO todas las reparaciones, terminadas o no --%>
                <c:forEach var="r" items="${reparaciones}">
                    <%-- Segun si ha terminadao la reparacion mostramos un mensaje u otro--%>
                    <c:choose>
                        <c:when test="${r.fechaTerminacion != null}">
                            <h3>Id <fmt:message bundle="${mensajes}" key="reparacion"/>: ${r.id}
                                <span class="text-primary display-4 text-uppercase" style="float: right"><fmt:message bundle="${mensajes}" key="estadoReparacionTerminacion"/></span>
                            </h3>
                        </c:when>
                        <c:otherwise>
                            <h3>Id <fmt:message bundle="${mensajes}" key="reparacion"/>: ${r.id}
                                <span class="text-success display-4 text-uppercase" style="float: right"><fmt:message bundle="${mensajes}" key="estadoReparacionEnCurso"/></span>
                            </h3>
                        </c:otherwise>
                    </c:choose>

                    <table class="table table-hover border-bottom text-center">
                        <thead class="thead-dark">
                            <tr>
                                <th class="col-1"><fmt:message bundle="${mensajes}" key="actuacion"/></th>
                                <th class="col-3"><fmt:message bundle="${mensajes}" key="descripcion"/></th>
                                <th class="col-1"><fmt:message bundle="${mensajes}" key="inicio"/></th>
                                <th class="col-3"><fmt:message bundle="${mensajes}" key="comentario"/></th>
                                <th class="col-1"><fmt:message bundle="${mensajes}" key="presupuesto"/></th>
                                <th class="col-1"><fmt:message bundle="${mensajes}" key="estado"/></th>
                                <th class="col-2"><fmt:message bundle="${mensajes}" key="imagen"/></th></tr>
                        </thead>
                        <tbody>
                            <%-- Generamos todas las filas sugun numero de actuaciones de la reparacion --%>
                            <c:forEach var="a" items="${r.actuaciones}">
                                <tr>
                                    <td class="col-1 align-middle">${a.id}</td>
                                    <td class="col-3 align-middle">${a.descripcion}</td>
                                    <td class="col-1 align-middle"><fmt:formatDate type="both" dateStyle="SHORT" value="${a.fechaInicio}"/></td>
                                    <td class="col-3 align-middle">${a.comentarios}</td>
                                    <td class="col-1 align-middle"><fmt:formatNumber value="${a.presupuesto}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <c:choose>
                                        <%-- Segun si esta autorizada o no, nos permite autorizarla --%>
                                        <c:when test="${a.autorizada != 'T'}">
                                            <c:url var="autorizar" value="AsignarActuacion">
                                                <c:param name="id" value="${a.id}"/>
                                                <c:param name="autorizar" value="true"/>
                                            </c:url>
                                            <td class="col-1 align-middle"><a href="${autorizar}" class="btn btn-primary btn-block"><fmt:message bundle="${mensajes}" key="noAutorizado"/></a></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="col-1 align-middle"><fmt:message bundle="${mensajes}" key="siAutorizado"/></td>
                                            <c:if test="${a.fechaFinalizacion != null}">
                                                <c:set var="total" value="${total + a.presupuesto}"/>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="col-2 align-middle">
                                        <c:if test="${a.foto != null}">
                                            <a href="../${a.foto}"><img src="../${a.foto}" style="max-width: 170px; max-height: 100px"></a>
                                        </c:if>
                                    </td>
                                </tr>                
                            </c:forEach>
                    </table>
                    <p class="text-right"><fmt:message bundle="${mensajes}" key="totalCoste"/>: <strong><fmt:formatNumber value="${total}" type="currency" minFractionDigits="2" maxFractionDigits="2"/>  </strong></p>
                    <c:set var="total" value="0"/>
                    <%-- Si la reparacion tiene fecha de finalizacion, quiere decir que esta terminada y podemos vr la factura--%>
                    <c:if test="${r.fechaTerminacion != null}">
                        <c:url var="fac" value="Facturacion">
                            <c:param name="repID" value="${r.id}"/>
                        </c:url>
                        <p class="text-right"><a href="${fac}" class="btn btn-primary btn-block"><fmt:message bundle="${mensajes}" key="factura"/></a> </p>
                    </c:if>
                    <br>
                    <div class="row d-flex justify-content-center">
                        <div id="container${r.id}" class="col-5"></div>
                    </div>
                    <script>
                        var a = new Array();
                        var b = new Array();
                        <c:forEach var="g_act" items="${r.actuaciones}">
                            <c:if test="${g_act.finalizada == 'T'}">
                            a.push(<c:out value="${g_act.presupuesto}"/>)
                            b.push(<c:out value="${g_act.id}"/>)
                            </c:if>
                        </c:forEach>

                        Highcharts.chart('container${r.id}', {

                            chart: {
                                renderTo: 'container',
                                type: 'column'
                            },
                            title: {
                                text: '<fmt:message bundle="${mensajes}" key="actuacionG"/>'
                            },
                            tooltip: {
                                shared: true
                            },
                            xAxis: {
                                categories: b
                            },
                            yAxis: [{
                                    title: {
                                        text: ''
                                    }
                                }, {
                                    title: {
                                        text: ''
                                    },
                                    minPadding: 0,
                                    maxPadding: 0,
                                    max: 100,
                                    min: 0,
                                    opposite: true,
                                    labels: {
                                        format: "{value}%"
                                    }
                                }],
                            series: [{
                                    name: '<fmt:message bundle="${mensajes}" key="coste"/>',
                                    type: 'column',
                                    zIndex: 2,
                                    data: a,
                                }]
                        });

                    </script>
                    <hr class="my-5">
                </c:forEach>
            </div>
                
        </div>
    </div>
</t:layout>
