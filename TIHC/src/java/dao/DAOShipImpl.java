/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.DAOObject;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class DAOShipImpl extends ConnectionDB implements DAOObject {

    @Override
    public void create(Object ob) throws SQLException {
        
    }

    @Override
    public void edit(Object ob) throws SQLException {
        
    }

    @Override
    public void delete(Object ob) throws SQLException {
       
    }

    @Override
    public List<Object> read() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
