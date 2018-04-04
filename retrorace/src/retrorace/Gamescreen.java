/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retrorace;

/**
 *
 * @author sosan
 */
public class Gamescreen {
    
    private Partida partida;
    
    public Gamescreen(){
        
    }
    
    //La partida tiene sentido que se pase por un metodo en algun momento, no como
    //parametro en el constructor, ya que no habra ninguna partida creada todavia
    public void setPartida (Partida partida) {
        this.partida = partida;
    }
}
