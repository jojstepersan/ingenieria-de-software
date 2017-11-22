<%-- 
    Document   : UpdateShip
    Created on : 13/10/2017, 11:43:30 PM
    Author     : Kevin
--%>

<%@page import="Data.State"%>
<%@page import="java.util.List"%>
<%@page import="dao.DAOShipImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>actualizar</h1>

<form action="UpdateShip" method="post">
    <div>
        <label for="name">codigo barco:</label>
        <%
            String value = request.getParameter("cod");       
            out.println("<input type=\"text\" name=\"cod\" readonly=\"readonly\" value=\""+value+"\"/>");
         %>
         <br>
         <label for="msg">Name:</label>
         <input type="text" name="name" s/> <br>
         <label for="msg">Weight:</label>
         <input type="number" name="weight" s/>
    </div>
    <div>
        <label for="mail">estado :</label>
        
        <select name="state">
        <%
           DAOShipImpl dao= new DAOShipImpl();
           List estados=dao.StateList();
           State es;
           for(int i=0;i<estados.size();i++){
           es=(State)estados.get(i);
           out.println("<option value=\""+es.getCode()+"\">"+es.getName()+"</option>");
           }
           dao.cerrar();
         %>
        </select>
    </div>
    <div>
        <label for="msg">fecha aquisicion:</label>
        <input type="date" name="dateac">
    </div>
    <div>
        <label for="msg">fecha manteminiemto:</label>
        <input type="date" name="datema">
    </div>
    
    <div class="button">
        <button type="submit">Actualizar</button>
    </div>
</form>
    </body>
</html>
