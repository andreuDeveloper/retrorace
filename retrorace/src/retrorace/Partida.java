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
    private final double gravedad = 1.5;
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

    private void checkPosition(Point pos) {
        //Recoger la casilla donde nos encontramos, y hacer x cosas segun la casilla
        Casilla aux = this.mapa.getCasilla(0, 0);
        if (aux != null) {
            int anchoCasillas = this.mapa.getCasilla(0, 0).getImage().getWidth(null);
            int xIzquierda = pos.x / anchoCasillas;
            int xDerecha = (pos.x + (int) this.personajes.get(0).getAncho()) / anchoCasillas;
            int yCabeza = pos.y / anchoCasillas;
            int ySuelo = (pos.y + (int) this.personajes.get(0).getAlto()) / anchoCasillas;
            
            System.out.println("Y: "+ySuelo+ " .. X: "+xIzquierda+" -- "+this.mapa.getCasilla(ySuelo, xIzquierda).getPropiedad());
        }

    }

    private void checkIfCaida(int x) {
        if (x > 455) {
            if (!this.personajes.get(0).isFalling()) {
                this.personajes.get(0).setY(501);
                this.personajes.get(0).setFalling(true);
            }
        }

    }

}
