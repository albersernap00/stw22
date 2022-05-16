<%-- 
    Document   : index
    Created on : 16 may. 2022, 16:26:32
    Author     : Alberto
--%>

<%@page import="REST.client.LuzClienteRest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Worldmtyty!</h1>
        
        <%
            LuzClienteRest client = new LuzClienteRest();
            String prueba = client.getAllPrices(String.class);
            client.close();
        %>
        
        <h1><%=prueba%></h1>

    </body>
</html>
