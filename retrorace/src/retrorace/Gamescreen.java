/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sosan
 */
public class Gamescreen extends Canvas implements Runnable, KeyListener {

    private Partida partida;
    private GUI gui;
    private final Set<Character> pressed = new HashSet<Character>();
    private Dimension offDimension;
    private Image offImage;
    private Graphics offGraphics;

    public Gamescreen(GUI gui, Partida partida) {
        this.addKeyListener(this);
        this.gui = gui;
        this.partida = partida;
        this.partida.getMapa().iniciarMapa();
    }

    public void setPartida(Partida partida) {

        this.partida = partida;
        this.partida.getMapa().iniciarMapa();
    }

    @Override
    public void run() {
        while (partida.isActiva()) {
            preDraw();
        }
    }

    private void preDraw() { 
        Graphics g = this.getGraphics();
        paint(g);
    }

    public void paint(Graphics g) {
        if ((offGraphics == null)
                || (this.getWidth() != offDimension.width)
                || (this.getHeight() != offDimension.height)) {
            offDimension = this.getSize();
            offImage = createImage(this.getWidth(), this.getHeight());
            offGraphics = offImage.getGraphics();
        }
        offGraphics.setColor(getBackground());
        offGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.partida.getMapa().paint(offGraphics);
        for (int i = 0; i < this.partida.totalPersonajes(); i++) {
            this.partida.getPersonaje(i).pintar(offGraphics);
        }
        g.drawImage(offImage, 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        System.out.println("keyPressed=" + KeyEvent.getKeyText(ke.getKeyCode()));

        switch (key) {
            case 37:    //Left
                this.partida.getPersonaje(0).setMovingLeft(true);
                break;
            case 38:    //Up
            case 32:
                this.partida.getPersonaje(0).saltar();
                break;
            case 39:    //Right
                this.partida.getPersonaje(0).setMovingRight(true);
                break;
            case 82:    //R restart
                this.partida.getPersonaje(0).reset(this.partida.getLastCheckPoint());
                break;
            default:    //Other keys
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        System.out.println("keyReleased=" + KeyEvent.getKeyText(ke.getKeyCode()));

        switch (key) {
            case 37:    //Left
                this.partida.getPersonaje(0).setMovingLeft(false);
                break;
            case 39:    //Right
                this.partida.getPersonaje(0).setMovingRight(false);
                break;
            default:    //Other keys
        }
    }
}
