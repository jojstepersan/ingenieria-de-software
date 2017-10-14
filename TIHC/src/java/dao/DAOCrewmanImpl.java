/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Data.Captain;
import Data.Crewman;
import Data.Ship;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.DAOCrud;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentina
 */
public class DAOCrewmanImpl extends ConnectionDB implements DAOCrud{

    public DAOCrewmanImpl()
        {
        super();
        }

    @Override
    public void create(Object ob) throws SQLException {
        Crewman crewman=(Crewman)ob;
        int type=(crewman instanceof Captain)?1:2;
        insert=conexion.prepareStatement("INSERT INTO tripulante(cod_empleado,nom_empleado,ape_empleado,tipo_empleado)  VALUES(?,?,?,?);");
        insert.setInt(1, crewman.getId());
        insert.setString(2, crewman.getName());
        insert.setString(3, crewman.getLastName());
        insert.setInt(4,type);
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void edit(Object ob) throws SQLException {
        Crewman crewman=(Crewman)ob; 
        int type=(crewman instanceof Captain)?1:2;
        insert=conexion.prepareStatement("UPDATE tripulante set cod_barco=?,nom_empleado=?,ape_empleado=?,tipo_empleado=? where cod_empleado=?;");
        insert.setInt(1, crewman.getShip().getCodeShip());
        insert.setString(2, crewman.getName());
        insert.setString(3, crewman.getLastName());
        insert.setInt(4,type);
        insert.setInt(5, crewman.getId());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        insert=conexion.prepareStatement("DELETE FROM TRIPULANTE where cod_empleado=?;");
        insert.setInt(1, id);
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public List<Object> read() throws SQLException {
        List<Object> listCrewman = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM TRIPULANTE;");
        read = insert.executeQuery();
        while(read.next()){
            int type=read.getInt("tipo_empleado");
            Crewman crewman;
            if(type==1)
                crewman = new Captain(read.getInt("cod_empleado"), read.getString("nom_empleado"), read.getString("ape_empleado"),new Ship());
            else
                crewman = new Crewman(read.getInt("cod_empleado"), read.getString("nom_empleado"), read.getString("ape_empleado"),new Ship());
            listCrewman.add(crewman);
            }
        conexion.close();
        return listCrewman;
    }

    
    public Crewman getCrewman(int id) 
        {
        Crewman crewman = new Crewman();
        try {
            insert=conexion.prepareStatement("select * from tripulante where cod_empleado=? and tipo_empleado=1;");
            insert.setInt(1, id);
            read=insert.executeQuery();
            while(read.next()){
                crewman.setId(read.getInt(1));
                crewman.setName(read.getString(3));
                crewman.setLastName(read.getString(4));
              }
            conexion.close();
            } catch (SQLException ex) 
                {
                System.out.println("este usuario no existe");
                }
        return crewman;
        }

}
