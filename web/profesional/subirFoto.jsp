<%-- 
    Document   : subirFoto
    Created on : 26-feb-2020, 0:35:34
    Author     : alberto
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="taller" class="modelo.Taller"/>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="Subir Foto"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        
    </jsp:include>
    <div class="container-fluid col-5">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h3 class="text-center">Subir una foto</h3>
                <form action="SubirFichero" method="POST"  enctype="multipart/form-data">
                    <%--<input type="hidden" name="id" value="${actuacion.id}">--%>
                    <input type="file" name="foto" class="form-control-file"><br>
                    <%--<c:set var="actuacion" value="${taller.buscarActuacion(request.getParameter('id'))}"/>--%>

                    <button name="ida" type="submit" value="${actuacion.id}" class="btn btn-primary btn-block text-uppercase">Enviar </button>
                </form>
            </div>
        </div>
    </div>
</t:layout>
