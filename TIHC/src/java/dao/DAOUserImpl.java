/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Data.Admin;
import Data.User;
import interfaces.DAOCrud;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jojstepersan
 */

public class DAOUserImpl extends ConnectionDB implements DAOCrud {

    @Override
    public void create(Object ob) throws SQLException {
        User user=(User)ob;
        insert=conexion.prepareStatement("insert into usuario values(?,?,?,?,?,?)");
        insert.setInt(1, user.getId());
        insert.setInt(2, user.getTipoEmpleado());
        insert.setString(3, user.getName());
        insert.setString(4, user.getLastName());
        insert.setString(5, user.getUsername());
        insert.setString(6, user.getPass()); 
        insert.executeUpdate();
        conexion.close();    
          
     
        }

    @Override
    public void edit(Object ob) throws SQLException {
         User user=(User)ob;
        insert=conexion.prepareStatement("update usuario values(?,?,?,?,?,?)");
        insert.setInt(1, user.getId());
        insert.setInt(2, user.getTipoEmpleado());
        insert.setString(3, user.getName());
        insert.setString(4, user.getLastName());
        insert.setString(5, user.getUsername());
        insert.setString(6, user.getPass()); 
        insert.executeUpdate();
        conexion.close();    
          
    }

    @Override
    public void delete(Object ob) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> read() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) throws SQLException {
        DAOUserImpl dao=new DAOUserImpl();
        User us=new Admin();
        us.setId(1);
        us.setName("Mario");
        us.setLastName("Herrera");
        us.setUsername("marioherrera");
        us.setTipoEmpleado(3);
        us.setPass("1234");    
        
        
        
        
        
        
        dao.create(us);
    }
    
}
