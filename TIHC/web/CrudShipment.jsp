<%-- 
    Document   : CrudShipment
    Created on : 17-oct-2017, 15:13:11
    Author     : Sonico_Willie
--%>

<%@page import="java.util.List"%>
<%@page import="dao.DAOShipmentImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%DAOShipmentImpl shipment2=new DAOShipmentImpl();
  List<Object> listShipment=shipment2.read();
  request.setAttribute("shipment", listShipment);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shipments</title>
    </head>
    <body>
        <h1 align="center">Crud Shipment</h1>
        <table border="1" align="center" style="width:50%">
        <thead>
           <tr>
                <th>ID Shipment</th>
                <th>ID Contract</th>
                <th>ID Ship</th>
                <th>Weight</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Description</th>
                <th colspan=2>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="con" items="${shipment}">
                <tr>
                    <td>${con.getIdShipment()}</td>
                    <td>${con.getIdContract()}</td>
                    <td>${con.getIdShip()}</td>
                    <td>${con.getWeight()}</td>
                    <td>${con.getSource()}</td>
                    <td>${con.getDestination()}</td>
                    <td>${con.getDexcription()}</td>
                    <td><a href="UpdateShipment.jsp?Id=${con.getIdShipment()}">Update</a></td>
                    <td><a href="DeleteShipmentServlet?Id=${con.getIdShipment()}">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="CreateShipment.jsp">Add Shipment</a></p>
    </body>
</html>
