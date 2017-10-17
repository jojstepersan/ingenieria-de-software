/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Sonico_Willie
 */
public class Shipment {
    private int idShipment;
    private int idContract;
    private int idShip;
    private int weight;
    private String source;
    private String destination;
    private String description; 
    public Shipment(int idShipment, int idContract, int idShip, int weight, String source, String destination, String description) {
        this.idShipment = idShipment;
        this.idContract = idContract;
        this.idShip = idShip;
        this.weight = weight;
        this.source = source;
        this.destination = destination;
        this.description = description;
    }
    public int getIdShipment() {
        return idShipment;
    }

    public void setIdShipment(int idShipment) {
        this.idShipment = idShipment;
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public int getIdShip() {
        return idShip;
    }

    public void setIdShip(int idShip) {
        this.idShip = idShip;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
