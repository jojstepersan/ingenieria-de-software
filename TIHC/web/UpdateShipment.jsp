<%-- 
    Document   : UpdateShipment
    Created on : 18-oct-2017, 17:27:15
    Author     : Sónico
--%>

<%@page import="Data.State"%>
<%@page import="java.util.List"%>
<%@page import="dao.DAOShipmentImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Update Shipment</h1>

<form action="UpdateShipment" method="post">
    <div>
        <label for="name">Shipment code:</label>
        <%
            String value = request.getParameter("idShipment");       
            out.println("<input type=\"text\" name=\"idShipment\" readonly=\"readonly\" value=\""+value+"\"/>");
         %>
         <br>
         <label for="name">Contract code:</label>
         <input type="text" name="idContract" s/> <br>
         <label for="name">Ship code:</label>
         <input type="text" name="idShip" s/> <br>
         <label for="name">Shipment type code:</label>
         <input type="text" name="idShipmentType" s/> <br>
         <label for="name">Weight:</label>
         <input type="text" name="weight" s/> <br>
         <label for="name">Source:</label>
         <input type="text" name="source" s/> <br>
         <label for="name">Destination:</label>
         <input type="text" name="destination" s/> <br>
         <label for="name">Description:</label>
         <input type="text" name="description" s/> <br>
         
    </div>    
    <div class="button">
        <button type="submit">Update</button>
    </div>
</form>
    </body>
</html>
