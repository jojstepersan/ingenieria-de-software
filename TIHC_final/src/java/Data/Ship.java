package Data;

import dao.DAOShipImpl;
import java.sql.SQLException;

/**
 *
 * @author Kevin
 */
public class Ship {
    private int codeShip;    
    private String name;
    private String dateAcquisition;
    private String dateOfLastMaintenance;
    private int weight;
    private int state;

    public Ship(int codeShip, String name, String dateAcquisition, String dateOfLastMaintenance, int weight, int state) {
        this.codeShip = codeShip;
        this.name = name;
        this.dateAcquisition = dateAcquisition;
        this.dateOfLastMaintenance = dateOfLastMaintenance;
        this.weight = weight;
        this.state = state;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
 public Ship(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String state_text (int cod) throws SQLException{
      DAOShipImpl dao = new DAOShipImpl();
       String nom =dao.state_text(cod);
       return nom;     
   }
    
    
}

