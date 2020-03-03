<%-- 
    Document   : asignarActuacion
    Created on : 22-feb-2020, 12:40:05
    Author     : alberto
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="Actuacion. Asignacion del trabajo"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="gestionProfesional.jsp"/>
    </jsp:include>
    <div class="container-fluid col-5">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h1 class="text-center">Asignar Actuacion</h1>
                <h2 class="text-center">ID reparacion: ${reparacionRecibeActuacion.id}</h2>
                <form method="POST" action="AsignarActuacion">
                    <input type="hidden" name="id" value="${reparacionRecibeActuacion.id}">
                    <input type="hidden" name="profesionalID" value="${profesional.id}">
                    <label for="descripcion">Descripcion</label>
                    <textarea name="descripcion" id="descripcion" class="form-control" required></textarea>
                    <br>
                    <label for="responsable">Mecanico responsable</label>
                    <select name="responsable" id="responsable" class="form-control">
                        <c:forEach var="p" items="${profesionalesActivos}">
                            <c:if test="${p.activo == 'T'}">
                                <option value="${p.id}">${p.nombre} - ${p.id}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <br>
                    <label for="presupuesto">Presupuesto</label>
                    <input type="number" name="presupuesto" id="presupuesto" step="0.01" class="form-control" required>
                    <br>
                    <input type="submit" value="ASIGNAR" class="btn btn-primary btn-block text-uppercase">
                </form>
            </div>
        </div>
    </div>
</t:layout>