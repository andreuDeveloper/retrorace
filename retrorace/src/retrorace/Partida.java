/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sosan
 */
public class Partida implements Runnable {

    private ArrayList<Personaje> personajes; //Por defecto 0 es el nuestro
    private boolean activa;
    private final double gravedad = 0.9;
    private Mapa mapa;

    public Partida(Mapa mapa) {
        this.personajes = new ArrayList<Personaje>();
        this.activa = false;
        this.personajes.add(new Personaje(this));
        this.mapa = mapa;
    }

    private void iniciarPartida() {
        this.activa = true;
        for (int i = 0; i < this.personajes.size(); i++) {
            new Thread(this.personajes.get(i)).start();
        }
    }
    
    @Override
    public void run() {
        iniciarPartida();
        while (activa) {
            //Check position %xcasilla
            checkPosition(this.personajes.get(0).getCoordenadas());
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Mapa getMapa() {
        return mapa;
    }

    public void addPersonaje() {
        if (this.personajes.size() < 4) {
            this.personajes.add(new Personaje(this));
        }
    }

    public void addPersonaje(Personaje p) {
        this.personajes.add(p);
    }

    public Personaje getPersonaje(int i) {
        return this.personajes.get(i);
    }

    public int totalPersonajes() {
        return this.personajes.size();
    }

    public boolean isActiva() {
        return this.activa;
    }

    public double getGravedad() {
        return gravedad;
    }

    private void checkPosition(Point pos){
        //Recoger la casilla donde nos encontramos, y hacer x cosas segun la casilla
        int xCas = pos.x / this.mapa.getCasilla(1, 1).getImage().getWidth(null);
        int yCas = pos.y / this.mapa.getCasilla(1, 1).getImage().getWidth(null);
        
        System.out.println("Casila Fila " + xCas +" Casilla columna: "+ yCas);
        System.out.println("Propiedad: " + this.mapa.getCasilla(xCas, yCas).getPropiedad());
        
        
    }
    
    private void checkIfCaida(int x){
        if (x > 455) {
            if (!this.personajes.get(0).isFalling()) {
                this.personajes.get(0).setY(501);
                this.personajes.get(0).setFalling(true);
            }
        } 
        
    }

}
