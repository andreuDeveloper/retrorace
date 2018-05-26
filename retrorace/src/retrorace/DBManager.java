/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import crypt.Hash;
import entities.Users;
import entities.UsersJpaController;
import javax.persistence.Persistence;

/**
 *
 * @author Usuario
 */
public class DBManager{
    
    private UsersJpaController ujc;
    
    public DBManager(){
        ujc = new UsersJpaController(Persistence.createEntityManagerFactory("retroracePU"));
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
