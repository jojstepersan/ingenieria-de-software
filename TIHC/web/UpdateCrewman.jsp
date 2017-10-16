<%-- 
    Document   : EditCaptain
    Created on : 4/10/2017, 12:45:46 AM
    Author     : Valentina
--%>

<%@page import="dao.DAOShipImpl"%>
<%@page import="java.util.List"%>
<%@page import="Data.Crewman"%>
<%@page import="dao.DAOCrewmanImpl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
DAOCrewmanImpl daoCrewman=new DAOCrewmanImpl();
int id=Integer.valueOf(request.getParameter("Id"));
int type=Integer.valueOf(request.getParameter("type"));
Crewman crew =daoCrewman.getCrewman(id,type);
request.setAttribute("crewman",crew);
request.setAttribute("type", type);
DAOShipImpl daoShip = new DAOShipImpl();
List<Object> litsShip=daoShip.read();
System.out.println(litsShip);
  request.setAttribute("ships", litsShip);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TIHC</title>
    </head>
    <body>
       <form method="POST" action="EditCrewmanServlet" >    
        <br>
        <c:if test="${type==2}">
            CrewmanID:    
            <input type="text" name="id" value="${crewman.getId()}" readonly/>        
            <br>Crewman Name :
            <input type="text" name="name" value="${crewman.getName()}" />  
        </c:if>
        <c:if test="${type==1}">
            CaptainID:    
            <input type="text" name="id" value="${crewman.getId()}" readonly/>        
            <br>Captain Name :
            <input type="text" name="name" value="${crewman.getName()}" />  
        </c:if>
        <br>Last name:    
        <input type="text" name="lastName" value="${crewman.getLastName()}" />  
                <br>ship: 
        <select name="ship">
                    <c:forEach var="ship" items="${ships}">
                        <option > ${ship.getName()}</option>       
                    </c:forEach>
        </select>
        <br>
        <input type="submit" value="Submit" />
    </form>
    </body>
</html>
