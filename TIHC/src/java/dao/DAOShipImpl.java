/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Data.*;
import interfaces.DAOObject;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class DAOShipImpl extends ConnectionDB implements DAOObject {
    
    public DAOShipImpl()
        {
        super();
        }

    @Override
    public void create(Object ob) throws SQLException {
        Ship ship = (Ship)ob;
  
        insert=conexion.prepareStatement("INSERT INTO barco(cod_barco,cod_estado,fecha_adquisicion,fecha_ultimo_mantenimiento)  VALUES(?,?,?,?);");
        insert.setInt(1, ship.getCodeShip());
        insert.setInt(2, ship.getState());
        insert.setString(3,ship.getDateAcquisition());
        insert.setString(4,ship.getDateOfLastMaintenance());
        insert.executeUpdate();
        conexion.close();
        
    }

    @Override
    public void edit(Object ob) throws SQLException {
        Ship ship = (Ship)ob;
        
        insert=conexion.prepareStatement("UPDATE barco set cod_estado=? ,fecha_adquisicion=? ,fecha_ultimo_mantenimiento = ?   WHERE cod_barco= ?;");
        insert.setInt(4, ship.getCodeShip());
        insert.setInt(1, ship.getState());
        insert.setString(2,ship.getDateAcquisition());
        insert.setString(3,ship.getDateOfLastMaintenance());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void delete(Object ob) throws SQLException {
        Ship ship=(Ship)ob;
        insert=conexion.prepareStatement("DELETE FROM BARCO where cod_barco=?;");
        insert.setInt(1,ship.getCodeShip());
        insert.executeUpdate();
        conexion.close();
       
    }

    @Override
    public List<Object> read() throws SQLException {
        
        List<Object> listShip = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM BARCO;");
        read = insert.executeQuery();
        while(read.next()){
            Ship ship;
            ship = new Ship(read.getInt("cod_barco"), read.getString("fecha_adquisicion"), read.getString("fecha_ultimo_mantenimiento"),read.getInt("cod_estado"));
               
            listShip.add(ship);
            }
   
        return listShip;
    }
   public String state_text (int cod) throws SQLException{
       String nom = "";
       insert=conexion.prepareStatement("select nom_estado FROM ESTADO WHERE cod_estado=?");
        insert.setInt(1,cod);
        read = insert.executeQuery();
        while(read.next()){
            nom= read.getString("nom_estado");
           
            }
       return nom;     
   }
   
   
   public List<Object> StateList() throws SQLException {   
        List<Object> listStates = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM estado;");
        read = insert.executeQuery();
        while(read.next()){
            State estado;
            estado = new State(read.getInt("cod_estado"), read.getString("nom_estado"), read.getString("descripcion"));
               
            listStates.add(estado);
            }
   
        return listStates;
    }
   
   
   public void cerrar() throws SQLException{
        conexion.close();
   }
}
