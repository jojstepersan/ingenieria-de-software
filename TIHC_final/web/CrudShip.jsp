<%-- 
    Document   : CrudShip
    Created on : 22/11/2017, 12:46:14 AM
    Author     : Kevin
--%>

            
<%@page import="java.util.List"%>
<%@page import="dao.DAOShipImpl"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
        DAOShipImpl daoShip = new DAOShipImpl();
        try{
        List listShips= daoShip.read();
        request.setAttribute("ship", listShips);
    
        }catch(Exception e){}
        
    %>
            <html>
            <head>
            <style> table, th, td {border: 1px solid black;}</style>
            <title>Servlet Readship</title>
            </head>
            <body>
            <h1>Lista de Barcos</h1>
            <table style='width:100%'>
            <tr>
            <th>Codigo barco</th>
            <th>Name Ship</th>
            <th>weight</th>
            <th>Estado</th>
            <th>Fecha Adquisicion</th>
            <th>Fecha Manteminmiento</th>
            <th colspan=2 >Accion</th>
            </tr>
            <c:forEach var="aux" items="${ship}">    
                <tr>
                <td>${aux.getCodeShip()}</td>
                <td>${aux.getName()}</td>
                <td>${aux.getWeight()}</td>
                <td>${aux.state_text(aux.getState())}</td>
                <td>${aux.getDateAcquisition()}</td>
                <td>${aux.getDateOfLastMaintenance()}</td>
                <td><form action="UpdateShip.jsp" method="post">
                <input type="hidden" value="${aux.getCodeShip()}" name="cod" />
                <button type="submit">Actualizar</button>
                </form></td>
                <td><form action="DeleteShip" method="post">
                <input type="hidden" value="${aux.getCodeShip()}" name="cod" />
                 <button type="submit">Borrar</button>
                </form></td>
                </tr>
            
           </c:forEach>    
            </table>
            <a href="CreateShip.jsp">Add ship</a>
            </body>
            </html>
