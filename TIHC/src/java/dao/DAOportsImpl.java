package dao;

import Data.Country;
import Data.Port;
import interfaces.DAOCrud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOportsImpl extends ConnectionDB implements DAOCrud{

    public DAOportsImpl()
        {
        super();
        }

    @Override
    public void create(Object ob) throws SQLException {
        Port port=(Port)ob;
       
        insert=conexion.prepareStatement("INSERT INTO puerto(cod_puerto,nom_puerto,cod_pais)  VALUES(?,?,?);");
        insert.setInt(1,port.getId());
        insert.setString(2, port.getName());
        insert.setInt(3, port.getCountry().getId());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void edit(Object ob) throws SQLException {
       Port port=(Port)ob; 
       
        insert=conexion.prepareStatement("UPDATE puerto set nom_puerto=?,cod_pais=?  where cod_puerto=?;");
        insert.setString(1, port.getName());
        insert.setInt(2, port.getCountry().getId());
        insert.setInt(3,port.getId());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        
        insert=conexion.prepareStatement("DELETE FROM PUERTO where cod_puerto=?;");
        insert.setInt(1, id);
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public List<Object> read() throws SQLException {
        List<Object> listPort = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM PUERTO;");
        read = insert.executeQuery();
        while(read.next()){
            Port port;
            Country country=(new DAOCountryImpl()).getById(read.getInt("cod_pais")); 
            port = new Port(read.getInt("cod_puerto"), read.getString("nom_puerto"), country);
            listPort.add(port);
            }
        conexion.close();
        return listPort;
    }

    public Port getById(int id)throws SQLException
        {
        Port port=null;
        insert=conexion.prepareStatement("select * FROM PUERTO WHERE cod_puerto=?;");
        insert.setInt(1, id);
        read = insert.executeQuery();
        while(read.next())
            {
            Country country=(new DAOCountryImpl()).getById(read.getInt("cod_pais")); 
            port = new Port(read.getInt("cod_puerto"), read.getString("nom_puerto"), country);
            }
        conexion.close();
        return port;
        }
}
