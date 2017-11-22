<%-- 
    Document   : ListCaptain
    Created on : 4/10/2017, 12:28:32 AM
    Author     : Valentina
--%>

<%@page import="Data.Captain"%>
<%@page import="java.util.List"%>
<%@page import="Data.Crewman"%>
<%@page import="dao.DAOCrewmanImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
     <%
        DAOCrewmanImpl daocrewman = new DAOCrewmanImpl();
        List<Object> listCrewman= daocrewman.read();            
        request.setAttribute("crewmans", listCrewman);%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Crud Crewman</h1>
        <div>
        <table border="1" align="center" style="width:50%">
        <thead>
           <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Barco</th>
                <th colspan=2>Action</th>
            </tr>   
        </thead>
        <tbody>
            <c:forEach var="con" items="${crewmans}">
                <tr>
                    <td>${con.getId()}</td>
                    <td>${con.getName()}</td>
                    <td>${con.getLastName()}</td>
                    <td>${con.getShip().getName()}</td>
                    <c:if test="${con.getClass().getName()=='Data.Captain'}">
                       <td><a href="UpdateCrewman.jsp?Id=${con.getId()}&&type=1">Update</a></td>
                    </c:if>
                    <c:if test="${con.getClass().getName()=='Data.Crewman'}">
                       <td><a href="UpdateCrewman.jsp?Id=${con.getId()}&&type=2">Update</a></td>
                    </c:if>
                     <td><a href="DeletedCrewmanServlet?Id=${con.getId()}">Delete</a></td>
                </tr>
               
            </c:forEach>
                 <a href="CreateCrewman.jsp?type=2">Create Crewman</a>  <br> 
                 <a href="CreateCaptain.jsp?type=1">Create Captain</a>  <br> 
        </tbody>
    </table>
    </div>
      
    </body>
</html>
