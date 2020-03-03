<%-- 
    Document   : login
    Created on : 18-feb-2020, 17:42:11
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>
<t:layout>
    <div class="jumbotron">
        <h1 class="display-4 text-center">Taller Mecánico. JSP</h1>
        <p class="lead text-center border col-4 offset-4 bg-primary">Taller Mecánico On-Line. No reparamos On-Line, gestionamos On-Line</p>
        
    </div>
    <div class="row my-5 d-flex justify-content-around">
        <div class="col-4" >
            <h1 class="text-center mb-5"><fmt:message bundle="${mensajes}" key="tituloLogin"/></h1>
            <form method="POST" action="LoginClientes">
                <div class="form-group">
                    <label for="login"> <fmt:message bundle="${mensajes}" key="nombreUsuario"/> </label>
                    <input type="text" name="login" id="login" value="a1" class="form-control">
                    <br>
                    <label for="password"><fmt:message bundle="${mensajes}" key="password"/></label>
                    <input type="password" name="password" id="password" value="11" class="form-control">
                    <br>
                    <input type="submit" value="<fmt:message bundle="${mensajes}" key="login"/>" class="btn btn-primary btn-block text-uppercase">
                </div>
            </form>
            <a href="registroCliente.jsp" class="btn btn-primary btn-block text-uppercase"><fmt:message bundle="${mensajes}" key="registroCliente"/></a>
        </div>
        <div class="col-4" >
            <h1 class="text-center mb-5">Login Profesionales</h1>
            <form method="POST" action="LoginProfesionales">
                <label for="login"> Profesional </label>
                <input type="text" name="login" id="login" value="admin" class="form-control">
                <br>
                <label for="password">Password</label>
                <input type="password" name="password" id="password" value="admin" class="form-control">
                <br>
                <input type="submit" value="Iniciar Sesion" class="btn btn-primary btn-block">
            </form>
        </div>
    </div>
    <!--<a href="index.html" class="btn btn-primary btn-block"></a>-->
    <c:if test="${!empty requestScope.error}">
        <h3 style="text-align: center; background-color: red; color: white">
            <c:out value="${error}"/>
        </h3>
    </c:if>

</t:layout>
