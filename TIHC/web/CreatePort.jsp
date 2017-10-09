<%-- 
    Document   : CreatePort
    Created on : 4/10/2017, 08:38:01 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       
       
         <section>
            <form action="CreatePort" method="post">
                <input type="number" placeholder="Number ID" name="id">
             <input type="text" placeholder="Name Port" name="namePort">
             <input type="text" placeholder="Country code " name="codeCountry">
             
             
             
                <button type="submit">Create</button>
            
        </form>
            </section>  
    </body>
</html>
