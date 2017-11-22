/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Valentina
 */
//Clase capitan
public class Captain extends Crewman{
     
    public Captain(int id, String name, String lastName) {
        super(id, name, lastName);
    }
    public Captain(int id, String name, String lastName,Ship ship) {
        super(id, name, lastName,ship);
    }

    public Captain() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
