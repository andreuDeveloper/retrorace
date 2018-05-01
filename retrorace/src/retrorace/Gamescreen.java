/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
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
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (partida.isActiva()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                updates++;
                delta--;
            }
            preDraw();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void preDraw() { //Method which prepares the screen for drawing
        BufferStrategy bs = getBufferStrategy(); //Gets the buffer strategy our canvas is currently using
        if (bs == null) { //True if our canvas has no buffer strategy (should only happen once when we first start the game)
            createBufferStrategy(2); //Create a buffer strategy using two buffers (double buffer the canvas)
            return; //Break out of the preDraw method instead of continuing on, this way we have to check again if bs == null instead of just assuming createBufferStrategy(2) worked
        }

        Graphics g = bs.getDrawGraphics(); //Get the graphics from our buffer strategy (which is connected to our canvas)
        g.setColor(getBackground());
        g.fillRect(0, 0, WIDTH, HEIGHT); //Fill the screen with the canvas' background color
        g.setColor(getForeground());

        //repaint();
        paint(g); //Call our draw method, passing in the graphics object which we just got from our buffer strategy

        g.dispose(); //Dispose of our graphics object because it is no longer needed, and unnecessarily taking up memory
        bs.show(); //Show the buffer strategy, flip it if necessary (make back buffer the visible buffer and vice versa) 
    }

    public void paint(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        //this.partida.getMapa().mover(g, (int)this.partida.getPersonaje(0).getX());
        this.partida.getMapa().paint(g);
        //Pintar personajes
        for (int i = 0; i < this.partida.totalPersonajes(); i++) {
            this.partida.getPersonaje(i).pintar(g);
        }
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
            case 40:    //Down
                //this.partida.getPersonaje(0).caer();
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
