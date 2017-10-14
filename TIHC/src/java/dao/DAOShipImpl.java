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
        conexion.close();
        return listShip;
    }
    public Ship One_ship(int cod) throws SQLException{
        Ship h;
        insert=conexion.prepareStatement("select * FROM BARCO WHERE cod_barco=?");
        insert.setInt(1,cod);
        read = insert.executeQuery();
        while(read.next()){
            Ship ship;
            h= new Ship(read.getInt("cod_barco"), read.getString("fecha_adquisicion"), read.getString("fecha_ultimo_mantenimiento"),read.getInt("cod_estado"));
            return h;
            }
        conexion.close();
        
        return null;
        
    }
    
}
