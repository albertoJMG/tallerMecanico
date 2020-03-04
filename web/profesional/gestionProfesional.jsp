<%-- 
    Document   : gestionProfesional
    Created on : 19-feb-2020, 19:06:36
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="taller" class="modelo.Taller"/>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <%-- Inicio Cabecera --%>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="Gestion Profesional"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="CerrarSesion"/>
    </jsp:include>
    <%-- Fin Cabecera --%>
    <%-- Cuando el PROFESIONAL es el ADMINISTRADOR ++++++++++++++++++++++++++++++++++++++++++++ --%>
    <c:if test="${profesional.tipo == 'administrador'}">

        <%-- ZONA GESTION PROFESIONALES. MOSTRAMOS TODOS LOS MECANICOS DE TALLER --%>
        <div class="container-fluid col-11">
            <div class="row d-flex justify-content-around mt-3 bg-white ">
                <div class="col-12 my-4" >
                    <h2 class="text-center text-uppercase">Listado de mecanicos</h2>
                    <table class="table table-hover text-center table-borderless">
                        <thead class="thead-dark">
                            <tr>
                                <th class="col-1">ID</th>
                                <th class="col-2">Nombre</th>
                                <th class="col-2">Apellidos</th>
                                <th class="col-2">Especialidad</th>
                                <th class="col-1">Activo</th>
                                <th class="col-1">Nombre Usuario</th>
                                <th colspan="2" class="col-1">Gestion</th>
                            </tr>
                        </thead>
                        <c:forEach var="p" items="${taller.profesionales}">
                            <tr>
                                <td><c:out value="${p.id}"/></td>
                                <td><c:out value="${p.nombre}"/></td>
                                <td><c:out value="${p.apellidos}"/></td>
                                <td><c:out value="${p.especialidad}"/></td>
                                <c:choose>
                                    <c:when test="${p.activo == 'T'}">
                                        <td>SI</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>NO</td>
                                    </c:otherwise>
                                </c:choose>
                                <td><c:out value="${p.nombreUsuario}"/></td>
                                <c:url var="editar" value="CrearProfesional">
                                    <c:param name="id" value="${p.id}"/>
                                    <c:param name="editar" value="true"/>
                                </c:url>
                                <td><a href="${editar}" class="btn btn-primary btn-block">Editar</a></td>
                                <c:url var="baja" value="CrearProfesional">
                                    <c:param name="id" value="${p.id}"/>
                                    <c:param name="baja" value="true"/>
                                </c:url>
                                <c:if test="${p.activo == 'T'}">
                                    <td ><a href="${baja}" class="btn btn-warning btn-block">Baja</a></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                    <a href="editarProfesional.jsp" class="btn btn-primary btn-block">Nuevo Profesional</a>
                </div>
            </div>
        </div>       
        <hr>
        <%-- ZONA DE GESTION DE REPARACIONES. BUSQUEDA SEGUN CLIENTE O FECHA  --%>
        <div class="container-fluid">
            <h2 class="text-center display-4 m-5 bg-white">Búsqueda de reparaciones terminadas</h2>
            <div class="row d-flex justify-content-around">
                <div class="col-5 text-center">
                    <div class="row d-flex justify-content-around" id="resultados">
                        <div class="col-5 border py-3 bg-white ">
                            <h4>Introduce fecha de finalizacion</h4>
                            <form method="POST" action="GestionProfesional">
                                <label for="fecha">Seleccione un fecha</label>
                                <input type="date" name="rep" id="fecha"  required>
                                <input type="submit" value="Consultar" class="btn btn-primary">
                            </form>
                        </div>
                        <div class="col-5 border py-3 bg-white">
                            <h4>Selecciona cliente</h4>
                            <form method="POST" action="GestionProfesional">
                                <select name="cliente" class="form-control col-10 offset-1">
                                    <c:forEach var="cliente" items="${taller.clientes}">
                                        <option value="${cliente.id}"><c:out value="${cliente.id} - ${cliente.nombre} ${cliente.apellidos}"/></option><p>Ir</p>
                                        </c:forEach>
                                </select>
                                <input type="submit" value="Consultar" class="btn btn-primary mt-4">
                            </form>
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col-12 bg-white ">
                            <h4 class="text-center pt-3">RESULTADOS</h4>
                            <c:choose>
                                <%-- CONSULTA DE LAS REPARACIONES POR FECHA --%>
                                <c:when test="${vehiculos == null && reparacionConsulta != null}">
                                    <c:choose>
                                        <c:when test="${reparacionConsulta.size()>0}">
                                            <p class="text-left">Reparaciones fecha: <fmt:formatDate type="date" dateStyle="SHORT" value="${reparacionConsulta[0].fechaTerminacion}"/></p>
                                            <table class="table text-center">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th>Id Reparacion</th>
                                                        <th>Fecha solicitud</th>
                                                        <th>Fecha terminacion</th>
                                                        <th>ID cliente</th>
                                                        <th>Importe total</th>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="reparacionDeterminada" items="${reparacionConsulta}">
                                                        <tr>
                                                            <td>${reparacionDeterminada.id}</td>
                                                            <td><fmt:formatDate type="date" dateStyle="SHORT" value="${reparacionDeterminada.fechaSolicitud}"/></td>
                                                            <td><fmt:formatDate type="date" dateStyle="SHORT" value="${reparacionDeterminada.fechaTerminacion}"/></td>
                                                            <td>${reparacionDeterminada.vehiculo.cliente.id} - ${reparacionDeterminada.vehiculo.cliente.nombre}</td>
                                                            <c:forEach var="f" items="${reparacionDeterminada.actuaciones}">
                                                                <c:if test="${f.fechaFinalizacion != null}">
                                                                    <c:set var="total2" value="${total2 + f.presupuesto}"/>
                                                                </c:if>
                                                            </c:forEach>
                                                            <td><fmt:formatNumber value="${total2}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                                                        </tr>
                                                        <c:set var="total2" value="0"/>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:when>
                                        <c:otherwise>
                                            <p>No hay reparaciones finalizadas en la fecha indicada</p>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <%-- CONSULTA DE LAS REPARACIONES POR CLIENTE --%>
                                <c:when test="${vehiculos != null }">
                                    <p>Vehiculos de ${vehiculos[0].cliente}</p>
                                    <table class="table text-center">
                                        <thead class="thead-light">
                                            <tr>
                                                <th>Id Reparacion</th>
                                                <th>Fecha solicitud</th>
                                                <th>Fecha terminacion</th>
                                                <th>Actuaciones</th>
                                                <th>Importe total</th>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="v" items="${vehiculos}">
                                                <c:forEach var="rep" items="${v.reparaciones}">
                                                    <c:if test="${rep.fechaTerminacion != null}">
                                                        <tr>
                                                            <td>${rep.id}</td>
                                                            <td><fmt:formatDate type="date" dateStyle="SHORT" value="${rep.fechaSolicitud}"/> </td>
                                                            <td><fmt:formatDate type="date" dateStyle="SHORT" value="${rep.fechaTerminacion}"/></td>
                                                            <td>${rep.actuaciones.size()}</td>
                                                            <c:forEach var="act" items="${rep.actuaciones}">
                                                                <c:if test="${act.fechaFinalizacion != null}">
                                                                    <c:set var="total" value="${total + act.presupuesto}"/>
                                                                </c:if>
                                                            </c:forEach>
                                                            <td><fmt:formatNumber value="${total}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                                                            <c:set var="total" value="0"/><%-- COMPROBAR CON ACTUACIONES REALES--%>
                                                        </tr>
                                                    </c:if>

                                                </c:forEach>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                            </c:choose>
                        </div>  
                    </div>
                </div>


                <div id="container" class="col-5" style="height: 30em"></div>

            </div>
        </div>
    </c:if>

    <%-- Cuando el PROFESIONAL es el MECANICO JEFE ++++++++++++++++++++++++++++++++++++++++++++ --%>

    <c:choose>
        <c:when test="${profesional.tipo == 'mecanicoJefe'}">
            <div class="container-fluid col-11">
                <div class="row d-flex justify-content-around mt-3 bg-white ">
                    <div class="col-12 my-4" >
                        <h2 class="text-center text-uppercase display-4">Reparaciones en curso </h2>
                        <table class="table table-hover text-center ">
                            <thead class="thead-dark">
                                <tr>
                                    <th class="col-1">ID Reparacion</th>
                                    <th class="col-1">ID / Vehiculo</th>
                                    <th class="col-2">Cliente</th>
                                    <th class="col-1">Fecha</th>
                                    <th class="col-4">Descripcion averia</th>
                                    <th class="col-1">Actuaciones</th>
                                    <th colspan="2" class="col-2">Gestion</th></tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${taller.reparacionesEnCurso.size() == 0}">
                                        <tr><td colspan="6">No hay reparaciones pendientes</td></tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="r" items="${taller.reparacionesEnCurso}">

                                            <tr>
                                                <td><c:out value="${r.id}"/></td>
                                                <td><c:out value="${r.vehiculo.id} / ${r.vehiculo.marca}"/></td>
                                                <td><c:out value="${r.vehiculo.cliente.nombre} ${r.vehiculo.cliente.apellidos}"/></td>
                                                <td><fmt:formatDate type="date" dateStyle="SHORT" value="${r.fechaSolicitud}"/></td>
                                                <td><c:out value="${r.descripcionAveria}"/></td>
                                                <c:choose>

                                                    <c:when test="${r.actuaciones.size()>0 }">
                                                        <td>Si</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>No</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:url var="asignar" value="AsignarActuacion">
                                                    <c:param name="id" value="${r.id}"/>
                                                </c:url>
                                                <td><a href="${asignar}" class="btn btn-primary btn-block">Asignar Actuacion</a></td>
                                                <c:url var="cerrar" value="RegistrarReparacion">
                                                    <c:param name="id" value="${r.id}"/>
                                                    <c:param name="idp" value="${profesional.id}"/>
                                                    <c:param name="cerrar" value="true"/>
                                                </c:url>
                                                <td><a href="${cerrar}" class="btn btn-danger btn-block">Cerrar Reparacion</a></td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="container-fluid col-11">
                <div class="row d-flex justify-content-around mt-3 bg-white ">
                    <div class="col-12 my-4" >

                        <h2 class="text-center text-uppercase display-4">Estado de las actuaciones por reparacion</h2>
                        <c:forEach var="reparacionesActuales" items="${taller.reparacionesEnCurso}">        
                            <h3>Reparcion <c:out value="${reparacionesActuales.id}"/> </h3>

                            <table class="table table-hover text-center ">
                                <thead class="thead-light">
                                    <tr>
                                        <th class="col-1">ID Actuacion</th>
                                        <th class="col-6">Comentarios Mecánico</th>
                                        <th class="col-2">Profesional asignado</th>
                                        <th class="col-1">Inicio</th>
                                        <th class="col-1">Autorizada</th>
                                        <th class="col-1">Estado</th>
                                    </tr>
                                </thead>
                                <c:choose>
                                    <c:when test="${reparacionesActuales.actuaciones.size() == 0}">
                                        <tr><td colspan="5">No hay actuaciones asignadas</td></tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="actuaciones" items="${reparacionesActuales.actuaciones}">
                                            <tr>
                                                <td><c:out value="${actuaciones.id}"/></td>
                                                <td><c:out value="${actuaciones.comentarios}"/></td>
                                                <td><c:out value="${actuaciones.profesional.nombre}"/></td>
                                                <td><fmt:formatDate type="date" dateStyle="SHORT" value="${actuaciones.fechaInicio}"/></td>
                                                <c:choose>
                                                    <c:when test="${actuaciones.autorizada == 'T'}">
                                                        <td class="text-primary text-uppercase">SI</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="text-uppercase text-warning">NO</td>
                                                    </c:otherwise>
                                                </c:choose>

                                                <c:choose>
                                                    <c:when test="${actuaciones.finalizada == 'T'}">
                                                        <td class="text-primary text-uppercase">Finalizada</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="text-uppercase text-warning">Pendiente</td>
                                                    </c:otherwise>
                                                </c:choose>


                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </table>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="container-fluid col-11">
                <div class="row d-flex justify-content-around mt-3 bg-white ">
                    <div class="col-12 my-4" >
                        <h2 class="text-center text-uppercase display-4">Tus actuacciones pendientes de realizar</h2>
                        <table class="table table-hover text-center ">
                            <thead class="thead-light">
                                <tr>
                                    <th class="col-1">ID Reparación</th>
                                    <th class="col-1">ID Actuación</th>
                                    <th class="col-7">Descripción</th>
                                    <th class="col-1">Inicio</th>
                                    <th class="col-1">Gestion</th>

                            </thead>
                            <tbody>

                                <c:forEach var="a" items="${profesional.actuaciones}">

                                    <c:if test="${a.finalizada != 'T' && a.reparacion.fechaTerminacion == null && a.autorizada == 'T'}">
                                        <c:url var="actuacion" value="AsignarActuacion">
                                            <c:param name="id" value="${a.id}"/>
                                            <c:param name="realizar" value="true"/>
                                        </c:url>
                                        <tr>
                                            <td><c:out value="${a.reparacion.id}"/> </td>
                                            <td><c:out value="${a.id}"/></td>
                                            <td><c:out value="${a.descripcion}"/> </td>
                                            <td><fmt:formatDate type="date" dateStyle="SHORT" value="${a.fechaInicio}"/></td>
                                            <td><a href="${actuacion}" class="btn btn-primary btn-block">Realizar</a></td>
                                        </tr> 
                                    </c:if>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </c:when>
        <%-- Cuando el PROFESIONAL es el MECANICO +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ --%>
        <c:when test="${profesional.tipo == 'mecanico'}">
            <div class="container-fluid col-11">
                <div class="row d-flex justify-content-around mt-3 bg-white ">
                    <div class="col-12 my-4" >
                        <h2 class="text-center text-uppercase display-4">Tus actuacciones pendientes de relizar</h2>
                        <table class="table table-hover text-center ">
                            <thead class="thead-light">
                                <tr>
                                    <th class="col-1">ID Reparación</th>
                                    <th class="col-1">ID Actuación</th>
                                    <th class="col-7">Descripción</th>
                                    <th class="col-1">Inicio</th>
                                    <th class="col-1">Gestion</th>

                            </thead>
                            <tbody>

                                <c:forEach var="a" items="${profesional.actuaciones}">

                                    <c:if test="${a.finalizada != 'T' && a.reparacion.fechaTerminacion == null && a.autorizada == 'T'}">
                                        <c:url var="actuacion" value="AsignarActuacion">
                                            <c:param name="id" value="${a.id}"/>
                                            <c:param name="realizar" value="true"/>
                                        </c:url>
                                        <tr>
                                            <td><c:out value="${a.reparacion.id}"/> </td>
                                            <td><c:out value="${a.id}"/></td>
                                            <td><c:out value="${a.descripcion}"/> </td>
                                            <td><fmt:formatDate type="date" dateStyle="SHORT" value="${a.fechaInicio}"/></td>
                                            <td><a href="${actuacion}" class="btn btn-primary btn-block">Realizar</a></td>
                                        </tr> 
                                    </c:if>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>   
    <br>


    <script>
        Highcharts.chart('container', {
        chart: {
        renderTo: 'container',
                type: 'column'
        },
                title: {
                text: 'Reparaciones'
                },
                tooltip: {
                shared: true
                },
                xAxis: {
                categories: [
        <c:forEach var="c" items="${reparacionConsulta}" varStatus="status">
            <c:out value="${c.id}"/><c:out value="${status.last?'':','}"/>
        </c:forEach>
                ]
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
                name: 'Coste',
                        type: 'column',
                        zIndex: 2,
                        data: [
        <c:forEach var="g" items="${reparacionConsulta}" varStatus="status">
            <c:forEach var="g_act" items="${g.actuaciones}">
                <c:if test="${g_act.fechaFinalizacion != null}">
                    <c:set var="total3" value="${total3 + g_act.presupuesto}"/>
                </c:if>
            </c:forEach>
            <c:out value="${total3}"/><c:out value="${status.last?'':','}"/>
            <c:set var="total3" value="0"/>
        </c:forEach>
                        ]
                }]
        });
    </script>
</t:layout>