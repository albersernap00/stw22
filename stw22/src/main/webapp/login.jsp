<%-- 
    Document   : login
    Created on : 31 may. 2022, 16:23:43
    Author     : Alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN</title>
    </head>
    <body>
         <h1>LOGIN</h1>
        <hr>
        <br>
        <div style="background-color: red; color:white; font-size: 16px;">${msg}</div>
        <br>
        <form method="POST" action="<%=response.encodeURL("login")%>">
            <table>
                <tr>
                    <td>Username:</td>
                    <td><input name="username"></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="password" name="pwd"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Login"></td>
                </tr>
            </table>
        
        </form>
            
            <br><br>
            <a href="<%=response.encodeURL("addUsuario.jsp")%>">Dar de alta un nuevo usuario</a>
    </body>
</html>
