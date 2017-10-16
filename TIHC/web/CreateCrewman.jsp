<%-- 
    Document   : Crear tripulacion
    Created on : 27/09/2017, 02:46:56 PM
    Author     : Valentina
--%>

<%@page import="dao.DAOCrewmanImpl"%>
<%@page import="Data.Crewman"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    DAOCrewmanImpl daoCrewman = new DAOCrewmanImpl();
    int type = Integer.valueOf(request.getParameter("type"));
    request.setAttribute("type", type);
%>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crewman</title>
    <body>
        <h1>Create Crewman</h1>

        <section>
            <c:if test="${type==2}">
                <form action="CreateCaptainServlet" method="post">
            </c:if
            <c:if test="${type==1}">
                <form action="CreateCrewmanServlet" method="post">
            </c:if>
            <input type="number" placeholder="Number ID" name="id">
            <input type="text" placeholder="Name Crewman" name="name">
            <input type="text" placeholder="Last name Crewman" name="lastName">            


            <button type="submit">Create</button>

        </form>
    </section>  
</body>



</html>
