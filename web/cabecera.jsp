<%-- 
    Document   : cabecera
    Created on : 28-feb-2020, 16:16:56
    Author     : alberto
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle var="mensajes" basename="recursos.mensajes"/>


<div class="row d-flex justify-content-around bg-info align-items-center pt-2 noImprimir" style="height: 8em">
    <c:choose>
        <c:when test="${cliente != null}">
            <p  class="text-uppercase text-light "><fmt:message bundle="${mensajes}" key="bienvenido"/><span class="cabe"> <c:out value="${cliente.nombre}"/></span></p>
        </c:when>
        <c:otherwise>
            <p class="text-uppercase text-light ">Bienvenido <span class="cabe"><c:out value="${profesional.nombre}"/></span></p>
        </c:otherwise>
    </c:choose>

    <h1 class="text-uppercase">${param.titulo}</h1>
    <div >
        <c:choose>
            <c:when test="${cliente != null}">
                <p class="text-uppercase noImprimir"><a href="${param.cerrar}" style="color: white" class="btn btn-primary btn-block"><fmt:message bundle="${mensajes}" key="cerrarSesion"/></a></p>
                <p class="text-uppercase noImprimir"><a href="${param.volver}" style="color: white" class="btn btn-primary btn-block"><fmt:message bundle="${mensajes}" key="volver"/></a></p>
            </c:when>
            <c:when test="${profesional != null}">
                <p class="text-uppercase noImprimir"><a href="${param.cerrar}" style="color: white" class="btn btn-primary btn-block">Cerrar Sesion</a></p>
                <p class="text-uppercase noImprimir"><a href="${param.volver}" style="color: white" class="btn btn-primary btn-block">Volver</a></p>
            </c:when>
        </c:choose>
    
    </div>
</div>

