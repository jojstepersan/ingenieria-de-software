<%-- 
    Document   : DeletedCaptain
    Created on : 4/10/2017, 12:18:01 AM
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
        <h1>Deleted Captain</h1>
      
        <section>

            <form action="DeletedCaptainServlet" method="post">

                <input type="number" placeholder="Number ID" name="id">
                        
                <button type="submit">Deleted</button>
            
        </form>
            </section>  
    </body>
</html>
