<%-- 
    Document   : editarActuacion
    Created on : 23-feb-2020, 12:48:21
    Author     : alberto
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="Actuacion. Finalizando actuacion"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="gestionProfesional.jsp"/>
    </jsp:include>
    <div class="container-fluid col-5">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h1 class="text-center">Registrando la actuacion nº ${actuacion.id}</h1>
                <form method="POST" action="AsignarActuacion">
                    <input type="hidden" name="id" value="${actuacion.reparacion.id}">
                    <input type="hidden" name="ida" value="${actuacion.id}">
                    <input type="hidden" name="profesionalID" value="${profesional.id}">
                    <label for="comentario">Descripcion</label>
                    <textarea name="comentario" id="comentario"  required class="form-control"></textarea>
                    <c:url var="foto" value="SubirFichero">
                        <c:param name="id" value="${actuacion.id}"/>
                    </c:url>
                    <a href="${foto}" class="btn btn-primary btn-block text-uppercase mt-3">Subir Foto</a>
                    <input type="submit" value="ASIGNAR" class="btn btn-primary btn-block text-uppercase">
                </form>
            </div>
        </div>
    </div>
</t:layout>
