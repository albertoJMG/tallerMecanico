<%-- 
    Document   : registroProfesional
    Created on : 18-feb-2020, 16:25:19
    Author     : alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registro Profesional</h1>
        <form method="POST" action="CrearProfesional">

            <label for="nombre">Nombre</label>
            <input type="text" name="nombre" id="nombre"  required>
            <br>
            <label for="apellidos">Apellidos</label>
            <input type="text" name="apellidos" id="apellidos"  required>
            <br>
            <label for="dni">DNI</label>
            <input type="text" name="dni" id="dni" maxlength="9">
            <br>
            <label for="email">Email</label>
            <input type="email" name="email" id="email">
            <br>
            <label for="tipo">Tipo Profesional</label>
            <select name="tipo" id="tipo">
                <option value="mecanico">Mecánico</option>
                <option value="mecanicoJefe">Mecánico Jefe</option>
            </select>
            <br>
            <select name="especialidad" id="especialidad">
                <option value="motor">Motor</option>
                <option value="electricidad">Electricidad</option>
                <option value="mecanicoJefe">Chapa y pintura</option>
            </select>
            <br>
            <label>Situación</label>
            <input type="radio" id="activo" value="T" name="activo" >Activo
            <input type="radio" id="noActivo" value="F" name="activo">No activo
            
            <br>
            <label for="nombreUsuario">nombreUsuario</label>
            <input type="text" name="nombreUsuario" id="nombreUsuario" required>
            <br>
            <label for="password">Password</label>
            <input type="password" name="password" id="password" required>
            <br>

            <input type="submit" value="REGISTRARSE">
        </form>
    </body>
</html>
