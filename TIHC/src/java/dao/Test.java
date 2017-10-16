/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Data.Crewman;
import Data.Ship;
import Data.State;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Valentina
 */
public class Test {
    
    public static void main(String[] args) throws SQLException {
      DAOCrewmanImpl d=new DAOCrewmanImpl();
      //  Ship s=new Ship(1,new Date(1,12,1),new Date(2,12,2),new State(1, "bueno", "nice"));
        Crewman crew=new Crewman(11, "mario","lindarte");
       // System.out.println("crear");
       // System.out.println(crew);
       // d.create(crew);
       // crew.setName("stiven joj");
       // System.out.println("edit");
       // d.edit(crew);
       // System.out.println("delete");
      // d.delete(crew);
       // List<Object> list=d.read();
        //System.out.println(list);
//        for (int i=0;i<list.size();i++) {
//            Crewman crewman=(Crewman)list.get(i);
//            System.out.println(crewman.getName()+" "+crewman.getId());
//            
//        }

    }
}
