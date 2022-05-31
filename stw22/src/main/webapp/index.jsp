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
        <title>JSP Page</title>
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
                
        
        <script type="text/javascript" src="websocket.js"></script>
    </body>
</html>
