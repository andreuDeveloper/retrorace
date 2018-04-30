/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

import java.awt.Canvas;
import java.awt.Color;

/**
 *
 * @author sosan
 */
public class Gamescreen extends Canvas{
    
    private Partida partida;
    private GUI gui;
    
    public Gamescreen(GUI gui){
        this.gui=gui;
    }
    
    //La partida tiene sentido que se pase por un metodo en algun momento, no como
    //parametro en el constructor, ya que no habra ninguna partida creada todavia
    public void setPartida (Partida partida) {
        this.partida = partida;
    }
}
