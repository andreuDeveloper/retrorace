/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import crypt.Hash;
import entities.Ranking;
import entities.RankingJpaController;
import entities.Users;
import entities.UsersJpaController;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Usuario
 */
public class DBManager {

    /*ATRIBUTOS*/
    private final UsersJpaController ujc;
    private final RankingJpaController rjc;

    /*CONTRUCTOR*/
    public DBManager() {
        ujc = new UsersJpaController(Persistence.createEntityManagerFactory("retroracePU"));
        rjc = new RankingJpaController(Persistence.createEntityManagerFactory("retroracePU"));
    }

    /* MÉTODOS PULBICOS*/
    /**
     * Comprueba la conexion con la BD
     *
     * @return
     */
    public boolean checkConnectionDB() {
        try {
            ujc.getUsersCount();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Comprueba las credenciales para hacer login
     *
     * @param username
     * @param password
     * @return
     */
    public boolean checkCredentials(String username, String password) {
        return (ujc.checkCredentials(username, Hash.sha1(password)) > 0);
    }

    /**
     * Comprueba si existe un usuario en la BD
     *
     * @param username Nombre de usuario a comprobar
     * @return
     */
    public boolean checkIfUsernameExists(String username) {
        return (ujc.checkIfUserExist(username) > 0);
    }

    /**
     * Consigue el ranking global para un mapa
     *
     * @param idMap
     * @return
     */
    public List<Ranking> getGlobalRankingInMap(int idMap) {
        return rjc.getGlobalRankingInMap(idMap);
    }

    /**
     * Consigue el ranking personal de un usuario en un mapa
     *
     * @param idMap
     * @param username
     * @return
     */
    public List<Ranking> getPersonalRankingInMap(int idMap, String username) {
        return rjc.getPersonalRankingInMap(idMap, username);
    }

    /**
     * Recibe usuario y password y lo registra
     *
     * @param username
     * @param password
     */
    public void registerUser(String username, String password) {
        Users u = new Users();
        u.setPassword(Hash.sha1(password));
        u.setUsername(username);

        ujc.create(u);
    }

    /**
     * Guarda un record en el ranking de BD
     *
     * @param r
     */
    public void saveRecord(Ranking r) {
        rjc.create(r);
    }

}
