package Data;
/**
 *
 * @author Kevin
 */
public class Ship {
    private int codeShip;    
    private String name;
    private String dateAcquisition;
    private String dateOfLastMaintenance;
    private int state;

    public Ship(int codeShip, String name, String dateAcquisition, String dateOfLastMaintenance, int state) {
        this.codeShip = codeShip;
        this.name = name;
        this.dateAcquisition = dateAcquisition;
        this.dateOfLastMaintenance = dateOfLastMaintenance;
        this.state = state;
    }

    public Ship(int codeShip, String name, String dateAcquisition, int state) {
        this.codeShip = codeShip;
        this.name = name;
        this.dateAcquisition = dateAcquisition;
        this.state = state;
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
    
    
}

