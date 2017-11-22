<%-- 
    Document   : CreateContract
    Created on : 21-nov-2017, 23:54:18
    Author     : Produccion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear contrato</title>
    </head>
    <body>
        <form id="formularioContrato" method="post" action="CreateContractServlet">
            
            <input type="text" name="nombre" placeholder="Nombre">
            <input type="number" name="identificacion" placeholder="Identificacion">
            <input type="text" name="tipoCarga" placeholder="Tipo de carga">
            <input type="number" name="cantidadPeso" placeholder="Peso de la carga">
            <input type="text" name="origen" placeholder="Origen">
            <input type="text" name="destino" placeholder="Destino">
            <input type="text" name="descripcion" placeholder="Descripcion o recomendaciones">
            <h2>Para el numero de radicacion ingrese su cedula seguido de un numero de 3 cifras</h2>
            <input type="text" name="Numero de radicacion" placeholder="Numero de radicacion">
            
            <button type="submit">Radicar contrato</button>
        </form>
    </body>
</html>
