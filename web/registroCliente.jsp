<%-- 
    Document   : crearCliente
    Created on : 18-feb-2020, 11:59:34
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<t:layout>
    <div class="container-fluid col-5">
        <div class="row d-flex justify-content-around mt-3 bg-white ">
            <div class="col-10 my-4" >
                <h1 class="text-center"><fmt:message bundle="${mensajes}" key="crearCuentaCliente"/></h1>
                <form method="POST" action="RegistroCliente">
                    <label for="nombre"><fmt:message bundle="${mensajes}" key="nombre"/></label>
                    <input type="text" name="nombre" id="nombre" class="form-control" required >
                    <br>
                    <label for="apellidos"><fmt:message bundle="${mensajes}" key="apellidos"/></label>
                    <input type="text" name="apellidos" id="apellidos" class="form-control" required>
                    <br>
                    <label for="dni"><fmt:message bundle="${mensajes}" key="idCliente"/></label>
                    <input type="text" name="dni" id="dni" class="form-control" required maxlength="9">
                    <br>
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" class="form-control" required>
                    <br>
                    <label for="direccion"><fmt:message bundle="${mensajes}" key="direccion"/></label>
                    <input type="text" name="direccion" id="direccion" class="form-control">
                    <br>
                    <label for="nombreUsuario"><fmt:message bundle="${mensajes}" key="nombreUsuario"/></label>
                    <input type="text" name="nombreUsuario" id="nombreUsuario" class="form-control" required>
                    <br>
                    <label for="password"><fmt:message bundle="${mensajes}" key="password"/></label>
                    <input type="password" name="password" id="password" class="form-control" required>
                    <br>
                    <input type="submit" value="<fmt:message bundle="${mensajes}" key="registroCliente"/>" class="btn btn-primary btn-block text-uppercase">
                    <a href="login.jsp" class="btn btn-primary btn-block text-uppercase"><fmt:message bundle="${mensajes}" key="volver"/></a>
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
