/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import crypt.Hash;
import entities.Users;
import entities.UsersJpaController;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author sosan
 */
public class Juego {
    
    private Sesion sesion;
    private GUI gui;
    private UsersJpaController ujc;
    
    public void initJuego(){
        gui = new GUI(this);
        this.gui.initGUI();
        ujc = new UsersJpaController(Persistence.createEntityManagerFactory("retroracePU"));
    }

    void initSesion(String username) {
        this.sesion=new Sesion(this.gui,username);
    }

    boolean checkIfUsernameExists(String username) {
        return (ujc.checkIfUserExist(username)>0);
    }

    void registerUser(String username, String password) {
        Users u = new Users();
        u.setPassword(Hash.sha1(password));
        u.setUsername(username);
        
       ujc.create(u);
    }

    boolean checkConnectionDB() {
        try {  
            ujc.getUsersCount();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    boolean checkCredentials(String username, String password) {
        return (ujc.checkCredentials(username,Hash.sha1(password))>0);
    }
}
