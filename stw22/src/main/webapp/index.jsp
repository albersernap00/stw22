<%-- 
    Document   : index
    Created on : 16 may. 2022, 16:26:32
    Author     : Roberto Jiménez y Alberto Serna
--%>


<%@page import="javax.naming.InitialContext"%>
<%@page import="stw22.ejb.Sonoff"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    Sonoff sonoff = null;
    InitialContext ic = new InitialContext();
    sonoff = (Sonoff) ic.lookup("java:global/stw22/Sonoff");
    String estadoSonoff = "???";
    String onDisabled = "";
    String offDisabled = "";
    String color = "";
    
    if(sonoff.getEstado()){
        onDisabled = "DISABLED";
        offDisabled = "";
        color = "yellowgreen";
        estadoSonoff = "ENCENDIDO";
    }else{
        onDisabled = "";
        offDisabled = "DISABLED";
        color = "red";
        estadoSonoff = "APAGADO";
    }

%>
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
          Double gastoHoy = (Double) session.getAttribute("gastoHoy");
          if (nombreUsuario==null){
            session.setAttribute("msg", "ERROR: La sesión ha caducado.");
        %>
            <jsp:forward page="login.jsp"/>
        <%
          } 
        %>
        <h1>Hola <%=nombreUsuario%>!</h1>
        <table>
            <tr>

                <td>
                    <form action="addPreciosLuz">
                        <input type="submit" value="Añadir precio">
                    </form>
                </td>                
                
                <td>
                    <form action="cerrarSesion">
                        <input type="submit" value="Cerrar sesión">
                    </form>
                </td>
                <td>
                    <h5 id="usuariosDentro">Sesiones activas:: </h5>
                </td>
            </tr>
            <tr>
                
            </tr>
        </table>
        
        
        
        
        
        
        <h3>Selecciona la fecha para obtener el precio de la luz</h3>
        <input type="date" min="2022-05-31" id="datePrecioLuz" onchange="enviarFecha();">
        <div id="graficaPrecios"></div>  
        <br>
        <br>
        <h3>Selecciona la fecha para obtener las veces que se ha encencido el sonoff</h3>
        <input type="date" min="2022-05-31" id="dateGraficaBarras" onchange="enviarFechaHistorico();">
        <div id="graficaBarras"></div>  
       
        <div>
            <table>
            <tr>
                <td valign="top">
                    <fieldset>
                    <legend>Sonoff:</legend>
                    <table>
                        <tr>
                            <td>
                                <fieldset>
             
                                    <button id="botonON" type="submit" name="comando" value="1" <%=onDisabled%>  onclick="pruebaSwitch(value);">ON</button> 
                                    <button id="botonOFF" type="submit" name="comando" value="0" <%=offDisabled%> onclick="pruebaSwitch(value);">OFF</button>
                                    
                                    
                                <!--</form>-->
                                </fieldset>
                            </td>
                            <td>
                                <canvas id="canvas" style="background-color: <%=color%>"  width="50" height="50"></canvas>
                                
                            </td>       
                            
                            <td>
                                <fieldset>  
                                    <legend>Gasto Enchufe (€)</legend>                                    
                                    <div id="gastoEnchufe"><%=gastoHoy%></div>
                                </fieldset>
                                
                                
                            </td> 
                        </tr>
                        <tr>
                            <td valign="middle" colspan="2">
                                <div id="estado">Estado <%=estadoSonoff%> </div>
                            </td>                                 
                        </tr>
                    </table>
                    </fieldset>
                </td>
                
            </tr>
            </table>
        </div>
        
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
        google.charts.load('current', {'packages':['corechart', 'bar'], language:'es'});
        google.charts.setOnLoadCallback(initGraficaBarras);        
        </script>
    </body>
</html>
