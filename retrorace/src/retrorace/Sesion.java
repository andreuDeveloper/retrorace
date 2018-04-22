/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.util.ArrayList;

/**
 *
 * @author sosan
 */
public class Sesion {
    
    private ArrayList mapas;
    private Partida partida;
    private GUI gui;
    private String username;
    
    //ranking
    
    public Sesion(GUI gui,String user){
        this.gui = gui;
        this.username=user;
    }
    
    
    
}
