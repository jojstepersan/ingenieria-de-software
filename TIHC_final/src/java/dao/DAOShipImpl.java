/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Valentina
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Data.*;
import interfaces.DAOCrud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class DAOShipImpl extends ConnectionDB implements DAOCrud {
    
    public DAOShipImpl()
        {
        super();
        }

    @Override
    public void create(Object ob) throws SQLException {
        Ship ship = (Ship)ob;
        insert=conexion.prepareStatement("INSERT INTO barco(cod_barco,nom_barco,cod_estado,fecha_adquisicion,fecha_ultimo_mantenimiento,peso)  VALUES(?,?,?,?,?,?);");
        insert.setInt(1, ship.getCodeShip());
        insert.setString(2,ship.getName());
        insert.setInt(3, ship.getState());
        insert.setString(4,ship.getDateAcquisition());
        insert.setString(5,ship.getDateOfLastMaintenance());
       insert.setInt(6, ship.getWeight());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void edit(Object ob) throws SQLException {
        Ship ship = (Ship)ob;
        
        insert=conexion.prepareStatement("UPDATE barco set cod_estado=? ,fecha_adquisicion=? ,fecha_ultimo_mantenimiento = ?,nom_barco=?   WHERE cod_barco= ?;");
        insert.setInt(5, ship.getCodeShip());
        insert.setInt(1, ship.getState());
        insert.setString(2,ship.getDateAcquisition());
        insert.setString(3,ship.getDateOfLastMaintenance());
        insert.setString(4,ship.getName());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        insert=conexion.prepareStatement("DELETE FROM BARCO where cod_barco=?;");
        insert.setInt(1,id);
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
            ship = new Ship(read.getInt("cod_barco"),read.getString(2),read.getString("fecha_adquisicion"), read.getString("fecha_ultimo_mantenimiento"),read.getInt(3),read.getInt("cod_estado"));
            listShip.add(ship);
            }
        conexion.close();
   
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
   
   public Ship getByName(String name) throws SQLException
        {
        Ship ship=new Ship();
        insert=conexion.prepareStatement("select * FROM barco where nom_barco=?;");
        insert.setString(1, name);
        read = insert.executeQuery();
        while(read.next())
             ship=new Ship(read.getInt(1),read.getString(2),read.getString(5),read.getString(6),read.getInt(3),read.getInt(4));
        conexion.close();
        return ship;
        } 

    
   public Ship getById(int id) throws SQLException
        {
        Ship ship=new Ship();
        insert=conexion.prepareStatement("select * FROM barco where cod_barco=?;");
        insert.setInt(1, id);
        read = insert.executeQuery();
        while(read.next())
             ship=new Ship(read.getInt(1),read.getString(2),read.getString(5),read.getString(6),read.getInt(3),read.getInt(4));
        conexion.close();
        return ship;
        } 
    
}

