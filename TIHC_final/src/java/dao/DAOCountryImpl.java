/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Data.Country;
import interfaces.DAOCrud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jojstepersan
 */
public class DAOCountryImpl extends ConnectionDB implements DAOCrud{

    @Override
    public void create(Object ob) throws SQLException {
        Country country=(Country)ob;
        insert=conexion.prepareStatement("INSERT INTO pais(nom_pais,ubicacion_x,ubicacion_y) VALUES(?,?,?);");
        insert.setString(1, country.getName());
        insert.setDouble(2, country.getX());
        insert.setDouble(3,country.getY());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void edit(Object ob) throws SQLException {
   Country country=(Country)ob; 
        insert=conexion.prepareStatement("UPDATE pais set nom_pais=?,ubicacion_x=?,ubicacion_y=? where cod_pais=?;");
        insert.setString(1,country.getName() );
        insert.setDouble(2,country.getX());
        insert.setDouble(3, country.getY());
        insert.setInt(4, country.getId());
        insert.executeUpdate();
        conexion.close(); }

    @Override
    public void delete(int id) throws SQLException {
        insert=conexion.prepareStatement("DELETE FROM pais where cod_pais=?;");
        insert.setInt(1, id);
        insert.executeUpdate();
        conexion.close();}

    @Override
    public List<Object> read() throws SQLException 
        {
        List<Object> listCrewman = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM pais order by nom_pais;");
        read = insert.executeQuery();
        while(read.next()){
            Country country=new Country(read.getString(2),read.getInt(1), read.getDouble(3), read.getDouble(4));
            listCrewman.add(country);
            }
        conexion.close();
        return listCrewman; 
        }   
    
    public Country getById(int id) throws SQLException
        {
        Country country=new Country();
        insert=conexion.prepareStatement("select * FROM pais where cod_pais=?;");
        insert.setInt(1, id);
        read = insert.executeQuery();
        while(read.next())
             country=new Country(read.getString(2),read.getInt(1), read.getDouble(3), read.getDouble(4));
        conexion.close();
        return country;
        }
    
     public Country getByName(String name) throws SQLException
        {
        Country country=new Country();
        insert=conexion.prepareStatement("select * FROM pais where nom_pais=?;");
        insert.setString(1, name);
        read = insert.executeQuery();
        while(read.next())
             country=new Country(read.getString(2),read.getInt(1), read.getDouble(3), read.getDouble(4));
        conexion.close();
        return country;
        } 
  
}
