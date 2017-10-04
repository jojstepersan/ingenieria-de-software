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
//clase tripulante
public class Crewman  extends Employee{
    protected Ship ship;
    
  
    
    public Crewman(int id, String name, String lastName) {
        super(id, name, lastName);
     }
    public Crewman(int id, String name, String lastName,Ship ship) {
        super(id, name, lastName);
        this.ship=ship;
    }

    public Crewman() {}

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
    
    @Override
    public String toString()
        {
        return this.getName()+" "+this.getId();
        }
}
