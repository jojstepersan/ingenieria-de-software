<%-- 
    Document   : CrudCountry
    Created on : Oct 13, 2017, 4:29:38 PM
    Author     : jojstepersan
--%>

<%@page import="java.util.List"%>
<%@page import="dao.DAOCountryImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%DAOCountryImpl country2=new DAOCountryImpl();
  List<Object> listCountry=country2.read();
  request.setAttribute("country", listCountry);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Countries</title>
    </head>
    <body>
        <h1 align="center">Crud Country</h1>
        <table border="1" align="center" style="width:50%">
        <thead>
           <tr>
                <th>ID</th>
                <th>Country</th>
                <th>Locale x</th>
                <th>Locale y</th>
                <th colspan=2>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="con" items="${country}">
                <tr>
                    <td>${con.getId()}</td>
                    <td>${con.getName()}</td>
                    <td>${con.getX()}</td>
                    <td>${con.getY()}</td>
                    <td><a href="UpdateCountry.jsp?Id=${con.getId()}">Update</a></td>
                    <td><a href="DeleteCountryServlet?Id=${con.getId()}">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="CreateCountry.jsp">Add Country</a></p>
    </body>
</html>
