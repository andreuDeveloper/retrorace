/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sosan
 */
public class Personaje implements Runnable {

    private float x, y;
    private float velX = 0, velY = 0;
    private Partida partida;

    public Personaje(Partida partida) {
        this.x = 0;
        this.y = 0;
        this.partida = partida;
    }

    @Override
    public void run() {
        while (partida.isActiva()) {
            try {

                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Personaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public float getX() {
        return this.x;
    }
    
    public void setX(float x){
        this.x = x;
    }
    
    public float getY(){
        return this.y;
    }
    
    public void setY(float y){
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
    
    public void pintar(Graphics g){
        g.setColor(Color.blue);
        g.fillRect((int) x, (int)y, 32, 32);
    }
    
    public void moverIzquerda(){
        this.x = x - 10;
    }
    
    public void moverDerecha(){
        this.x = x + 10;
    }
    

}
