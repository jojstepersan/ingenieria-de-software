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
public class User {
    
    private int id;
    private int tipoEmpleado;
    private String name;
    private String pass;
    private String lastName;
    private String username;

    public User(int id, int tipoEmpleado, String name, String pass, String lastName, String username) {
        this.id = id;
        this.tipoEmpleado = tipoEmpleado;
        this.name = name;
        this.pass = pass;
        this.lastName = lastName;
        this.username = username;
    }
    public User(){}
    public int getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(int tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
