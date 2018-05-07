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
    private double ancho, alto;
    private double velX = 0, velY = 0;
    private boolean falling = true;
    private boolean jumping = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean estaSobreSuelo = false;
    private boolean estaMoviendo = true;
    private String lastDirection = "Right";
    private Partida partida;
    private int timeAc = 30;
    private boolean enMeta;
    private Animacion animacion;

    public Personaje(Partida partida) {
        this.x = 5;
        this.y = 0;
        this.partida = partida;
        this.enMeta = false;
        this.estaMoviendo = false;
        this.animacion = new Animacion();
    }

    @Override
    public void run() {
        new Thread(this.animacion).start();
        
        while (partida.isActiva()) {
            try {
                x += velX;
                y += velY;
                if (falling || jumping) {
                    velY += this.partida.getGravedad();
                    //max vel
                }

                if (enMeta) {
                    saltar();
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

    public boolean isEstaSobreSuelo() {
        return estaSobreSuelo;
    }

    public void setEstaSobreSuelo(boolean estaSobreSuelo) {
        this.estaSobreSuelo = estaSobreSuelo;
        if (estaSobreSuelo) {
            velY = 0;
            falling = false;
            jumping = false;
        } else {
            falling = true;
        }

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
        if (!movingLeft) this.animacion.restartContador();
        this.movingLeft = estaMoviendo = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        if (!movingRight) this.animacion.restartContador();
        this.movingRight = estaMoviendo = movingRight;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }

    public boolean isEnMeta() {
        return enMeta;
    }

    public void setEnMeta(boolean enMeta) {
        this.enMeta = enMeta;
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
        String imgPath;
        if (isJumping()) {
            imgPath = "img/personaje30/pjJumping.png";
        } else if (estaMoviendo) {
            imgPath = "img/personaje30/pj" + lastDirection + "" +this.animacion.getContador()%6+ ".png";
        } else {
            imgPath = "img/personaje30/pj" + lastDirection + "0.png";
        }
        
        //System.out.println(imgPath);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgPath));
            this.alto = img.getHeight();
            this.ancho = img.getWidth();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        g.drawImage(img, (int) x, (int) y, null);
        //g.drawImage(img, 0, (int) y, null);
    }

    public void moverIzquerda() {
        if (!enMeta && partida.puedeMoverIzquierda((int) x, (int) y)) {
            this.x = x - 3;
        }
    }

    public void moverDerecha() {
        if (!enMeta && partida.puedeMoverDerecha((int) x, (int) y)) {
            this.x = x + 3;
        }
    }

    public void saltar() {
        if (!jumping && !falling) {
            velY = -11.5;
            this.y = y + velY;
            jumping = true;
        }
    }

    public void reset(Point lastCheck) {
        if (!enMeta) {
            this.estaSobreSuelo = false;
            this.estaMoviendo = false;
            this.velY = 0;
            this.velX = 0;
            if (lastCheck != null) {
                this.x = lastCheck.x;
                this.y = lastCheck.y;
            } else {
                this.x = 0;
                this.y = 0;
            }
        }
    }

    public Point getCoordenadas() {
        return new Point((int) x, (int) y);
    }

}
