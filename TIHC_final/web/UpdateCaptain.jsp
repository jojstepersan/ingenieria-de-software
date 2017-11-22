<%-- 
    Document   : EditCaptain
    Created on : 4/10/2017, 12:45:46 AM
    Author     : Valentina
--%>
<%@page import="Data.Crewman"%>
<%@page import="dao.DAOCrewmanImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
DAOCrewmanImpl daoCrewman=new DAOCrewmanImpl();
int id=Integer.valueOf(request.getParameter("Id"));
Crewman crew =daoCrewman.getCrewman(id,2);
request.setAttribute("captain",crew);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TIHC</title>
    </head>
    <body>
       <form method="POST" action="EditCaptainServlet" >    
        <br>
        CaptainID:    
        <input type="text" name="id" value="${captain.getId()}" readonly/>        
        <br>Captain Name :
        <input type="text" name="name" value="${captain.getName()}" /> 
        <br>Last name:    
        <input type="text" name="lastName" value="${captain.getLastName()}" />  
        <br>Ship : 
        <input type="text" name="ship"  value="${captain.getShip().getCodeShip()}" /> 
        <br>
        <input type="submit" value="Submit" />
    </form>
    </body>
</html>
