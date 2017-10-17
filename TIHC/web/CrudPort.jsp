<%-- 
    Document   : PortList
    Created on : 4/10/2017, 08:39:10 PM
    Author     : usuario
--%>


<%@page import="Data.Port"%>
<%@page import="java.util.List"%>
<%@page import="dao.DAOportsImpl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%DAOportsImpl dao=new DAOportsImpl();
  List<Object> listPorts=dao.read();
  request.setAttribute("ports", listPorts);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <h1 align="center">Crud Ports</h1>
        <table border="1" align="center" style="width:50%">
        <thead>
           <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Country</th>
                <th colspan=2>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="port" items="${ports}">
                <tr>
                    <td>${port.getId()}</td>
                    <td>${port.getName()}</td>
                    <td>${port.getCountry().getName()}</td>
                    <td><a href="UpdatePort.jsp?Id=${port.getId()}">Update</a></td>
                    <td><a href="DeletePort?Id=${port.getId()}">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="CreatePort.jsp">Add Port</a></p>
    </body>
</html>
