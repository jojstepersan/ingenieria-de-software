<%-- 
    Document   : CreatePort
    Created on : 4/10/2017, 08:38:01 PM
    Author     : usuario
--%>

<%@page import="java.util.List"%>
<%@page import="dao.DAOCountryImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%DAOCountryImpl country=new DAOCountryImpl();
  List<Object> litsCountry=country.read();
  request.setAttribute("countries", litsCountry);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create port</title>
    </head>
    <body>
        <section>
            <form action="CreatePort" method="post">
                <input type="number" placeholder="Number ID" name="id">
                <input type="text" placeholder="Name Port" name="namePort">
                <select name="country">
                    <c:forEach var="pais" items="${countries}">
                        <option > ${pais.getName()}</option>       
                    </c:forEach>
                </select>
                <button type="submit">Create</button>    
            </form>
        </section>  
    </body>
</html>
