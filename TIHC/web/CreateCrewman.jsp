<%-- 
    Document   : Crear tripulacion
    Created on : 27/09/2017, 02:46:56 PM
    Author     : Valentina
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crewman</title>
        <body>
        <h1>Create Crewman</h1>
      
        <section>
            <form action="CreateCrewmanServlet" method="post">
                <input type="number" placeholder="Number ID" name="id">
             <input type="text" placeholder="Name Crewman" name="nameCrewman">
             <input type="text" placeholder="Last name Crewman" name="lastNameCrewman">
             
             
             
                <button type="submit">Create</button>
            
        </form>
            </section>  
    </body>
        