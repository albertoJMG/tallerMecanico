<%-- 
    Document   : editarEmpleado
    Created on : 22-feb-2020, 9:20:52
    Author     : alberto
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>

    <c:choose>
        <c:when test="${!empty p.id}">
            <jsp:include page="../cabecera.jsp">
                <jsp:param name="titulo" value="Edicion de profesionales"/>
                <jsp:param name="cerrar" value="CerrarSesion"/>
                <jsp:param name="volver" value="gestionProfesional.jsp"/>
            </jsp:include>
            <div class="container-fluid col-5">
                <div class="row d-flex justify-content-around mt-3 bg-white ">
                    <div class="col-10 my-4" >
                        <h1 class="text-center">Editando Profesional: ${p.id}</h1>
                        <form method="POST" action="CrearProfesional">
                            <input type="hidden" name="editando" value="true">
                            <input type="hidden" name="id" value="${p.id}">
        </c:when>
        <c:otherwise>
            <jsp:include page="../cabecera.jsp">
                <jsp:param name="titulo" value="Registro de profesionales"/>
                <jsp:param name="cerrar" value="CerrarSesion"/>
                <jsp:param name="volver" value="gestionProfesional.jsp"/>
            </jsp:include>
            <div class="container-fluid col-5">
                <div class="row d-flex justify-content-around mt-3 bg-white ">
                    <div class="col-10 my-4" >
                        <h1 class="text-center">Registrar nuevo Profesional</h1>
                        <form method="POST" action="CrearProfesional">
        </c:otherwise>
    </c:choose>
                    <label for="nombre">Nombre</label>
                    <input type="text" name="nombre" id="nombre" value="${p.nombre}" class="form-control" required>

                    <label for="apellidos">Apellidos</label>
                    <input type="text" name="apellidos" id="apellidos"  value="${p.apellidos}" class="form-control" required>

                    <label for="dni">DNI</label>
                    <input type="text" name="dni" id="dni" value="${p.dni}" class="form-control" required maxlength="9">

                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" value="${p.email}" class="form-control" required>

                    <label for="tipo">Tipo Profesional</label>
                    <select name="tipo" id="tipo" class="form-control" required>
                        <option value="mecanico">Mecánico</option>
                        <option value="mecanicoJefe" ${p.tipo == "mecanicoJefe"?"selected":""}>Mecánico Jefe</option>
                    </select>

                    <label for="tipo">Especialidad</label>
                    <select name="especialidad" id="especialidad" class="form-control" required>
                        <option value="Motor" ${p.especialidad == "Motor"?"selected":""}>Motor</option>
                        <option value="Electricidad" ${p.especialidad == "Electricidad"?"selected":""}>Electricidad</option>
                        <option value="Chapa y Pintura" ${p.especialidad == "Chapa y Pintura"?"selected":""}>Chapa y pintura</option>
                    </select>

                    <p>Situacion</p>
                    <div class="form-check form-check-inline">
                        <input type="radio" id="activo" value="T" class="form-check-input" name="activo" ${p.activo == "T"?"checked":""}>
                        <label for="activo" class="form-check-label">Activo</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="radio" id="noActivo" value="F" class="form-check-input" name="activo" ${p.activo == "F"?"checked":""}>
                        <label for="noActivo" class="form-check-label">No Activo</label>
                    </div>
                    <br>
                    <label for="nombreUsuario" class="mt-2">Nombre Usuario</label>
                    <input type="text" name="nombreUsuario" id="nombreUsuario"value="${p.nombreUsuario}" class="form-control" required>

                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" class="form-control" required>

                    <input type="submit" value="ACEPTAR" class="btn btn-primary btn-block text-uppercase mt-4">
                        <c:if test="${error != null}">
                            <h3 style="text-align: center; background-color: red; color: white">
                                <c:out value="${error}"/>
                            </h3>
                        </c:if>
                </form>
            </div>
        </div>
    </div>
</t:layout>
