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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>List Captain</h1>
        
       <%DAOCrewmanImpl daocrewman = new DAOCrewmanImpl();
         List<Object> listCrewman= daocrewman.read(); %>
         <table id="tablaVerContacto">
            <tr><td>#id   </td><td>Name    </td><td>Last name     </td>  <%
          
        for(int i=0;i<listCrewman.size();i++)
            {
             Crewman aux=(Crewman)listCrewman.get(i);
             if(aux instanceof Captain){
        %>
            <tr ><td><%=aux.getId()%></td><td><%=aux.getName() %></td><td><%=aux.getLastName() %></td></tr>
            <%}}%>
       
       </table>
    </body>
</html>
