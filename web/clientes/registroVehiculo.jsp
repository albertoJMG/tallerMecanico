<%-- 
    Document   : registroVehiculo
    Created on : 18-feb-2020, 17:26:13
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<fmt:message bundle="${mensajes}" key="cabeceraV" var="cabeceraTitulo"></fmt:message>
<t:layout>
    <%-- Inicio Cabecera --%>
    <jsp:include page="../cabecera.jsp">
        <jsp:param name="titulo" value="${cabeceraTitulo}"/>
        <jsp:param name="cerrar" value="CerrarSesion"/>
        <jsp:param name="volver" value="gestionCliente.jsp"/>
    </jsp:include>
    <%-- Fin Cabecera --%>
    <div class="container-fluid col-7">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h1 class="text-center"><fmt:message bundle="${mensajes}" key="registrarVehiculo"/></h1>
                <form method="POST" action="RegistrarVehiculo">
                    <div class="form-group">
                        <label for="marca"><fmt:message bundle="${mensajes}" key="marca"/></label>
                        <input type="text" name="marca" id="marca" required class="form-control">
                        <br>
                        <label for="modelo"><fmt:message bundle="${mensajes}" key="modelo"/></label>
                        <input type="text" name="modelo" id="modelo"  required class="form-control">
                        <br>
                        <label for="fecha"><fmt:message bundle="${mensajes}" key="fechaMatriculacion"/></label>
                        <input type="date" name="fecha" id="fecha"  required class="form-control">
                        <br>
                        <label for="color">Color</label>
                        <input type="color" name="color" id="color" class="form-control col-5 ">
                        <br>
                        <input type="submit" value="<fmt:message bundle="${mensajes}" key="enviarVehiculo"/>" class="btn btn-primary btn-block ">
                    </div>
                </form>
            </div>
        </div>
    </div>
</t:layout>
