/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author jojstepersan
 * 
 */
public class Admin  extends User{

    public Admin(int id, int tipoEmpleado, String name, String pass, String lastName, String username) {
        super(id, tipoEmpleado, name, pass, lastName, username);
    }
    public Admin(){}
    
}
