<%-- 
    Document   : PortList
    Created on : 4/10/2017, 08:39:10 PM
    Author     : usuario
--%>


<%@page import="Data.Port"%>
<%@page import="java.util.List"%>

<%@page import="dao.DAOportsImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>List Ports</h1>
        
       <%DAOportsImpl daoport = new DAOportsImpl();
         List<Object> listPort= daoport.read(); %>
         <table id="tablaVerPuerto">
            <tr><td>#id   </td><td>Name    </td><td>Country code    </td>  <%
          
        for(int i=0;i<listPort.size();i++)
            {
             Port aux=(Port)listPort.get(i);
             {
        %>
            <tr ><td><%=aux.getId()%></td><td><%=aux.getName() %></td><td><%=aux.getId_country() %></td></tr>
            <%}}%>
       
       </table>
    </body>
</html>
