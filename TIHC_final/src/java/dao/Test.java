/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;



/**
 *
 * @author Valentina
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        ConnectionDB c=new ConnectionDB();
        DAOShipImpl barco=new DAOShipImpl();
        barco.read();
    }
    
}
