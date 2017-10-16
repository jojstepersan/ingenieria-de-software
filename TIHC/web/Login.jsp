<%-- 
    Document   : Login
    Created on : 27-sep-2017, 14:08:12
    Author     : Produccion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        
        <form id="formularioLogin" action="LoginServlet" method="POST">
            <table>

                <tr>
                    <td>Usuario:</td>
                    <td><input title="Se necesita un usuario" type="text"  name="user" placeholder="Escriba el nombre de su usuario" required  /></td>

                <tr>
                    <td>Contraseña:</td>
                    <td><input title="Se necesita una contraseña" type="password" name="password" placeholder="Escriba su contraseña" required/></td>
                </tr>
            </table>
            <input  type="submit" value="Ingresar" id="botonLogin" /><br>
        </form>
       
    </body>
</html>
