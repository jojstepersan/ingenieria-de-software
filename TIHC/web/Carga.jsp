<%-- 
    Página creada para ingresar datos de la carga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ingresar datos de Carga</title>
    </head>
    <body>
        <h1>Insertar datos de Carga</h1>
        <p>Codigo de la carga: <input id="txt_cod_carga"></p>
        <p>Código del contrato: <input id="txt_cod_contrato"></p>
        <p>Código del barco: <input id="txt_cod_barco"></p>
        <p>Código del tipo de carga: <input id="txt_cod_tipo_carga"></p>   
        <p>Peso (en Kgs.): <input type="text" id="txt_peso"></p>
        <p>Origen: <input type="text" input id="txt_origen"></p>
        <p>Destino: <input type="text" input id="txt_destino"></p>
        <p>Descripcion: <input type="text" id="txt_descripcion"></p>
        <button type="button">Enviar</button><br>
    </body>
</html>
