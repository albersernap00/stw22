<%-- 
    Document   : index
    Created on : 16 may. 2022, 16:26:32
    Author     : Alberto
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    </head>
    <body>
        <%
          String nombreUsuario = (String)session.getAttribute("username");
          if (nombreUsuario==null){
            session.setAttribute("msg", "ERROR: La sesión ha caducado.");
        %>
            <jsp:forward page="login.jsp"/>
        <%
          } 
        %>
        <h1>Hello Worldmtyty! <%=nombreUsuario%></h1>
        <form action="listarPrecios">
            <input type="submit" value="Listar">
        </form>
        <form action="addPreciosLuz">
            <input type="submit" value="Añadir precio">
        </form>
        <form action="addUsuario">
            <input type="submit" value="Añadir Usuario">
        </form>
        <form action="resetearUsuarios">
            <input type="submit" value="Resetar usuarios">
        </form>
        <form action="cerrarSesion">
            <input type="submit" value="Cerrar sesión">
        </form>
        <h1 id="prueba"></h1>
        <input type="date" min="2022-05-31" id="datePrecioLuz" onchange="enviarFecha();">
        <div id="graficaPrecios"></div>  
        <input type="date" min="2022-05-31" id="dateGraficaBarras" onchange="">
        <div id="graficaBarras"></div>  
        
        
        
        <script type="text/javascript" src="websocket.js"></script>
        <!--<script type="text/javascript" src="websocketsingleton.js"></script>-->
        <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart'], language:'es'});

        /*google.charts.setOnLoadCallback(
            function() { // Anonymous function that calls drawChart1 and drawChart2
                initCo2GraphLive();
                initCo2GraphLog();
             });*/
        google.charts.setOnLoadCallback(initGrafica);
        //google.charts.setOnLoadCallback(initCo2GraphLog);

        </script>
        
        <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart'], language:'es'});

        /*google.charts.setOnLoadCallback(
            function() { // Anonymous function that calls drawChart1 and drawChart2
                initCo2GraphLive();
                initCo2GraphLog();
             });*/
        google.charts.setOnLoadCallback(initGraficaBarras);
        //google.charts.setOnLoadCallback(initCo2GraphLog);

        </script>
    </body>
</html>
