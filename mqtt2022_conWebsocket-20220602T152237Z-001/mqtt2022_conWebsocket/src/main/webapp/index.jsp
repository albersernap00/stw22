<%-- 
    Document   : index
    Created on : 27-abr-2020, 17:06:04
    Author     : fsern
--%>

<%@page import="demo.util.Time"%>
<%@page import="demo.ejb.Raspberry"%>
<%@page import="demo.ejb.Sonoff"%>

<%@page import="javax.naming.InitialContext"%>
 <%
    Sonoff sonoff = null;
    Raspberry rpi = null;
    InitialContext ic = new InitialContext();
    sonoff  = (Sonoff)ic.lookup("java:global/mqtt2022_conWebsocket/Sonoff");
    rpi     = (Raspberry)ic.lookup("java:global/mqtt2022_conWebsocket/Raspberry");
    
    String onDisabled   = "";
    String offDisabled  = "";
    String colorSonoff  = "";
    String estadoSonoff = "???";
    
    if (sonoff.getEstado()){
        onDisabled  = "DISABLED";
        offDisabled = "";
        colorSonoff       = "yellowgreen";
        estadoSonoff      = "ENCENDIDO";
    }else{
        onDisabled  = "";
        offDisabled = "DISABLED";
        colorSonoff       = "red";
        estadoSonoff      = "APAGADO";
    }
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
body {font-family: "Lato", sans-serif; font-size: 12px;}
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MQTT demo</title>
    </head>
    <body>
        <h1>MQTT demo 25/04/2022 - v0.6-SÍNCRONA</h1>
        <hr>
        <br>
        <br>
        <div id="queHoraEs"></div>
        <br>
        <table>
            <tr>
                <td valign="top">
                    <fieldset>
                    <legend>Sonoff:</legend>
                    <table>
                        <tr>
                            <td>
                                <fieldset>
                                <form method="POST" id="sonoff" action="switchSonoff">
                                    <button id="botonON" type="submit" form="sonoff" name="comando" value="ON" <%=onDisabled%>>ON</button> 
                                    <button id="botonOFF" type="submit" form="sonoff" name="comando" value="OFF" <%=offDisabled%>>OFF</button> 
                                </form>
                                </fieldset>
                            </td>

                            <td>
                                <canvas id="canvas" style="background-color: <%=colorSonoff%>"  width="50" height="50"></canvas>
                            </td>        

                            <td valign="middle">
                                <div id="estado">Estado: <%=estadoSonoff%></div>
                            </td>    
                        </tr>
                    </table>
                           
                    </fieldset>
                </td>
                <td valign="top">
                    <fieldset>
                        <legend>Raspberry:</legend>
                        
                        <br>
                        <table>
                            <tr>
                                <td colspan="2" align="center">
                                    <div id="rpiTime"> <%=Time.getDDMMYYYY(rpi.getMs())%> @ <%=Time.getHHMMSS(rpi.getMs())%></div>
                                    <hr>
                                </td>
                            </tr>
                            <tr>
                                <td>Temperatura:</td>
                                <td align="center"><b><div id="rpiTemp" style="font-size: 20px;"><%=rpi.getTemp()%> ºC</div></b></td>
                            </tr>
                            <tr>
                                <td>Presión:</td>
                                <td align="center"><b><div id="rpiPress" style="font-size: 18px;"><%=rpi.getPress()%> HPa</div></b></td>
                            </tr>
                            <tr>
                                <td>CPU temp:</td>
                                <td align="center"><b><div id="rpiTempCpu" style="font-size: 18px;"><%=rpi.getTempCpu()%> ºC</div></b></td>
                            </tr>
                        </table>

                    </fieldset>
                </td>
            </tr>
        </table>
    </body>
    
    <script src="websocket_demo.js"></script>
</html>
