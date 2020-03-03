<%-- 
    Document   : layout
    Created on : 28-feb-2020, 15:42:45
    Author     : alberto
--%>

<%@tag description="layout" pageEncoding="ISO-8859-15"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <!-- HIGHCHARTS -->
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/modules/pareto.js"></script>
        <!-- Estilos propios -->
        <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- Estilos fuera de las carpetas de clientes y profesional -->
        <link rel="stylesheet" type="text/css" href="../css/style.css"> <!-- Estilos dentro de las carpetas de clientes y profesional -->
        <!-- FUENTES GOOGLE -->
        <link href="https://fonts.googleapis.com/css?family=Anton&display=swap" rel="stylesheet">
        <title>Taller Mecanico</title>
    </head>
    <body>
        <jsp:doBody/>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>