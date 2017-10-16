/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */package dao;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jojstepersan
 */
public class ConnectionDB{
    
    protected PreparedStatement insert;
    protected Statement statement;
    protected Connection conexion;
    protected ResultSet read;
    
    public ConnectionDB() 
        {
          try
            {
            Class.forName("com.mysql.jdbc.Driver");
            conexion=DriverManager.getConnection("jdbc:mysql://localhost/tihc?user=root&password=12345");
            statement=conexion.createStatement();    
            }catch(ClassNotFoundException e)
                {
                System.out.println("no a puesto el diver en la libreria");
                }
            catch(SQLException sql)
                {
                System.out.println("recuerde que la contraseñña es: 12345 y es user root");
                }
        }
}
