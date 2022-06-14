<%-- 
    Document   : addUsuario
    Created on : 31 may. 2022, 16:42:18
    Author     :  Roberto Jiménez y Alberto Serna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <hr>
        <br>
        <a href="index.jsp">Volver</a>
        <br><br>
        
        <legend>Crear Usuario</legend>
        <div style="background-color: red; color:white; font-size: 16px;">${msg}</div>
        <form method="POST" action="<%=response.encodeURL("addUsuario")%>">
            <table>
                <tr>
                    <td>Username</td>
                    <td><input name="username"></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="pwd" name="pwd"></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="Dar de Alta"></td>
                </tr>
            </table>
        </form>
        
    </body>
</html>
