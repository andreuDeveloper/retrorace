/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author sosan
 */
public class GUI extends JFrame implements KeyListener{
    
    private Gamescreen gamescreen;
    Personaje p;
    //ranking
    
    public GUI () {
        this.addKeyListener(this);
        this.setVisible(true);
        this.setSize(500, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Partida pa = new Partida();
        this.gamescreen = new Gamescreen(pa);
        this.add(gamescreen);
        p = new Personaje(pa);
        pa.addPersonaje(p);
        new Thread(this.gamescreen).start();
    }
    
    
    
     @Override
    public void keyTyped(KeyEvent ke) {
        //System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
        
        switch (key) {
            case 37:    //Left
                p.moverIzquerda();
                break;
            case 38:    //Up
                
                break;
            case 39:    //Right
                p.moverDerecha();
                break;
            case 40:    //Down
                break;
            default:    //Other keys
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
    }
}
