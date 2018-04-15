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
public class Partida {
    
    private ArrayList personajes;
    private boolean activa;
    
    public Partida(){
        this.personajes = new ArrayList();
        this.activa = true;
    }
    
    public void addPersonaje() {
        Personaje p = new Personaje(this);
        this.personajes.add(p);
        new Thread(p).start();
    }
    
    public boolean isActiva(){
        return this.activa;
    }
    
    
}
