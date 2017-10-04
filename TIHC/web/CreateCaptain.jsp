<%-- 
    Document   : createCaptain
    Created on : 3/10/2017, 11:35:02 PM
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
        <h1>Create Captain</h1>
      
        <section>
            <form action="CreateCaptainServlet" method="post">
                <input type="number" placeholder="Number ID" name="id">
             <input type="text" placeholder="Name Captain" name="nameCaptain">
             <input type="text" placeholder="Last name Captain" name="lastNameCaptain">
             
             
             
                <button type="submit">Create</button>
            
        </form>
            </section>  
    </body>
</html>
