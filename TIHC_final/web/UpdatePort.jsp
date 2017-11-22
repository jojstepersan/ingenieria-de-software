<%-- 
    Document   : EditPort
    Created on : 4/10/2017, 08:38:16 PM
    Author     : usuario
--%>

<%@page import="java.util.List"%>
<%@page import="dao.DAOCountryImpl"%>
<%@page import="Data.Port"%>
<%@page import="dao.DAOportsImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%
    DAOportsImpl dao = new DAOportsImpl();
    DAOCountryImpl c = new DAOCountryImpl();
    List<Object> countries = c.read();
    int id = Integer.valueOf(request.getParameter("Id"));
    Port port = dao.getById(id);
    request.setAttribute("countries", countries);
    request.setAttribute("port", port);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EDIT PORT</title>
    </head>
    <body>
        <form action="EditPort" method="POST">
            <br>
            Port ID:    
            <input type="text" name="id" value="${port.getId()}" readonly/>        
            <br>Port Name :
            <input type="text" name="name" value="${port.getName()}" /> 
                    <br>country     
            <select name="country">
                <c:forEach var="country" items="${countries}">
                    <option > ${country.getName()}</option>       
                </c:forEach>
            </select>
                    <br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
