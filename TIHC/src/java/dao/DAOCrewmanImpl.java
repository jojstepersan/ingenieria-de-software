/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Data.Captain;
import Data.Crewman;
import Data.Ship;
import interfaces.DAOCrewman;
import interfaces.DAOObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentina
 */
public class DAOCrewmanImpl extends ConnectionDB implements DAOCrewman{

    public DAOCrewmanImpl()
        {
        super();
        }

    @Override
    public void create(Crewman ob) throws SQLException {
        int type=(ob instanceof Captain)?1:2;
//        if( ob instanceof Captain)
//            type=1;
//        else
//            type=2;
System.out.println("tipo "+type);
        insert=conexion.prepareStatement("INSERT INTO tripulante VALUES(?,?,?,?,?);");
        insert.setInt(1, ob.getId());
        System.out.println("id "+ob.getId());
        insert.setInt(2, ob.getShip().getCodeShip());
        System.out.println("barco "+ob.getShip().getCodeShip());
        insert.setString(3, ob.getName());
        insert.setString(4, ob.getLastName());
        insert.setInt(5,type);
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void edit(Crewman ob) throws SQLException {
         int type=(ob instanceof Captain)?1:2;
//        if( ob instanceof Captain)
//            type=1;
//        else
//            type=2;
        insert=conexion.prepareStatement("UPDATE tripulante set cod_barco=?,nom_empleado=?,ape_empleado=?,tipo_empleado=? where cod_empleado=?;");
        insert.setInt(1, ob.getShip().getCodeShip());
        System.out.println("barco "+ob.getShip().getCodeShip());
        insert.setString(2, ob.getName());
        System.out.println("name "+ob.getName());
        insert.setString(3, ob.getLastName());
        insert.setInt(4,type);
        insert.setInt(5, ob.getId());
        insert.executeUpdate();
        conexion.close();
     
    }

    @Override
    public void delete(Crewman ob) throws SQLException {
        int type=(ob instanceof Captain)?1:2;
//        if( ob instanceof Captain)
//            type=1;
//        else
//            type=2;
        insert=conexion.prepareStatement("DELETE FROM TRIPULANTE where cod_empleado=?;");
        insert.setInt(1, ob.getId());
        insert.executeUpdate();
        conexion.close();
    
    }

    @Override
    public List<Crewman> read() throws SQLException {
        List<Crewman> listCrewman = new ArrayList<>();
        System.out.println("holis");
        insert=conexion.prepareStatement("select * FROM TRIPULANTE;");
        System.out.println("query");
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

}
