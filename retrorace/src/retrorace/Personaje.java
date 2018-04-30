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

    private double x, y;
    private double velX = 0, velY = 0;
    private boolean falling = true;
    private boolean jumping = false;
    private Partida partida;
    private int timeAc = 33;

    public Personaje(Partida partida) {
        this.x = 0;
        this.y = 0;
        this.partida = partida;
    }

    @Override
    public void run() {
        while (partida.isActiva()) {
            try {
                x += velX;
                y += velY;
                if (falling || jumping) {
                    velY += this.partida.getGravedad();
                    //max vel
                }
                if (y > 500) {
                    velY = 0;
                    falling = false;
                    jumping = false;
                }

                Thread.sleep(timeAc);
            } catch (InterruptedException ex) {
                Logger.getLogger(Personaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public double getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public void pintar(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval((int) x, (int) y, 70, 70);
    }

    public void moverIzquerda() {
        this.x = x - 10;
    }

    public void moverDerecha() {
        this.x = x + 10;
    }

    public void moverArriba() {
        if (!jumping && !falling) {
            velY = - 15;
            this.y = y + velY;
            jumping = true;
        }

    }

    public void moverAbajo() {
        if (y < 500) {
            this.y = y + 5;
            if (timeAc > 5) {
                this.timeAc *= 0.95;
            }
        } else {
            timeAc = 100;
        }
    }

    private void fall() {
        int max_vel = 300;
        this.velY = this.velY + this.partida.getGravedad();
        if (this.velY > max_vel) {
            this.velY = max_vel;
        }
        this.y = this.y + this.velY;
    }

}
