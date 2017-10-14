/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author jojstepersan
 */
public class Country {
    private String name;
    private int id;
    private double x;
    private double y;

    public Country(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public Country(){}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Country(String name, int id, double x, double y) {
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
}
