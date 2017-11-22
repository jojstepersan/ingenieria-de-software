/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Data.*;
import interfaces.DAOCrud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Sonico_Willie
 */
public class DAOShipmentImpl extends ConnectionDB implements DAOCrud {
    
    public DAOShipmentImpl()
        {
        super();
        }

    @Override
    public void create(Object ob) throws SQLException {
        Shipment shipment = (Shipment)ob;
        insert=conexion.prepareStatement("INSERT INTO carga(cod_carga, cod_contrato, cod_barco, cod_tipo_carga, peso, origen, destino, descripcion)  VALUES(?,?,?,?,?,?,?,?);");
        insert.setInt(1, shipment.getIdShipment());
        insert.setString(2,shipment.getIdContract()); 
                
        insert.setInt(3, shipment.getIdShip());
        insert.setString(4,shipment.getIdShipmentType());
        insert.setString(5,shipment.getWeight());
        insert.setString(6,shipment.getSource());
        insert.setString(7,shipment.getDestination());
        insert.setString(8,shipment.getDescription());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void edit(Object ob) throws SQLException {
        Shipment shipment = (Shipment)ob;
        
        insert=conexion.prepareStatement("UPDATE carga set cod_contrato=?,cod_barco=?, cod_tipo_carga=?, peso=?, origen=?, destino WHERE cod_carga= ?;");
        insert.setString(2,shipment.getIdContract());
        insert.setInt(3, shipment.getIdShip());
        insert.setString(4,shipment.getIdShipmentType());
        insert.setString(5,shipment.getWeight());
        insert.setString(6,shipment.getSource());
        insert.setString(7,shipment.getDestination());
        insert.setString(8,shipment.getDescription());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        insert=conexion.prepareStatement("DELETE FROM carga where cod_carga=?;");
        insert.setInt(1,id);
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public List<Object> read() throws SQLException {
        
        List<Object> listShipment = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM carga;");
        read = insert.executeQuery();
        while(read.next()){
            Shipment shipment;
            shipment = new Shipment(read.getInt("cod_carga"),read.getInt("cod_contrato"),read.getInt("cod_barco"),read.getInt("cod_tipo_carga"),read.getInt("weight"),read.getString("origen"),read.getString("destino"),read.getString("descripcion"));
            listShipment.add(shipment);
            }
   
        return listShipment;
    }  
   
   public void cerrar() throws SQLException{
        conexion.close();
   }
   
      
   public Shipment getById(int id) throws SQLException
        {
        Shipment shipment=new Shipment();
        insert=conexion.prepareStatement("select * FROM carga where cod_carga=?;");
        insert.setInt(1, id);
        read = insert.executeQuery();
        while(read.next())
             shipment=new Shipment(read.getInt(1),read.getInt(2),read.getInt(3),read.getInt(4),read.getInt(5),read.getString(6),read.getString(7),read.getString(8));
        conexion.close();
        return shipment;
        } 
    
}

