<%-- 
    Document   : EditCaptain
    Created on : 4/10/2017, 12:45:46 AM
    Author     : Valentina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TIHC</title>
    </head>
    <body>
        <h1>Edit Captain</h1>
        <form action="EditPortServlet" method="POST">
            <div>Enter the number of the captain you want to edit </div>
              <input  type="number" name="numEditCaptain">
              <input name="Editar" type="submit" value="Editar">
        </form>
        
    </body>
</html>
