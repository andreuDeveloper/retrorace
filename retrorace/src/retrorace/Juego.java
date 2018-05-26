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
    private DBManager dbManager;
    
    
    public void initJuego(){
        gui = new GUI(this);
        this.gui.initGUI();
        dbManager = new DBManager();
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    void initSesion(String username) {
        this.sesion=new Sesion(this.gui,username);
    }

    boolean checkIfUsernameExists(String username) {
        return dbManager.checkIfUsernameExists(username);
    }

    void registerUser(String username, String password) {
        dbManager.registerUser(username, password);
    }

    boolean checkConnectionDB() {
        return dbManager.checkConnectionDB();
    }

    boolean checkCredentials(String username, String password) {
        return dbManager.checkCredentials(username, password);
    }
}
