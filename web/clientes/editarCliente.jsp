<%-- 
    Document   : editarCliente
    Created on : 02-mar-2020, 19:50:43
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<fmt:message bundle="${mensajes}" key="cabeceraE" var="cabeceraTitulo"></fmt:message>
<t:layout>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="${cabeceraTitulo}"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="gestionCliente.jsp"/>
    </jsp:include>
    <div class="container-fluid col-5">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h1 class="text-center"><fmt:message bundle="${mensajes}" key="editarCuenta"/></h1>
                <form method="POST" action="RegistroCliente">
                    <input type="hidden" name="idC" value="${cliente.id}">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" class="form-control" value="${cliente.email}" required>
                    <br>
                    <label for="direccion"><fmt:message bundle="${mensajes}" key="direccion"/></label>
                    <input type="text" name="direccion" id="direccion" value="${cliente.direccion}" class="form-control" required>
                    <br>
                    <label for="password"><fmt:message bundle="${mensajes}" key="cambiarPassword"/></label>
                    <input type="password" name="password" id="password" class="form-control" required>
                    <br>
                    <input type="submit" value="<fmt:message bundle="${mensajes}" key="editar"/>" class="btn btn-primary btn-block text-uppercase">
                </form>
            </div>
        </div>
    </div>
</t:layout>
