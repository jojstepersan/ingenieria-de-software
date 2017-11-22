<%-- 
    Página creada para ingresar datos de la carga
--%>

<%@page import="java.util.List"%>
<%@page import="dao.DAOShipmentImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>agregar</h1>

<form action="CreateShip" method="post">
    <div>
        <label for="name">Shipment Code:</label>
        <input type="number" name="Cod_Shipment" />
    </div>
    <div>
        <label for="name">Contract Code:</label>
        <input type="number" name="Cod_Contract" />
        <br
        <label for="name">Ship Code:</label>
        <input type="number" name="Cod_Ship" />
    </div>    
    <div>
        <label for="name">Weight:</label>
        <input type="number" name="Weight" />
    </div>
    <div>
        <label for="name">Source:</label>
        <input type="text" name="Source" />
    </div>
    <div>
        <label for="name">Destination:</label>
        <input type="text" name="Destination" />
    </div>
    <div>
        <label for="name">Description:</label>
        <input type="text" name="Description" />
    </div>
    
    <div class="button">
        <button type="submit">Submit</button>
    </div>
</form>
    </body>
</html>