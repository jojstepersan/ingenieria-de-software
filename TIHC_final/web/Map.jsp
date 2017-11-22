<%-- 
    Document   : Map
    Created on : Nov 21, 2017, 11:54:18 PM
    Author     : jojstepersan
--%>

<%@page import="Data.Ship"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%
Ship perla=new Ship(1,"la niña","10-10-10", "10-10-10", 100, 1);
perla.setX(10.46);
perla.setY(-12.144);
request.setAttribute("perla", perla);
%>    
<c:set var="barco" value="${perla}" />    
<body>
    <h1>consultar ubicacion actual de la carga</h1>
    <input type="text" value="ingrese numero de contrato" />
    <input type="submit" value="enviar" onclick="myMap()">
    <div id="googleMap" style="width:100%;height:500px;"></div>
    <div>
        <label><strong>Coordenada en x</strong></label>
        <input  id="x" type="text" value="${barco.getX()}" readonly/>
        <label><strong>Coordenada en y</strong></label>
        <input id="y" type="text" value="${barco.getY()}" readonly/>
        
        
    </div>
<script>
    var x,y;
    function myMap() 
        {
        x=document.getElementById("x").value;
        y=document.getElementById("y").value;    
        var myCenter = new google.maps.LatLng(x,y);
        var mapCanvas = document.getElementById("googleMap");
        var mapOptions = {center: myCenter, zoom: 5};
        var map = new google.maps.Map(mapCanvas, mapOptions);
        var marker = new google.maps.Marker({position:myCenter});
        marker.setMap(map);
        }
   
/*function myMap() {
var mapProp= {
    center:new google.maps.LatLng(x,y),
    zoom:5
};
var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
}*/
</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAAZwsQck-KOha4ndKBo0JYY3WDfbagISM&callback=myMap"></script>
<!--
To use this code on your website, get a free API key from Google.
Read more at: https://www.w3schools.com/graphics/google_maps_basic.asp
-->

</body>
</html>
