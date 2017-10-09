/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Date;


/**
 *
 * @author Valentina
 */
//Clase barco
public class Ship {
    private int codeShip;    
    private Date dateAcquisition;
    private Date dateOfLastMaintenance;
    private State state;

    public Ship(){}
    public Ship(int codeShip, Date dateAcquisition, Date dateOfLastMaintenance, State state) {
        this.codeShip = codeShip;
        this.dateAcquisition = dateAcquisition;
        this.dateOfLastMaintenance = dateOfLastMaintenance;
        this.state = state;
    }

    public int getCodeShip() {
        return codeShip;
    }

    public void setCodeShip(int codeShip) {
        this.codeShip = codeShip;
    }

    public Date getDateAcquisition() {
        return dateAcquisition;
    }

    public void setDateAcquisition(Date dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    public Date getDateOfLastMaintenance() {
        return dateOfLastMaintenance;
    }

    public void setDateOfLastMaintenance(Date dateOfLastMaintenance) {
        this.dateOfLastMaintenance = dateOfLastMaintenance;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    
    
}
