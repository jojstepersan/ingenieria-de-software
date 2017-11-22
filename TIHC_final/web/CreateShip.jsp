<%-- 
    Document   : CreateShip
    Created on : 14/10/2017, 12:13:54 AM
    Author     : Kevin
--%>

<%@page import="java.util.List"%>
<%@page import="Data.State"%>
<%@page import="dao.DAOShipImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>agregar</h1>

<form action="CreateShip" method="post">
    <div>
        <label for="name">codigo barco:</label>
        <input type="text" name="cod" required/>
    </div>
        <div>
        <label for="name">Name ship:</label>
        <input type="text" name="name" required />
        <br
        <label for="name">Weight ship:</label>
        <input type="number" name="weight" required />
    </div>    <div>
        <label for="mail">estado :</label>
         <select name="state" required>
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
        <input type="date" name="dateac" required>
    </div>
    <div>
        <label for="msg">fecha manteminiemto:</label>
        <input type="date" name="datema" required>
    </div>
    
    <div class="button">
        <button type="submit">Agregar</button>
    </div>
</form>
    </body>
</html>
