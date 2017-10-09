/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author usuario
 */
public class Port {
    
      private int id;
    private String name;
    private int id_country;

    public Port(int id, String name, int id_country) {
        this.id = id;
        this.name = name;
        this.id_country = id_country;
    }
    
    
    

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }
    
    
}
