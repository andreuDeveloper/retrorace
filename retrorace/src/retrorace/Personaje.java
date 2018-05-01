/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author sosan
 */
public class Personaje implements Runnable {

    private double x, y;
    private double velX = 0, velY = 0;
    private boolean falling = true;
    private boolean jumping = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean estaEnSuelo = false;
    private String lastDirection = "Right";
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

                if (y > 400) {
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

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    private void moverPersonaje() {
        if (this.movingLeft) {
            lastDirection = "Left";
            moverIzquerda();
        }
        if (this.movingRight) {
            lastDirection = "Right";
            moverDerecha();
        }
        //if (this.jumping) saltar();
    }

    public void pintar(Graphics g) {
        moverPersonaje();
        String imgPath = "img/personaje/pj" + lastDirection + ".png";
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
        }
        g.drawImage(img,(int) x, (int) y, null);
    }

    public void moverIzquerda() {
        this.x = x - 3;
    }

    public void moverDerecha() {
        this.x = x + 3;
    }

    public void saltar() {
        if (!jumping && !falling) {
            velY = - 15;
            this.y = y + velY;
            jumping = true;
        }

    }

    /*
    public void caer() {
        if (y < 500) {
            this.y = y + 5;
            if (timeAc > 5) {
                this.timeAc *= 0.95;
            }
        } else {
            timeAc = 100;
        }
    }
*/

    public Point getCoordenadas() {
        return new Point((int) x, (int) y);
    }

}
