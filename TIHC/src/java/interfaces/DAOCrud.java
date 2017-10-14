/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jojstepersan
 */
public interface DAOCrud {
    
    public void create(Object ob)throws SQLException;
    public void edit(Object ob)throws SQLException;
    public void delete(int id)throws SQLException;
    public List<Object> read()throws SQLException;
    
    
}
