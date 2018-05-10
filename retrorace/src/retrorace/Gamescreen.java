/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

        //Dibujar fondo
        offGraphics.setColor(getBackground());
        offGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        //Dibjuar mapa
        this.partida.getMapa().paint(offGraphics);

        //Dibujar personajes
        for (int i = 0; i < this.partida.totalPersonajes(); i++) {
            this.partida.getPersonaje(i).pintar(offGraphics);
        }

        //Dibujar tiempo
        offGraphics.setColor(new Color(0x918886));
        offGraphics.setFont(new Font("Impact", 1, 65));
        offGraphics.drawString("TIME: " + this.partida.getTiempoPartida(), 850, 70);
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
            case 38:    //Up Space
            case 32:
                this.partida.getPersonaje(0).saltar(this.partida.getPersonaje(0).getFuerzaSalto());
                break;
            case 39:    //Right
                this.partida.getPersonaje(0).setMovingRight(true);
                break;
            case 82:    //R restart
                this.partida.getPersonaje(0).reset();
                break;
            default:    //Other keys
        }

        // FOR PLAYER 2
        if (this.partida.getTipoPartida().equals("Duo")) {
            switch (key) {
                case 65:    //Left
                    this.partida.getPersonaje(1).setMovingLeft(true);
                    break;
                case 87:    //Up Space
                    this.partida.getPersonaje(1).saltar(this.partida.getPersonaje(1).getFuerzaSalto());
                    break;
                case 68:    //Right
                    this.partida.getPersonaje(1).setMovingRight(true);
                    break;
                default:    //Other keys
            }
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

        //FOR DUO LOCAL
        if (this.partida.getTipoPartida().equals("Duo")) {
            switch (key) {
                case 65:    //A
                    this.partida.getPersonaje(1).setMovingLeft(false);
                    break;
                case 68:    //D
                    this.partida.getPersonaje(1).setMovingRight(false);
                    break;
                default:    //Other keys
            }
        }
    }
}
