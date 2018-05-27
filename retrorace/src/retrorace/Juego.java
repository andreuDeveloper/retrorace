/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import entities.Ranking;

/**
 *
 * @author sosan
 */
public class Juego {

    /*ATRIBUTOS*/
    private Sesion sesion;
    private GUI gui;
    private DBManager dbManager;

    /*GETTERS Y SETTERS*/
    public DBManager getDbManager() {
        return dbManager;
    }

    /*MÉTODOS PÚBLICOS*/
    /**
     * Comprueba si un usuario existe en BD
     *
     * @param username
     * @return
     */
    public boolean checkIfUsernameExists(String username) {
        return dbManager.checkIfUsernameExists(username);
    }

    /**
     * Comprueba la conexión con BD
     *
     * @return
     */
    public boolean checkConnectionDB() {
        return dbManager.checkConnectionDB();
    }

    /**
     * Comprueba las credenciales de un usuario en BD
     *
     * @param username
     * @param password
     * @return
     */
    public boolean checkCredentials(String username, String password) {
        return dbManager.checkCredentials(username, password);
    }

    /**
     * Inicia la GUI y la BD
     */
    public void initJuego() {
        gui = new GUI(this);
        this.gui.initGUI();
        dbManager = new DBManager();
    }

    /**
     * Inicia la sesión de un usuario
     *
     * @param username
     */
    public void initSesion(String username) {
        this.sesion = new Sesion(this.gui, username);
    }

    /**
     * Registra a un usuario en BD
     *
     * @param username
     * @param password
     */
    public void registerUser(String username, String password) {
        dbManager.registerUser(username, password);
    }

}
