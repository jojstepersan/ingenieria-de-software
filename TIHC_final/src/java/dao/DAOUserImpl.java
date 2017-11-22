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
import java.util.ArrayList;
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
        insert=conexion.prepareStatement(
        "UPDATE usuario set nom_usuario=?,ape_usuario=?,username=?,pass=?"
        + " where cod_usuario=?;");
        insert.setString(1, user.getName());
        insert.setString(2, user.getLastName());
        insert.setString(3, user.getUsername());
        insert.setString(4, Hash.hash(user.getPass()));
        insert.setInt(5, user.getId());
        insert.executeUpdate();
        conexion.close();    
    }

    @Override
    public void delete(int id) throws SQLException {
        insert=conexion.prepareStatement("DELETE FROM USUARIO where cod_usuario=?;");
        insert.setInt(1,id);
        insert.executeUpdate();
        conexion.close();  }

    @Override
    public List<Object> read() throws SQLException {
       List<Object> listUsers = new ArrayList<>();
        insert=conexion.prepareStatement("select * FROM USUARIO;");
        read = insert.executeQuery();
        while(read.next())
            {
            User user=null;
            int type=read.getInt(2);
            if(type==3)
                user=new User(read.getInt(1),3, read.getString(3),read.getString(6),read.getString(4), read.getString(5));
            else if(type==4)
                user=new Admin(read.getInt(1),4, read.getString(3),read.getString(6),read.getString(4), read.getString(5));
            listUsers.add(user);
            }
        conexion.close();
        return listUsers;
    }
   
    public User login(String username,String pass)throws SQLException
        {
        insert=conexion.prepareStatement("select * FROM USUARIO where username=? and pass=?");
        insert.setString(1, username);
        insert.setString(2, Hash.hash(pass));
        read = insert.executeQuery();
        User user=null;
        while(read.next())
            {
            int type=read.getInt(2);
            if(type==3)
                user=new User(read.getInt(1),3, read.getString(3),read.getString(6),read.getString(4), read.getString(5));
            else if(type==4)
                user=new Admin(read.getInt(1),4, read.getString(3),read.getString(6),read.getString(4), read.getString(5));
            }    
        return user;
        }
    
}
