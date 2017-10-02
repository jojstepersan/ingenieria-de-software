/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Data.Crewman;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Valentina
 */
public interface DAOCrewman {
    public void create(Crewman ob)throws SQLException;
    public void edit(Crewman ob)throws SQLException;
    public void delete(Crewman ob)throws SQLException;
    public List<Crewman> read()throws SQLException;
    
}
