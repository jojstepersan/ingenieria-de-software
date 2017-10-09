package dao;



import Data.Port;
import dao.ConnectionDB;
import interfaces.DAOCrud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        insert.setInt(3, port.getId_country());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void edit(Object ob) throws SQLException {
       Port port=(Port)ob; 
       
        insert=conexion.prepareStatement("UPDATE puerto set (cod_puerto,nom_puerto,cod_pais)  VALUES(?,?,?);");
        insert.setInt(1,port.getId());
       insert.setString(2, port.getName());
       insert.setInt(3, port.getId_country());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public void delete(Object ob) throws SQLException {
        Port port=(Port)ob; 
        insert=conexion.prepareStatement("DELETE FROM PUERTO where cod_puerto=?;");
        insert.setInt(1, port.getId());
        insert.executeUpdate();
        conexion.close();
    }

    @Override
    public List<Object> read() throws SQLException {
        List<Object> listPort = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM PUERTO;");
        read = insert.executeQuery();
        while(read.next()){
            int type=read.getInt("tipo_empleado");
           Port port;
           
                port = new Port(read.getInt("cod_puerto"), read.getString("nom_puerto"), read.getInt("cod_pais"));
        
            listPort.add(port);
            }
        conexion.close();
        return listPort;
    }

    


}
