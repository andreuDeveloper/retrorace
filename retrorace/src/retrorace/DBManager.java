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
public class DBManager{
    
    private UsersJpaController ujc;
    private RankingJpaController rjc;
    
    public DBManager(){
        ujc = new UsersJpaController(Persistence.createEntityManagerFactory("retroracePU"));
        rjc = new RankingJpaController(Persistence.createEntityManagerFactory("retroracePU"));
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
    
    public List<Ranking> getGlobalRankingInMap(int idMap){
        return rjc.getGlobalRankingInMap(idMap);
    }
    
    public List<Ranking> getPersonalRankingInMap(int idMap, String username){
        return rjc.getPersonalRankingInMap(idMap, username);
    }

    void saveRecord(Ranking r) {
        rjc.create(r);
    }
    
}
