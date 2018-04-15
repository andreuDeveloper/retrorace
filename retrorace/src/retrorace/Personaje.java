/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author sosan
 */
public class Personaje implements Runnable, KeyListener{
    
    private Partida partida;
    private Point coordenadas;
    
    public Personaje (Partida partida) {
        this.partida = partida;
        this.coordenadas = new Point();
    }
    
    @Override
    public void run() {
        while (partida.isActiva()){
            
        }        
    }
    
    public void moverPersonaje(){
        
    }

    public Point getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Point coordenadas) {
        this.coordenadas = coordenadas;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }


    
    
    
}
