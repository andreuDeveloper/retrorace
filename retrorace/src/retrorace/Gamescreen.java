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

/**
 *
 * @author sosan
 */
public class Gamescreen extends Canvas implements Runnable, KeyListener {

    private Partida partida;
    private GUI gui;

    
    public Gamescreen(GUI gui, Partida partida){
        this.addKeyListener(this);
        this.gui=gui;
        this.partida = partida;
        this.partida.getMapa().iniciarMapa();
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
        this.partida.getMapa().iniciarMapa();
        this.createBufferStrategy(2);
        BufferStrategy strategy = this.getBufferStrategy();
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
                tick();
                updates++;
                delta--;
            }
            preDraw();            
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick() {

    }

    private void render() {

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
        this.partida.getMapa().paint(g);
        for (int i = 0; i < this.partida.totalPersonajes(); i++) {
            this.partida.getPersonaje(i).pintar(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        int key = ke.getKeyCode();
        System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
        
        switch (key) {
            case 37:    //Left
                this.partida.getPersonaje(0).moverIzquerda();
                break;
            case 38:    //Up
            case 32:    
                this.partida.getPersonaje(0).moverArriba();
                break;
            case 39:    //Right
                this.partida.getPersonaje(0).moverDerecha();
                break;
            case 40:    //Down
                this.partida.getPersonaje(0).moverAbajo();
                break;
            default:    //Other keys
        }
    }

    @Override
    public  void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
        
        switch (key) {
            case 37:    //Left
                this.partida.getPersonaje(0).moverIzquerda();
                break;
            case 38:    //Up
            case 32:    
                this.partida.getPersonaje(0).moverArriba();
                break;
            case 39:    //Right
                this.partida.getPersonaje(0).moverDerecha();
                break;
            case 40:    //Down
                this.partida.getPersonaje(0).moverAbajo();
                break;
            default:    //Other keys
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
       
    }
}
