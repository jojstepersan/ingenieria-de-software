/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Date;

/**
 *
 * @author Kevin
 */
public class Ship {
    private int codeShip;    
    private String dateAcquisition;
    private String dateOfLastMaintenance;
    private int state;

    public Ship(){}
    public Ship(int codeShip, String dateAcquisition, String dateOfLastMaintenance, int state) {
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

    public String getDateAcquisition() {
        return dateAcquisition;
    }

    public void setDateAcquisition(String dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    public String getDateOfLastMaintenance() {
        return dateOfLastMaintenance;
    }

    public void setDateOfLastMaintenance(String dateOfLastMaintenance) {
        this.dateOfLastMaintenance = dateOfLastMaintenance;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    
}
