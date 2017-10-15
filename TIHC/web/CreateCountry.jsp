<%-- 
    Document   : CreateCountry
    Created on : Oct 15, 2017, 3:23:20 PM
    Author     : jojstepersan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Country</title>
    </head>
    <body>
        <h1>Create country</h1>
        <form action="CreateCountryServlet" method="post" >
        
        <br>Country Name :
        <input type="text" name="name" value="${country.getName()}" /> 
        <br>Locale X:    
        <input type="text" name="x" value="${country.getX()}" />  
        <br>Locale Y : 
        <input type="text" name="y"value="${country.getY()}" /> 
        <br>
        <input type="submit" value="Submit" />
        </form>
    </body>
</html>
