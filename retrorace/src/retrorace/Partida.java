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
    
    private ArrayList<Personaje> personajes;
    private boolean activa;
    private final double gravedad = 9.8;
    private final double dt = 0.016683;
    
    //BORRAR ESTE CONSTRUCTOR
 public Partida(Mapa m){
        this.personajes = new ArrayList<Personaje>();
        this.activa = true;
    }
    
    public void addPersonaje(Personaje p) {
        //Personaje p = new Personaje(this);
        this.personajes.add(p);
        new Thread(p).start();
    }
    
    public Personaje getPersonaje(int i){
        return this.personajes.get(i);
    }
    
    public int totalPersonajes(){
        return this.personajes.size();
    }
    
    public boolean isActiva(){
        return this.activa;
    }

    public double getGravedad() {
        return gravedad;
    }

    public double getDt() {
        return dt;
    }
    
    
    
    
}
