/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;


/**
 *
 * @author sosan
 */

public class Gamescreen extends Canvas implements Runnable {

    private Partida partida;
    private GUI gui;
    
    public Gamescreen(GUI gui){
        this.gui=gui;
    }

    //La partida tiene sentido que se pase por un metodo en algun momento, no como
    //parametro en el constructor, ya que no habra ninguna partida creada todavia
    public void setPartida(Partida partida) {
        this.partida = partida;
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
            repaint();
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
    
    private void tick(){
        
    }
    
    private void render() {
        
    }
    
    public void paint(Graphics g){
        this.partida.getPersonaje(0).pintar(g);
    }
}
