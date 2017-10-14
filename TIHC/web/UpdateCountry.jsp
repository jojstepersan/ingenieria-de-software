<%-- 
    Document   : UpdateCountry
    Created on : Oct 13, 2017, 8:40:20 PM
    Author     : jojstepersan
--%>

<%@page import="Data.Country"%>
<%@page import="dao.DAOCountryImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
DAOCountryImpl dao=new DAOCountryImpl();
int id=Integer.valueOf(request.getParameter("Id"));
Country country=dao.getById(id);
request.setAttribute("country",country);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>edit country</title>
    </head>
    <body>
       <form method="POST" action="EditCountryServlet" name="frmAddUser">    
        <br>
        Country ID:    
        <input type="text" name="id" value="${country.getId()}" readonly/>
        
        <br>Country Name :
        <input type="text" name="name" value="${country.getName()}" /> 
        <br>Locale X:    
        <input type="text" name="x" value="${country.getX()}" />  
        <br>Locale Y : 
        <input type="text" name="y"value="${country.getY()}" /> 
        <br>
        <input type="submit" value="Submit" />
    </form>
    </body>
</html>
